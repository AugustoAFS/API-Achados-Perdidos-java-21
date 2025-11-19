package com.AchadosPerdidos.API.Application.DTOs.Usuario;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuariosUpdateDTO {
    private String nomeCompleto;
    private String cpf;
    private String email;
    private String matricula;
    private Integer enderecoId;
    private Boolean flgInativo;
}
