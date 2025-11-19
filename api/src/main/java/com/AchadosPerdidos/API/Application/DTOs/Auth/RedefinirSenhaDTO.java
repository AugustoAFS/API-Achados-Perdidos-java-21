package com.AchadosPerdidos.API.Application.DTOs.Auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RedefinirSenhaDTO {
    private String Cpf_Usuario;
    private String Matricula;
    private String Nova_Senha;

}
