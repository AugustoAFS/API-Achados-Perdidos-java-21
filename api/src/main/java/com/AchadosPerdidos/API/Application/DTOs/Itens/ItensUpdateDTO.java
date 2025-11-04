package com.AchadosPerdidos.API.Application.DTOs.Itens;

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
@Schema(description = "DTO para atualização de item")
public class ItensUpdateDTO {
    @Schema(description = "Nome do item", example = "Chave do Laboratório")
    private String nome;

    @Schema(description = "Descrição do item", example = "Chave do laboratório de informática, cor prata")
    private String descricao;

    @Schema(description = "Flag de inativação", example = "false")
    private Boolean flgInativo;

    @Schema(description = "ID do status do item", example = "1")
    private Integer statusItemId;

    @Schema(description = "ID do local onde o item foi encontrado", example = "2")
    private Integer localId;

    @Schema(description = "Data/hora em que o item foi encontrado", example = "2024-01-01T10:30:00")
    private Date encontradoEm;
}
