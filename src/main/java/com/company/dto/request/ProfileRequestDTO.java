package com.company.dto.request;

import com.company.enums.ProfileType;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class ProfileRequestDTO {
    @NotNull
    private String name;
    @NotNull
    private String surname;
    @Email
    private String email;
    @NotNull
    @Size(min = 12, max = 13)
    private String phone;
    @NotNull
    @Size(min = 4)
    private String password;

    @NotNull
    private String about;
    private ProfileType profileType;
}
