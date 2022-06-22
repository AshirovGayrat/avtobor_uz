package com.company.controller;

import com.company.dto.FilterDTO;
import com.company.dto.request.AdvertRequestDTO;
import com.company.enums.AppLang;
import com.company.service.AdvertService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/advert")
@RequiredArgsConstructor
@Api(tags = "Advert")
public class AdvertController {
    private final AdvertService advertService;

    @ApiOperation(value = "create", notes = "Method used for create advert",
            authorizations = @Authorization(value = "JWT Token"))
    @PreAuthorize("hasRole('USER')")
    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody AdvertRequestDTO dto) {
        log.info("Advert create: {}", dto);
        return ResponseEntity.ok(advertService.create(dto));
    }


//    @ApiOperation(value = "update", notes = "Method used for update advert",
//            authorizations = @Authorization(value = "JWT Token"))
//    @PreAuthorize("hasRole('USER')")
//    @PutMapping("/update/{id}")
//    public ResponseEntity<?> update(@PathVariable("id") Long id,
//                                    @RequestBody AdvertRequestDTO dto) {
//        log.info("Advert update: {}", dto);
//        return ResponseEntity.ok(advertService.update(id, dto));
//    }


    @ApiOperation(value = "delete", notes = "Method used for delete advert",
            authorizations = @Authorization(value = "JWT Token"))
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id")Long id){
        log.info("Delete advert: {}", id);
        return ResponseEntity.ok(advertService.delete(id));
    }


    @ApiOperation(value = "getById", notes = "Method used for get advert By Id",
            authorizations = @Authorization(value = "JWT Token"))
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/{id}/{lang}")
    public ResponseEntity<?> getById(@PathVariable("id")Long id,
                                     @PathVariable("lang")AppLang lang){
        log.info("Get By Id advert: {}", id);
        return ResponseEntity.ok(advertService.getAdvertById(id, lang));
    }


    @ApiOperation(value = "filter", notes = "Method used for get adverts By filter",
            authorizations = @Authorization(value = "JWT Token"))
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/filter/{lang}")
    public ResponseEntity<?> filter(@PathVariable("lang")AppLang lang,
                                    @RequestBody FilterDTO dto){
        log.info("Get adverts By filter: {}", dto);
        return ResponseEntity.ok(advertService.filter(dto, lang));
    }

}
