package com.AchadosPerdidos.API.Application.DTOs.ItensReivindicados;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "DTO para criação de item reivindicado")
@Getter
@Setter
@AllArgsConstructor
public class ItensReivindicadosCreateDTO {
    
    @Schema(description = "Detalhes da reivindicação", example = "Este item me pertence, perdi na biblioteca", required = true)
    private String detalhesReivindicacao;
    
    @Schema(description = "ID do item reivindicado", example = "1", required = true)
    private int itemId;
    
    @Schema(description = "ID do usuário que fez a reivindicação", example = "1", required = true)
    private int usuarioReivindicadorId;

}

