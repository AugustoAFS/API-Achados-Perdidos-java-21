package com.AchadosPerdidos.API.Application.DTOs.Reivindicacoes;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para lista de reivindicações")
public class ReivindicacoesListDTO {
    
    @Schema(description = "Lista de reivindicações")
    private List<ReivindicacoesDTO> reivindicacoes;
    
    @Schema(description = "Total de reivindicações na lista")
    private int totalCount;
}
