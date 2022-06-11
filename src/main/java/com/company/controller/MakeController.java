package com.company.controller;

import com.company.dto.request.MakeRequestDTO;
import com.company.service.MakeService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/api/v1/make")
@RequiredArgsConstructor
public class MakeController {
private final MakeService makeService;

    @ApiOperation(value = "create", notes = "Method for create make")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody MakeRequestDTO dto) {
        log.info("Create make: {}", dto);
        return ResponseEntity.ok(makeService.create(dto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/pagination")
    public ResponseEntity<?> getAll(@RequestParam(value = "page", defaultValue = "0")int page,
                                    @RequestParam(value = "size", defaultValue = "10")int size){
        return ResponseEntity.ok(makeService.getAllWithPagination(page, size));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id")Long id){
        return ResponseEntity.ok(makeService.getMakeById(id));
    }

    @ApiOperation(value = "update", notes = "Method for update make")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody MakeRequestDTO dto) {
        log.info("Update make: {}", dto);
        return ResponseEntity.ok(makeService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        log.info("Delete make {}", id);
        return ResponseEntity.ok(makeService.delete(id));
    }
}
