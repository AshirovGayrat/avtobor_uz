package com.company.dto;

import com.company.enums.AppLang;
import lombok.Data;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class LoginDTO {
    @NotNull
    @Size(min = 12)
    private String emailOrPhone;
    @NotNull
    @Size(min = 4, max = 15)
    private String password;

    @NotNull(message = "AppLanguage required")
    private AppLang appLang;
}
