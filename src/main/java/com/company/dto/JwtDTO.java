package com.company.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtDTO {

    private String phoneNumber;

    public JwtDTO(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
