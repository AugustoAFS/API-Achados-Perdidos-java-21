package com.AchadosPerdidos.API.Application.DTOs.Campus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO completo de campus")
public class CampusDTO {
    @Schema(description = "ID do campus", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer id;

    @Schema(description = "Nome do campus", example = "IFPR - Sede Curitiba")
    private String nome;

    @Schema(description = "ID da instituição", example = "1")
    private Integer instituicaoId;

    @Schema(description = "ID do endereço", example = "10")
    private Integer enderecoId;

    @Schema(description = "Data de criação", example = "2024-01-01T00:00:00")
    private java.util.Date dtaCriacao;

    @Schema(description = "Flag de inativação", example = "false")
    private Boolean flgInativo;

    @Schema(description = "Data de remoção lógica", example = "2024-02-01T00:00:00")
    private java.util.Date dtaRemocao;
}
