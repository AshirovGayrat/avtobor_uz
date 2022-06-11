package com.company.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
@NoArgsConstructor
public class LoginResponse {
    private Boolean status;

    private String title;

    private String message;

    private ProfileResponseDTO profile;

    public LoginResponse(Boolean status, ProfileResponseDTO profile) {
        this.status=status;
        this.profile = profile;
    }

    public LoginResponse(Boolean status, String message) {
        this.status = status;
        this.message = message;
    }

}

/*    val title: String? = null, // Incorrect Sms code
    val message: String? = null, // The code you entered is incorrect. Please try again*/