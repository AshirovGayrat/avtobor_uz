package com.company.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResponseMessageDTO {
    private String message;
    private Boolean status;

    public ResponseMessageDTO(String message, Boolean status) {
        this.message = message;
        this.status = status;
    }

    public ResponseMessageDTO(Boolean status) {
        this.status = status;
    }
}
