package com.company.controller;

import com.company.dto.request.ConditionsRequestDTO;
import com.company.enums.AppLang;
import com.company.service.ConditionsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "Conditions")
public class ConditionsController {
    private final ConditionsService conditionsService;

    @ApiOperation(value = "create", notes = "Method for create conditions")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody ConditionsRequestDTO dto) {
        log.info("create conditions: {}", dto);
        return ResponseEntity.ok(conditionsService.create(dto));
    }

    @ApiOperation(value = "get all", notes = "Method used for get all conditions paginayion")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/{lang}")
    public ResponseEntity<?> getAll(@PathVariable("lang") AppLang lang) {
        return ResponseEntity.ok(conditionsService.getConditionsList(lang));
    }

    @ApiOperation(value = "get", notes = "Method used for get condition by id")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/{id}/{lang}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id,
                                     @PathVariable("lang") AppLang lang) {
        return ResponseEntity.ok(conditionsService.getConditionsById(id, lang));
    }

    @ApiOperation(value = "update", notes = "Method for update category", nickname = "Mazgi")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody ConditionsRequestDTO dto) {
        log.info("update conditions: {}", dto);
        return ResponseEntity.ok(conditionsService.update(id, dto));
    }

    @ApiOperation(value = "delete", notes = "Method used for deleted conditions")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        log.info("Delete conditions: {}", id);
        return ResponseEntity.ok(conditionsService.delete(id));
    }
}
