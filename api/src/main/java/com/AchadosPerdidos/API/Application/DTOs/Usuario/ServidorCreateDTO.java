package com.AchadosPerdidos.API.Application.DTOs.Usuario;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO específico para cadastro de SERVIDOR
 * Não inclui matrícula, pois servidores são identificados por CPF
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServidorCreateDTO {
    private String nomeCompleto;
    private String cpf; // Obrigatório para servidor
    private String email;
    private String senha;
    private String numeroTelefone;
    private Integer enderecoId;
    private Integer campusId;
}

