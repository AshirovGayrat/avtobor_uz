package com.company.dto;

import com.company.enums.AppLang;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@ToString
public class ProfileDetailDTO {

    @NotBlank(message = "PhoneNumber required")
    private String phoneNumber;

    private String smsCode;
    private String sms;

    @NotNull(message = "AppLanguage required")
    private AppLang appLanguage;

}
