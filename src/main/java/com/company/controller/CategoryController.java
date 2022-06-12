package com.company.controller;

import com.company.dto.request.CategoryRequestDTO;
import com.company.enums.AppLang;
import com.company.service.CategoryService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/category")
@Slf4j
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @ApiOperation(value = "create", notes = "Method for create category", nickname = "Mazgi")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody CategoryRequestDTO dto) {
        return ResponseEntity.ok(categoryService.create(dto));
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/pagination/{lang}")
    public ResponseEntity<?> getAll(@PathVariable("lang") AppLang lang) {
        return ResponseEntity.ok(categoryService.getCategoryList(lang));
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/{id}/{lang}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id,
                                     @PathVariable("lang") AppLang lang) {
        return ResponseEntity.ok(categoryService.getCategoryById(id, lang));
    }

    @ApiOperation(value = "update", notes = "Method for update category", nickname = "Mazgi")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody CategoryRequestDTO dto) {
        return ResponseEntity.ok(categoryService.update(id, dto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok(categoryService.delete(id));
    }

}
