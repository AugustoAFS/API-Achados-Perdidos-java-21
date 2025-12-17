package com.AchadosPerdidos.API.Application.DTOs.Auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDTO {
    @JsonProperty("Email_Usuario")
    private String emailUsuario;
    
    @JsonProperty("Senha_Hash")
    private String senhaHash;
    
    @JsonProperty("Device_Token")
    private String deviceToken;
    
    @JsonProperty("Plataforma")
    private String plataforma;
}
