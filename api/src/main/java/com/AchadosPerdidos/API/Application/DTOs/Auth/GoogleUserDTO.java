package com.AchadosPerdidos.API.Application.DTOs.Auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoogleUserDTO {
    private String id;
    private String name;
    private String email;
    private String picture;
    private Boolean verifiedEmail;

}