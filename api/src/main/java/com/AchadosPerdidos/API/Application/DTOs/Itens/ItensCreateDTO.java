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
@Schema(description = "DTO para criação de item")
public class ItensCreateDTO {
    @Schema(description = "Nome do item", example = "Chave do Laboratório", required = true)
    private String nome;

    @Schema(description = "Descrição do item", example = "Chave do laboratório de informática, cor prata", required = true)
    private String descricao;

    @Schema(description = "ID do status do item", example = "1", required = true)
    private Integer statusItemId;

    @Schema(description = "ID do local onde o item foi encontrado", example = "2", required = true)
    private Integer localId;

    @Schema(description = "Data/hora em que o item foi encontrado", example = "2024-01-01T10:30:00")
    private Date encontradoEm;
}
