package com.company.controller;

import com.company.dto.LoginDTO;
import com.company.dto.request.ProfileRequestDTO;
import com.company.dto.response.LoginResponse;
import com.company.dto.response.RegisterResponseDTO;
import com.company.service.AuthService;
import com.company.service.ProfileService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {
    private final ProfileService profileService;
    private final AuthService authService;

    @ApiOperation(value = "Login", notes = "Method used for login")
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginDTO dto) {
        log.info("Login {}", dto);
        return authService.login(dto);
    }

    @ApiOperation(value = "Registration", notes = "Method used for registration")
    @PostMapping("/registration")
    public ResponseEntity<RegisterResponseDTO> registration(@RequestBody @Valid ProfileRequestDTO dto) {
        log.info("Registration {}", dto);
        return authService.registration(dto);
    }

}
