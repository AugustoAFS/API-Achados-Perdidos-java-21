package com.AchadosPerdidos.API.Application.DTOs.ItensReivindicados;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Schema(description = "DTO para lista de itens reivindicados")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItensReivindicadosListDTO {
    
    @Schema(description = "Lista de itens reivindicados")
    private List<ItensReivindicadosDTO> itensReivindicados;
    
    @Schema(description = "Total de reivindicações na lista")
    private int totalCount;

}

