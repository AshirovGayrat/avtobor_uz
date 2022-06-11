package com.company.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ConditionsResponseDTO {
    private Long id;
    private String name;
    private LocalDateTime createdDate;
}
