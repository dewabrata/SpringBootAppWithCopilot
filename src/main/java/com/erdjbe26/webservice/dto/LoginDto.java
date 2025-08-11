package com.erdjbe26.webservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginDto {
    @NotBlank
    private String usernameOrEmail;

    @NotBlank
    private String password;
}
