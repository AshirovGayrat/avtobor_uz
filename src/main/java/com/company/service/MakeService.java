package com.company.service;

import com.company.dto.request.MakeRequestDTO;
import com.company.dto.response.MakeResponseDTO;
import com.company.entity.MakeEntity;
import com.company.exp.CategoryAlreadyExistsException;
import com.company.exp.ItemNotFoundException;
import com.company.repository.MakeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MakeService {
    private final MakeRepository makeRepository;

    public MakeResponseDTO create(MakeRequestDTO dto) {
        if (getByName(dto.getName()).isPresent()) {
            log.warn("Make already exists: {}", dto);
            throw new CategoryAlreadyExistsException("Make already exists");
        }

        MakeEntity entity = new MakeEntity();
        entity.setName(dto.getName());
        makeRepository.save(entity);
        return toDto(entity);
    }

    public PageImpl<MakeResponseDTO> getAllWithPagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdDate"));
        Page<MakeEntity> categoryEntityPage = makeRepository.findAll(pageable);

        List<MakeEntity> categoryEntityList = categoryEntityPage.getContent();
        long totalContent = categoryEntityPage.getTotalElements();
        List<MakeResponseDTO> dtoList = categoryEntityList.stream().map(this::toDto).toList();
        return new PageImpl<MakeResponseDTO>(dtoList, pageable, totalContent);
    }

    public Boolean update(Long id, MakeRequestDTO dto) {
        MakeEntity entity = getById(id);

        if (getByName(dto.getName()).isPresent()) {
            log.warn("Make already exists: {}", dto);
            throw new CategoryAlreadyExistsException("Make alredy exists");
        }

        entity.setName(dto.getName());
        makeRepository.save(entity);
        return true;
    }

    public Boolean delete(Long id) {
        MakeEntity entity = getById(id);
        if (!entity.getVisible()) {
            return true;
        }

        int n = makeRepository.updateVisible(id);
        return n > 0;
    }

    public Optional<MakeEntity> getOptionalById(Long id) {return makeRepository.findById(id);}

    public MakeEntity getById(Long id) {
        return makeRepository.findById(id).orElseThrow(() -> {
            log.warn("Make not found: {}", id);
            throw new ItemNotFoundException("Make not found");
        });
    }

    public MakeResponseDTO getMakeById(Long id) {
        return toDto(getById(id));
    }

    public Optional<MakeEntity> getByName(String name) {return makeRepository.findByName(name);}

    public MakeResponseDTO toDto(MakeEntity entity) {
        MakeResponseDTO dto = new MakeResponseDTO();
        dto.setName(entity.getName());
        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }
}
