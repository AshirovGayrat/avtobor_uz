package com.company.controller;

import com.company.dto.request.ChangePswdDTO;
import com.company.dto.request.ProfileRequestDTO;
import com.company.dto.request.ProfileUpdateDto;
import com.company.enums.ProfileStatus;
import com.company.service.ProfileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/profile")
@Api(tags = "Profile")
public class ProfileController {
    @Autowired
    private ProfileService profileService;

    @ApiOperation(value = "create", notes = "Method for create profile", nickname = "Mazgi",
            authorizations = @Authorization(value = "JWT Token"))
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("")
    public ResponseEntity<?> createProfile(@RequestBody ProfileRequestDTO dto) {
        log.info("Create Profile: {}", dto);
        return ResponseEntity.ok(profileService.createProfile(dto));
    }

    @ApiOperation(value = "changePswd", notes = "Method for changePswd profile", nickname = "Mazgi",
            authorizations = @Authorization(value = "JWT Token"))
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/change/pswd")
    public ResponseEntity<?> changePswd(@RequestBody ChangePswdDTO dto) {
        log.info("Change password: {}", dto);
        return ResponseEntity.ok(profileService.changePswd(dto));
    }


    @ApiOperation(value = "update", notes = "Method for update profile", nickname = "Mazgi",
            authorizations = @Authorization(value = "JWT Token"))
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PutMapping("/update")
    public ResponseEntity<?> updateProfile(@RequestBody @Valid ProfileUpdateDto dto) {
        log.info("update profile: {}", dto);
        return ResponseEntity.ok(profileService.updateProfile(dto));
    }

    // ADMIN
    @ApiOperation(value = "deleteProfile", notes = "Method for delete Profile", nickname = "Mazgi",
            authorizations = @Authorization(value = "JWT Token"))
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProfile(@PathVariable("id") Long id) {
        log.info("Delete profile: {}", id);
        return ResponseEntity.ok(profileService.delete(id));
    }

    @ApiOperation(value = "updateStatus", notes = "Method for update Profile status", nickname = "Mazgi",
            authorizations = @Authorization(value = "JWT Token"))
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update-status/{id}")
    public ResponseEntity<?> updateStatus(@PathVariable("id") Long id,
                                          @RequestParam("status")ProfileStatus status) {
        log.info("Update profile status: {}", id);
        return ResponseEntity.ok(profileService.updateStatus(status, id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/pagination")
    public ResponseEntity<?> getPaginationList(@RequestParam(value = "page", defaultValue = "0") int page,
                                               @RequestParam(value = "size", defaultValue = "3") int size) {
        log.info("Get profile List Pagination");
        return ResponseEntity.ok(profileService.paginationList(page, size));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        log.info("Get profile by id: {}", id);
        return ResponseEntity.ok(profileService.getById(id));
    }

}
