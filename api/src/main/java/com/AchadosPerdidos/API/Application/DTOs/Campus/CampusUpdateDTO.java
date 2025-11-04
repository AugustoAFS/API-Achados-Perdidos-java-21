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
@Schema(description = "DTO para atualização de campus")
public class CampusUpdateDTO {
    @Schema(description = "Nome do campus", example = "IFPR - Sede Curitiba")
    private String nome;

    @Schema(description = "ID da instituição", example = "1")
    private Integer instituicaoId;

    @Schema(description = "ID do endereço", example = "10")
    private Integer enderecoId;

    @Schema(description = "Flag de inativação", example = "false")
    private Boolean flgInativo;
}
