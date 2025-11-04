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
@Schema(description = "DTO para criação de campus")
public class CampusCreateDTO {
    @Schema(description = "Nome do campus", example = "IFPR - Sede Curitiba", required = true)
    private String nome;

    @Schema(description = "ID da instituição", example = "1", required = true)
    private Integer instituicaoId;

    @Schema(description = "ID do endereço", example = "10", required = true)
    private Integer enderecoId;
}
