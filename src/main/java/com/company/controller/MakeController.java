package com.company.controller;

import com.company.dto.request.MakeRequestDTO;
import com.company.service.MakeService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/make")
@RequiredArgsConstructor
public class MakeController {
private final MakeService makeService;

    @ApiOperation(value = "create", notes = "Method for create make", nickname = "Mazgi")
    @PostMapping("/adm")
    public ResponseEntity<?> create(@RequestBody MakeRequestDTO dto,
                                    HttpServletRequest request) {
        return ResponseEntity.ok(makeService.create(dto));
    }

    @GetMapping("/adm/pagination")
    public ResponseEntity<?> getAll(@RequestParam(value = "page", defaultValue = "0")int page,
                                    @RequestParam(value = "size", defaultValue = "10")int size,
                                    HttpServletRequest request){
        return ResponseEntity.ok(makeService.getAllWithPagination(page, size));
    }

    @ApiOperation(value = "update", notes = "Method for update make", nickname = "Mazgi")
    @PutMapping("/adm/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody MakeRequestDTO dto,
                                    HttpServletRequest request) {
        return ResponseEntity.ok(makeService.update(id, dto));
    }

    @DeleteMapping("/adm/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id,
                                    HttpServletRequest request) {
        return ResponseEntity.ok(makeService.delete(id));
    }
}
