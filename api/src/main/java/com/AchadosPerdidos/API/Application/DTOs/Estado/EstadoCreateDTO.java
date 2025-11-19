package com.AchadosPerdidos.API.Application.DTOs.Estado;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstadoCreateDTO {
    private String nome;
    private String uf;
}
