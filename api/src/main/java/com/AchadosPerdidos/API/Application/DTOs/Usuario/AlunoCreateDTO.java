package com.AchadosPerdidos.API.Application.DTOs.Usuario;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO específico para cadastro de ALUNO
 * Não inclui CPF, pois alunos são identificados por matrícula
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlunoCreateDTO {
    private String nomeCompleto;
    private String email;
    private String senha;
    private String matricula; // Obrigatório para aluno
    private String numeroTelefone;
    private Integer enderecoId;
    private Integer campusId;
}

