package com.company.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class ChangePswdDTO {
    @NotNull
    @Size(min = 4)
    private String newPassword;
    @NotNull
    @Size(min = 4, max = 25)
    private String oldPassword;
}
