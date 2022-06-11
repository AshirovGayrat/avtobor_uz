package com.company.controller;

import com.company.dto.request.CarTypeRequestDTO;
import com.company.enums.ProfileRole;
import com.company.service.CarTypeSerVice;
import com.company.util.JwtUtil;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/api/v1/car-type")
@RequiredArgsConstructor
public class CarTypeController {
    private final CarTypeSerVice carTypeService;

    @ApiOperation(value = "create", notes = "Method for create Car type", nickname = "Mazgi")
    @PostMapping("/adm")
    public ResponseEntity<?> create(@RequestBody CarTypeRequestDTO dto,
                                    HttpServletRequest request) {
        return ResponseEntity.ok(carTypeService.create(dto));
    }

    @GetMapping("/adm/pagination")
    public ResponseEntity<?> getAll(@RequestParam(value = "page", defaultValue = "0")int page,
                                    @RequestParam(value = "size", defaultValue = "10")int size,
                                    HttpServletRequest request){
        return ResponseEntity.ok(carTypeService.getAllWithPagination(page, size));
    }

    @ApiOperation(value = "update", notes = "Method for update Car type", nickname = "Mazgi")
    @PutMapping("/adm/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody CarTypeRequestDTO dto,
                                    HttpServletRequest request) {
        return ResponseEntity.ok(carTypeService.update(id, dto));
    }

    @DeleteMapping("/adm/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id,
                                    HttpServletRequest request) {
        return ResponseEntity.ok(carTypeService.delete(id));
    }

}
