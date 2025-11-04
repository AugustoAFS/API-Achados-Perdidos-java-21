package com.AchadosPerdidos.API.Application.DTOs.Usuario;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO completo de usuário")
public class UsuariosDTO {
    @Schema(description = "ID do usuário", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer id;

    @Schema(description = "Nome completo", example = "João Silva")
    private String nomeCompleto;

    @Schema(description = "CPF", example = "12345678901")
    private String cpf;

    @Schema(description = "Email", example = "joao@ifpr.edu.br")
    private String email;

    @Schema(description = "Senha (hash)", example = "$2a$10$...", accessMode = Schema.AccessMode.WRITE_ONLY)
    private String hashSenha;

    @Schema(description = "Matrícula", example = "2024001")
    private String matricula;

    @Schema(description = "Telefone", example = "(41) 99999-9999")
    private String numeroTelefone;

    @Schema(description = "Empresa ID", example = "10")
    private Integer empresaId;

    @Schema(description = "Endereço ID", example = "5")
    private Integer enderecoId;

    @Schema(description = "Data de criação", example = "2024-01-01T00:00:00")
    private Date dtaCriacao;

    @Schema(description = "Flag de inativação", example = "false")
    private Boolean flgInativo;

    @Schema(description = "Data de remoção lógica", example = "2024-02-01T00:00:00")
    private Date dtaRemocao;
}