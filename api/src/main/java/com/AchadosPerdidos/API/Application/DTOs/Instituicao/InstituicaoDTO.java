package com.AchadosPerdidos.API.Application.DTOs.Instituicao;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO completo de instituição")
public class InstituicaoDTO {
    @Schema(description = "ID da instituição", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer id;

    @Schema(description = "Nome da instituição", example = "Instituto Federal do Paraná")
    private String nome;

    @Schema(description = "Código da instituição", example = "IFPR")
    private String codigo;

    @Schema(description = "Tipo da instituição", example = "PUBLICA")
    private String tipo;

    @Schema(description = "CNPJ", example = "12345678000195")
    private String cnpj;

    @Schema(description = "Data de criação", example = "2024-01-01T00:00:00")
    private java.util.Date dtaCriacao;

    @Schema(description = "Flag de inativação", example = "false")
    private Boolean flgInativo;

    @Schema(description = "Data de remoção lógica", example = "2024-02-01T00:00:00")
    private java.util.Date dtaRemocao;
}
