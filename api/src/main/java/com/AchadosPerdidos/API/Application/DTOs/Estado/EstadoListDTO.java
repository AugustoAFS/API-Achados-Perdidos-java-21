package com.AchadosPerdidos.API.Application.DTOs.Estado;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstadoListDTO {
    private List<EstadoDTO> Estados;
}

