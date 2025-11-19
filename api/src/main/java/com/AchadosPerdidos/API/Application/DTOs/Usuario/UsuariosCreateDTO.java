package com.AchadosPerdidos.API.Application.DTOs.Usuario;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuariosCreateDTO {
    private String nomeCompleto;
    private String cpf;
    private String email;
    private String senha;
    private String matricula;
    private String numeroTelefone;
    private Integer enderecoId;
    private Integer campusId;
}
