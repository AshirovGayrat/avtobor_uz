package com.company.dto.response;

import com.company.dto.AttachSimpleDTO;
import com.company.dto.request.AdvertRequestDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AdvertResponseDTO extends AdvertRequestDTO {
    private Long id;
    private LocalDateTime createdDate;
    private Long profileId;
    private List<AttachSimpleDTO> attachList;

    private String category;
    private String carType;
    private String make;

}
