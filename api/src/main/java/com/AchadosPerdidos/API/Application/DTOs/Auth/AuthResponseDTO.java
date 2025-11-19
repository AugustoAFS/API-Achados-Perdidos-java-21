package com.AchadosPerdidos.API.Application.DTOs.Auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponseDTO {
    private String Token;
    private String Token_Type;
    private long Expires_Token;
    private int Usuario_Id;
    private String Nome_Usuario;
    private String Email_Usuario;
    private String Role_Usuario;
    private String campus;

}