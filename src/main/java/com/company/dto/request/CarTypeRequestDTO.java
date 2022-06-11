package com.company.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CarTypeRequestDTO {
    @NotNull
    private String nameUz;
    @NotNull
    private String nameRu;
    @NotNull
    private String nameEn;
}
