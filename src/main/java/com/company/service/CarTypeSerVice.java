package com.company.service;

import com.company.dto.request.CarTypeRequestDTO;
import com.company.dto.response.CarTypeResponseDTO;
import com.company.entity.CarTypeEntity;
import com.company.exp.CategoryAlreadyExistsException;
import com.company.exp.ItemNotFoundException;
import com.company.repository.CarTypeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CarTypeSerVice {
    private final CarTypeRepository carTypeRepository;

    public CarTypeResponseDTO create(CarTypeRequestDTO dto) {
        Optional<CarTypeEntity> optional=getByName(dto.getNameEn(),dto.getNameUz(),dto.getNameRu());
        if (optional.isPresent()) {
            throw new CategoryAlreadyExistsException("Category already exists");
        }

        CarTypeEntity entity = new CarTypeEntity();
        entity.setNameEn(dto.getNameEn());
        entity.setNameRu(dto.getNameRu());
        entity.setNameUz(dto.getNameUz());
        carTypeRepository.save(entity);
        return toDto(entity);
    }

    public PageImpl<CarTypeResponseDTO> getAllWithPagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdDate"));
        Page<CarTypeEntity> categoryEntityPage = carTypeRepository.findAllByVisibleTrue(pageable);

        List<CarTypeEntity> categoryEntityList = categoryEntityPage.getContent();
        long totalContent = categoryEntityPage.getTotalElements();
        List<CarTypeResponseDTO> dtoList = categoryEntityList.stream().map(this::toDto).toList();
        return new PageImpl<CarTypeResponseDTO>(dtoList, pageable, totalContent);
    }

    public Boolean update(Long id, CarTypeRequestDTO dto) {
        Optional<CarTypeEntity> optional=getByName(dto.getNameEn(),dto.getNameUz(),dto.getNameRu());
        if (optional.isPresent()) {
            throw new CategoryAlreadyExistsException("Category already exists");
        }

        CarTypeEntity category=getById(id);
        category.setNameUz(dto.getNameUz());
        category.setNameEn(dto.getNameEn());
        category.setNameRu(dto.getNameRu());
        carTypeRepository.save(category);
        return true;
    }

    public Boolean delete(Long id) {
        CarTypeEntity entity = getById(id);
        if (!entity.getVisible()) {
            return true;
        }
        int n = carTypeRepository.updateVisible(id);
        return n > 0;
    }

    public CarTypeEntity getById(Long id) {
        return carTypeRepository.findById(id).orElseThrow(()->{
            throw new ItemNotFoundException("Category not found");
        });
    }

    public Optional<CarTypeEntity> getOptionalById(Long id) {
        return carTypeRepository.findById(id);
    }

    public Optional<CarTypeEntity> getByName(String en, String uz, String ru) {
        return carTypeRepository.findByNameEnAndNameUzAndNameRuAndVisibleTrue(en, uz, ru);
    }

    public CarTypeResponseDTO toDto(CarTypeEntity entity) {
        CarTypeResponseDTO dto = new CarTypeResponseDTO();
        dto.setNameUz(entity.getNameUz());
        dto.setNameEn(entity.getNameEn());
        dto.setNameRu(entity.getNameRu());
        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }
}
