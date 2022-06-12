package com.company.service;

import com.company.dto.request.ConditionsRequestDTO;
import com.company.dto.response.ConditionsResponseDTO;
import com.company.entity.ConditionsEntity;
import com.company.enums.AppLang;
import com.company.exp.CategoryAlreadyExistsException;
import com.company.exp.ItemNotFoundException;
import com.company.repository.ConditionsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConditionsService {
    private final ConditionsRepository conditionsRepository;

    public ConditionsResponseDTO create(ConditionsRequestDTO dto) {
        Optional<ConditionsEntity> optional = getByName(dto.getNameEn(), dto.getNameUz(), dto.getNameRu());
        if (optional.isPresent()) {
            log.warn("Conditions already exists: {}", dto);
            throw new CategoryAlreadyExistsException("Conditions already exists");
        }

        ConditionsEntity entity = new ConditionsEntity();
        entity.setNameEn(dto.getNameEn());
        entity.setNameRu(dto.getNameRu());
        entity.setNameUz(dto.getNameUz());
        conditionsRepository.save(entity);
        return toDto(entity, AppLang.uz);
    }

    public List<ConditionsResponseDTO> getConditionsList(AppLang appLanguage) {
        List<ConditionsEntity> conditionsList = conditionsRepository.findAllByVisibleTrue();

        List<ConditionsResponseDTO> dtoList = new ArrayList<>();

        conditionsList.forEach(entity -> dtoList.add(toDto(entity, appLanguage)));

        return dtoList;
    }


    public Boolean update(Long id, ConditionsRequestDTO dto) {
        Optional<ConditionsEntity> optional = getByName(dto.getNameEn(), dto.getNameUz(), dto.getNameRu());
        if (optional.isPresent()) {
            throw new CategoryAlreadyExistsException("Conditions already exists");
        }

        ConditionsEntity category = getEntityById(id);
        category.setNameUz(dto.getNameUz());
        category.setNameEn(dto.getNameEn());
        category.setNameRu(dto.getNameRu());
        conditionsRepository.save(category);
        return true;
    }

    public Boolean delete(Long id) {
        ConditionsEntity entity = getEntityById(id);
        if (!entity.getVisible()) {
            return true;
        }
        int n = conditionsRepository.updateVisible(id);
        return n > 0;
    }

    public ConditionsEntity getEntityById(Long id) {
        return conditionsRepository.findById(id).orElseThrow(() -> {
            log.warn("Conditions not found: {}", id);
            throw new ItemNotFoundException("Conditions not found");
        });
    }

    public ConditionsResponseDTO getConditionsById(Long id, AppLang lang) {
        return toDto(getEntityById(id), lang);
    }

    public Optional<ConditionsEntity> getByName(String en, String uz, String ru) {
        return conditionsRepository.findByNameEnAndNameUzAndNameRuAndVisibleTrue(en, uz, ru);
    }

    public ConditionsResponseDTO toDto(ConditionsEntity entity, AppLang appLanguage) {
        ConditionsResponseDTO dto = new ConditionsResponseDTO();
        switch (appLanguage) {
            case uz -> dto.setName(entity.getNameUz());
            case ru -> dto.setName(entity.getNameRu());
            case en -> dto.setName(entity.getNameEn());
        }
        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }
}
