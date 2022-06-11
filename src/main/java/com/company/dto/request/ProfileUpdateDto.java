package com.company.dto.request;

import com.company.enums.ProfileType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileUpdateDto {
    @NotNull
    @Size(min = 3, max = 15, message = "name size min=3 , max=15")
    private String name;
    @NotNull
    @Size(min = 3, max = 15, message = "name size min=3 , max=15")
    private String surname;
    @Email
    private String email;
    @NotNull
    @Size(min = 12, max = 13, message = "name size min=3 , max=15")
    private String phone;

    private ProfileType profileType;

    private String webSite;
    private String facebook;
    private String about;
}
