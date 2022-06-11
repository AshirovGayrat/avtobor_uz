package com.company.controller;

import com.company.dto.request.AdvertRequestDTO;
import com.company.service.AdvertService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/advert")
@RequiredArgsConstructor
public class AdvertController {
    private final AdvertService advertService;

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody AdvertRequestDTO dto){
        log.info("Advert create: {}", dto);
        return ResponseEntity.ok(advertService.create(dto));
    }

}
