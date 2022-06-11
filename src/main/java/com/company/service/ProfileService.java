package com.company.service;

import com.company.config.details.EntityDetails;
import com.company.dto.request.ChangePswdDTO;
import com.company.dto.request.ProfileRequestDTO;
import com.company.dto.request.ProfileUpdateDto;
import com.company.dto.response.ProfileResponseDTO;
import com.company.dto.response.ResponseMessageDTO;
import com.company.entity.ProfileEntity;
import com.company.enums.ProfileRole;
import com.company.enums.ProfileStatus;
import com.company.exp.AppBadRequestException;
import com.company.exp.ItemNotFoundException;
import com.company.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileService {
    private final ProfileRepository profileRepository;

    public Boolean changePswd(ChangePswdDTO dto) {
        var profile = EntityDetails.getProfile();

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedOldPswd = passwordEncoder.encode(dto.getOldPassword());

        if (!profile.getPassword().equals(encodedOldPswd)) throw new AppBadRequestException("Old password wrong");

        int n = profileRepository.updatePassword(dto.getNewPassword(), profile.getId());
        return n > 0;
    }

    //Create profile
    public ResponseMessageDTO createProfile(ProfileRequestDTO dto) {

        Optional<ProfileEntity> optional = profileRepository.findByEmailAndVisibleTrue(dto.getEmail());
        if (optional.isPresent()) {
            log.warn("email alredy exists : {}", dto);
            return new ResponseMessageDTO("Email Already Exits", false);
//            throw new EmailAlreadyExistsException("Email Already Exits");
        }

        ProfileEntity entity = toEntity(dto);
        try {
            profileRepository.save(entity);
        } catch (DataIntegrityViolationException e) {
            log.warn("Unique Email {}", dto.getEmail());
            throw new AppBadRequestException("Unique Email!");
        }

        return new ResponseMessageDTO("Success", true);
    }

    //Get User pagination list
    public List<ProfileResponseDTO> paginationList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdDate"));

        List<ProfileResponseDTO> list = new ArrayList<>();
        profileRepository.findAllByVisibleTrue(pageable).forEach(entity -> list.add(toDTO(entity)));

        if (list.isEmpty()) {
            log.warn(" not found : {}", "Profils");
            throw new ItemNotFoundException("Not Found!");
        }
        return list;
    }

    // Get by id
    public ProfileResponseDTO getById(Long id) {
        ProfileEntity entity = profileRepository.findById(id).
                orElseThrow(() -> new ItemNotFoundException("Not Found!"));
        return toDTO(entity);
    }

    // Update profile
    public ProfileUpdateDto updateProfile(ProfileUpdateDto dto) {
        var entity=EntityDetails.getProfile();

        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setEmail(dto.getEmail());
        entity.setPhone(dto.getPhone());
        entity.setAbout(dto.getAbout());
        entity.setFacebook(dto.getFacebook());
        entity.setWebSite(dto.getWebSite());

        try {
            profileRepository.save(entity);
        } catch (DataIntegrityViolationException e) {
            log.warn("Unique Email {}", dto.getEmail());
            throw new AppBadRequestException("Unique Email!");
        }

        return toDTO(entity);
    }

    //delete profile
    public Boolean delete(Long id) {
        ProfileEntity entity = getProfileById(id);
        if (!entity.getVisible()) {
            return true;
        }
        int n = profileRepository.updateVisible(id);
        return n > 0;
    }

    public Boolean updateStatus(ProfileStatus status, Long id) {
        ProfileEntity entity = getProfileById(id);
        int n = profileRepository.updateStatus(status, id);
        return n > 0;
    }

    public ProfileResponseDTO toDTO(ProfileEntity entity) {
        ProfileResponseDTO dto = new ProfileResponseDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setEmail(entity.getEmail());
        dto.setPhone(entity.getPhone());
        dto.setWebSite(entity.getWebSite());
        dto.setFacebook(entity.getFacebook());
        dto.setAbout(entity.getAbout());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public ProfileEntity getProfileById(Long id) {
        return profileRepository.findById(id).
                orElseThrow(() -> new ItemNotFoundException("Profile Not Found!"));
    }

    public ProfileEntity getProfileByPhone(String phone) {
        return profileRepository.findByPhoneAndVisibleTrue(phone).
                orElseThrow(() -> new ItemNotFoundException("Profile Not Found!"));
    }

    public ProfileEntity toEntity(ProfileRequestDTO dto) {
        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        if (Optional.ofNullable(dto.getEmail()).isPresent()) {
            entity.setEmail(dto.getEmail());
        }
        if (Optional.ofNullable(dto.getPhone()).isPresent()) {
            entity.setPhone(dto.getPhone());
        }
        BCryptPasswordEncoder pswdEncode = new BCryptPasswordEncoder();
        String encodedPswd = pswdEncode.encode(dto.getPassword());
        entity.setPassword(encodedPswd);
        entity.setAbout(dto.getAbout());
        entity.setRole(ProfileRole.ROLE_USER);
        entity.setStatus(ProfileStatus.NOT_CONFIRMED);
        entity.setType(dto.getProfileType());
        return entity;
    }
}
