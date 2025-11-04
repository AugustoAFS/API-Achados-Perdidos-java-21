package com.AchadosPerdidos.API.Application.DTOs.Estado;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Schema(description = "DTO para lista de estados")
@Getter
@Setter
@AllArgsConstructor
public class EstadoListDTO {
    
    @Schema(description = "Lista de estados")
    private List<EstadoDTO> Estados;
}

