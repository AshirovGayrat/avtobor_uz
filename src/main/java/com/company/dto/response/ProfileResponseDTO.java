package com.company.dto.response;

import com.company.dto.request.ProfileUpdateDto;
import com.company.enums.ProfileStatus;
import com.company.enums.ProfileType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ProfileResponseDTO extends ProfileUpdateDto {
    private Long id;
    private LocalDateTime createdDate;
    private ProfileStatus status;
    private ProfileType type;
    private String token;
}
