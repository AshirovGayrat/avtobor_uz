package com.company.service;

import com.company.dto.LoginDTO;
import com.company.dto.request.ProfileRequestDTO;
import com.company.dto.response.LoginResponse;
import com.company.dto.response.ProfileResponseDTO;
import com.company.dto.response.RegisterResponseDTO;
import com.company.dto.response.ResponseMessageDTO;
import com.company.entity.ProfileDetailEntity;
import com.company.entity.ProfileEntity;
import com.company.enums.ProfileStatus;
import com.company.repository.ProfileRepository;
import com.company.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {
    private final ProfileRepository profileRepository;
    private final ProfileService profileService;


    public ResponseEntity<RegisterResponseDTO> registration(ProfileRequestDTO dto) {
        RegisterResponseDTO response = new RegisterResponseDTO();

        ResponseMessageDTO messageDTO = profileService.createProfile(dto);
        if (!messageDTO.getStatus()) {
            response.setMessage(messageDTO.getMessage());
            return ResponseEntity.badRequest().body(response);
        }

        ProfileEntity entity = profileService.getProfileByPhone(dto.getPhone());
        ProfileResponseDTO responseDTO = profileService.toDTO(entity);
        responseDTO.setToken(JwtUtil.encode(responseDTO.getPhone()));

        return ResponseEntity.ok(new RegisterResponseDTO(true, responseDTO));
    }


    public ResponseEntity<LoginResponse> login(LoginDTO dto) {
        Optional<ProfileEntity> optional;
        if (dto.getEmailOrPhone().startsWith("998") || dto.getEmailOrPhone().startsWith("+9989")) {
            optional = profileRepository.findByPhoneAndVisibleTrue(dto.getEmailOrPhone());
        } else {
            optional = profileRepository.findByEmailAndVisibleTrue(dto.getEmailOrPhone());
        }

        if (optional.isEmpty()) {
            log.warn("Bad request {}", dto);
            return ResponseEntity.badRequest().
                    body(new LoginResponse(false, "Email, Phone or password wrong"));
        }

        ProfileEntity entity = optional.get();

        if (!entity.getStatus().equals(ProfileStatus.ACTIVE)) {
            log.warn("This Profile not active: {}", dto);
            return ResponseEntity.badRequest().
                    body(new LoginResponse(false, "This Profile not active"));
        }

        ProfileResponseDTO responseDTO = profileService.toDTO(entity);

        return ResponseEntity.ok(new LoginResponse(true, responseDTO));
    }


}
