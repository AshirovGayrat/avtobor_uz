package com.company.dto.response;

import com.company.dto.request.CategoryRequestDTO;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CategoryResponceDTO extends CategoryRequestDTO {
    private Long id;
    private LocalDateTime createdDate;
}
