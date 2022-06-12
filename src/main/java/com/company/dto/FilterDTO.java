package com.company.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class FilterDTO {
    private Long fromPrice;
    private Long toPrice;

    private Double fromMileage;
    private Double toMileage;

    private LocalDate fromYear;
    private LocalDate toYear;

    private Long makeId;
    private Long categoryId;
    private Long carTypeId;

    private String transmission;

}
