package com.company.service;

import com.company.dto.response.AttachResponseDTO;
import com.company.entity.AttachEntity;
import com.company.exp.AppBadRequestException;
import com.company.exp.ItemNotFoundException;
import com.company.repository.AttachRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class AttachService {

    private final AttachRepository attachRepository;
    @Value("${attach.upload.fold")
    private String attachFolder;
    @Value("${server.domain.name}")
    private String domainName;

    public AttachResponseDTO upload(MultipartFile file) {
        String pathFolder = getYMDString();  // 2022/04/23
        File folder = new File(attachFolder + pathFolder);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        String key = UUID.randomUUID().toString(); // create uuid
        String extension = getExtension(file.getOriginalFilename());

        AttachEntity entity = saveAttach(key, pathFolder, extension, file);
        AttachResponseDTO dto = toDTO(entity);

        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(attachFolder + pathFolder + "/" + key + "." + extension);
            Files.write(path, bytes);
        } catch (IOException e) {
            log.warn("Not upload {}", AttachService.class);
            throw new RuntimeException(e);
        }
        return dto;
    }

    public ResponseEntity<Resource> download(String key) {
        try {
            AttachEntity entity = get(key);
            String path = entity.getPath() + "/" + key + "." + entity.getExtension();
            Path file = Paths.get(attachFolder + path);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                                "attachment; filename=\"" + entity.getOriginName() + "\"")
                        .body(resource);

            } else {
                log.warn("cloud not read the file : {}", key );
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    public AttachEntity get(String id) {
        return attachRepository.findById(id).orElseThrow(() -> {
            log.warn("Attach not found!{}", AttachService.class);
            throw new ItemNotFoundException("Attach Not Found");
        });
    }

    public byte[] openGeneral(String key) {
        byte[] data;
        try {
            AttachEntity entity = get(key);
            String path = entity.getPath() + "/" + key + "." + entity.getExtension();
            Path file = Paths.get(attachFolder + path);
            data = Files.readAllBytes(file);
            return data;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    public Boolean delete(String key) {
        AttachEntity entity = get(key);

        File file = new File(attachFolder + entity.getPath() +
                "/" + entity.getId() + "." + entity.getExtension());

        if (file.delete()) {
            attachRepository.deleteById(key);
            return true;
        }
        log.warn("Could not read the file!{}", AttachService.class);
        throw new AppBadRequestException("Could not read the file!");
    }

    private AttachResponseDTO toDTO(AttachEntity entity) {
        AttachResponseDTO dto = new AttachResponseDTO();
        dto.setId(entity.getId());
        dto.setOriginName(entity.getOriginName());
        dto.setPath(entity.getPath());
        dto.setUrl(domainName + "/attach/download/" + entity.getId());
        dto.setSize(entity.getSize());
        return dto;
    }

    private AttachEntity saveAttach(String key, String pathFolder, String extension, MultipartFile file) {
        AttachEntity entity = new AttachEntity();
        entity.setId(key);
        entity.setPath(pathFolder);
        entity.setOriginName(file.getOriginalFilename());
        entity.setExtension(extension);
        entity.setSize(file.getSize());
        attachRepository.save(entity);
        return entity;
    }

    private String getExtension(String fileName) {
        int lastIndex = fileName.lastIndexOf(".");
        return fileName.substring(lastIndex + 1);
    }

    private String getYMDString() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int day = Calendar.getInstance().get(Calendar.DATE);

        return year + "/" + month + "/" + day; // 2022/04/23
    }

    public String toOpenURL(String id) {
        return domainName + "/api/v1/attach/open-general/" + id;
    }

    public AttachResponseDTO toOpenURLDTO(String id) {
        return new AttachResponseDTO(domainName + "/api/v1/attach/open-general/" + id);
    }
}
