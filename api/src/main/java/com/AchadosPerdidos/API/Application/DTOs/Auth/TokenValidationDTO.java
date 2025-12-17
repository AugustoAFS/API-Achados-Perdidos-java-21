package com.AchadosPerdidos.API.Application.DTOs.Auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenValidationDTO {
    private boolean valid;
    private String email;
    private String role;
    private String message;
}
