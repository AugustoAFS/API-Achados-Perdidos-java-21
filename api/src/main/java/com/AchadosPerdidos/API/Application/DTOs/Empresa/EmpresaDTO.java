package com.AchadosPerdidos.API.Application.DTOs.Empresa;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO completo de empresa")
public class EmpresaDTO {
    @Schema(description = "ID da empresa", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer id;

    @Schema(description = "Nome da empresa", example = "Empresa ABC Ltda")
    private String nome;

    @Schema(description = "Nome fantasia", example = "ABC Ltda")
    private String nomeFantasia;

    @Schema(description = "CNPJ", example = "12345678000195")
    private String cnpj;

    @Schema(description = "ID do endereço", example = "10")
    private Integer enderecoId;

    @Schema(description = "Data de criação", example = "2024-01-01T00:00:00")
    private java.util.Date dtaCriacao;

    @Schema(description = "Flag de inativação", example = "false")
    private Boolean flgInativo;

    @Schema(description = "Data de remoção lógica", example = "2024-02-01T00:00:00")
    private java.util.Date dtaRemocao;
}
