package com.company.controller;

import com.company.dto.request.CarTypeRequestDTO;
import com.company.dto.response.CarTypeResponseDTO;
import com.company.enums.AppLang;
import com.company.service.CarTypeSerVice;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/car-type")
@RequiredArgsConstructor
public class CarTypeController {
    private final CarTypeSerVice carTypeService;

    @ApiOperation(value = "create", notes = "Method for create Car type")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody CarTypeRequestDTO dto) {
        return ResponseEntity.ok(carTypeService.create(dto));
    }

    @ApiOperation(value = "getAll", notes = "Method used for get all Car types")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/pagination/{lang}")
    public ResponseEntity<?> getAll(@PathVariable("lang") AppLang lang) {
        return ResponseEntity.ok(carTypeService.getAllCarTypeList(lang));
    }

    @ApiOperation(value = "get", notes = "Method used for get Car type by id")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/{id}/{lang}")
    public ResponseEntity<CarTypeResponseDTO> getById(@PathVariable("id") Long id,
                                                      @PathVariable("lang") AppLang lang) {
        return ResponseEntity.ok(carTypeService.getCarTypeById(id, lang));
    }

    @ApiOperation(value = "update", notes = "Method for update Car type")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/adm/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody CarTypeRequestDTO dto) {
        return ResponseEntity.ok(carTypeService.update(id, dto));
    }

    @ApiOperation(value = "delete", notes = "Method used for delete Car type")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/adm/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok(carTypeService.delete(id));
    }

}
