package com.company.dto;

import com.company.enums.ProfileRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProfileJwtDTO {
    private String phone;//username
    private ProfileRole role;
}
