package com.company.dto.response;

import com.company.dto.request.MakeRequestDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MakeResponseDTO extends MakeRequestDTO {
    private Long id;
    private LocalDateTime createdDate;
}
