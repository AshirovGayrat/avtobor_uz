package com.company.service;

import com.company.dto.request.CategoryRequestDTO;
import com.company.dto.response.CategoryResponceDTO;
import com.company.entity.CategoryEntity;
import com.company.enums.AppLang;
import com.company.exp.CategoryAlreadyExistsException;
import com.company.exp.ItemNotFoundException;
import com.company.repository.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryResponceDTO create(CategoryRequestDTO dto) {
        Optional<CategoryEntity> optional = getByName(dto.getNameEn(), dto.getNameUz(), dto.getNameRu());
        if (optional.isPresent()) {
            throw new CategoryAlreadyExistsException("Category already exists");
        }

        CategoryEntity entity = new CategoryEntity();
        entity.setNameEn(dto.getNameEn());
        entity.setNameRu(dto.getNameRu());
        entity.setNameUz(dto.getNameUz());
        categoryRepository.save(entity);
        return toDto(entity, AppLang.uz);
    }

    public PageImpl<CategoryResponceDTO> getAllWithPagination(AppLang appLanguage, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdDate"));
        Page<CategoryEntity> categoryEntityPage = categoryRepository.findAllByVisibleTrue(pageable);

        List<CategoryEntity> categoryEntityList = categoryEntityPage.getContent();
        long totalContent = categoryEntityPage.getTotalElements();
        List<CategoryResponceDTO> dtoList = new ArrayList<>();

        categoryEntityList.forEach(entity -> dtoList.add(toDto(entity, appLanguage)));

        return new PageImpl<CategoryResponceDTO>(dtoList, pageable, totalContent);
    }


    public Boolean update(Long id, CategoryRequestDTO dto) {
        Optional<CategoryEntity> optional = getByName(dto.getNameEn(), dto.getNameUz(), dto.getNameRu());
        if (optional.isPresent()) {
            throw new CategoryAlreadyExistsException("Category already exists");
        }

        CategoryEntity category = getEntityById(id);
        category.setNameUz(dto.getNameUz());
        category.setNameEn(dto.getNameEn());
        category.setNameRu(dto.getNameRu());
        categoryRepository.save(category);
        return true;
    }

    public Boolean delete(Long id) {
        CategoryEntity entity = getEntityById(id);
        if (!entity.getVisible()) {
            return true;
        }
        int n = categoryRepository.updateVisible(id);
        return n > 0;
    }

    public CategoryEntity getEntityById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> {
            log.warn("Category not found: {}", id);
            throw new ItemNotFoundException("Category not found");
        });
    }

    public CategoryResponceDTO getCategoryById(Long id, AppLang lang) {
        return toDto(getEntityById(id), lang);
    }

    public Optional<CategoryEntity> getOptionalById(Long id) {
        return categoryRepository.findById(id);
    }

    public Optional<CategoryEntity> getByName(String en, String uz, String ru) {
        return categoryRepository.findByNameEnAndNameUzAndNameRuAndVisibleTrue(en, uz, ru);
    }

    public CategoryResponceDTO toDto(CategoryEntity entity, AppLang appLanguage) {
        CategoryResponceDTO dto = new CategoryResponceDTO();
        switch (appLanguage) {
            case uz -> dto.setNameUz(entity.getNameUz());
            case ru -> dto.setNameRu(entity.getNameRu());
            case en -> dto.setNameEn(entity.getNameEn());
        }
        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

}
