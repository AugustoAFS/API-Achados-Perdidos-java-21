package com.AchadosPerdidos.API.Application.DTOs.Reivindicacoes;

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
@Schema(description = "DTO completo de reivindicação")
public class ReivindicacoesDTO {
    @Schema(description = "ID da reivindicação", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer id;

    @Schema(description = "Detalhes da reivindicação", example = "Este item me pertence, perdi na biblioteca")
    private String detalhesReivindicacao;

    @Schema(description = "ID do item", example = "1")
    private Integer itemId;

    @Schema(description = "ID do usuário reivindicador", example = "1")
    private Integer usuarioReivindicadorId;

    @Schema(description = "ID do usuário que achou o item", example = "2")
    private Integer usuarioAchouId;

    @Schema(description = "Data de criação", example = "2024-01-01T00:00:00")
    private Date dtaCriacao;

    @Schema(description = "Flag de inativação", example = "false")
    private Boolean flgInativo;

    @Schema(description = "Data de remoção lógica", example = "2024-02-01T00:00:00")
    private Date dtaRemocao;
}
