package com.company.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
@NoArgsConstructor
public class RegisterResponseDTO {

    private Boolean isRegistered;

    private String message;

    private ProfileResponseDTO profile;

    public RegisterResponseDTO(Boolean isRegistered, ProfileResponseDTO profile) {
        this.isRegistered = isRegistered;
        this.profile = profile;
    }

    public RegisterResponseDTO(Boolean isRegistered) {
        this.isRegistered = isRegistered;
    }
}

