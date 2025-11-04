package com.AchadosPerdidos.API.Application.DTOs.Cidade;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Schema(description = "DTO para lista de cidades")
@Getter
@Setter
@AllArgsConstructor
public class CidadeListDTO {
    
    @Schema(description = "Lista de cidades")
    private List<CidadeDTO> Cidades;
}

