package com.company.dto.response;

import com.company.dto.request.CarTypeRequestDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CarTypeResponseDTO extends CarTypeRequestDTO {
    private Long id;
    private LocalDateTime createdDate;
}
