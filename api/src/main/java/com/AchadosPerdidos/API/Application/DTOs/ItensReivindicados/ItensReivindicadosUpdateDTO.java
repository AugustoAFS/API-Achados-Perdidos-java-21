package com.AchadosPerdidos.API.Application.DTOs.ItensReivindicados;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(description = "DTO para atualização de item reivindicado")
@Getter

public class ItensReivindicadosUpdateDTO {
    
    @Schema(description = "Detalhes da reivindicação", example = "Este item me pertence, perdi na biblioteca")
    private String detalhesReivindicacao;
    
    @Schema(description = "ID do usuário que achou o item", example = "2")
    private Integer usuarioAchouId;
    
    @Schema(description = "Flag indicando se está inativo", example = "false")
    private Boolean flgInativo;
}

