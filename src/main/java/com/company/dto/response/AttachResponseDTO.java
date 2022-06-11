package com.company.dto.response;

import com.company.dto.request.AttachRequestDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AttachResponseDTO extends AttachRequestDTO {
    private String extension;
    private String path;
    private String url;
    private String originName;
    private Long size;
    private LocalDateTime createdDate;

    public AttachResponseDTO(String url) {
        this.url = url;
    }
}
