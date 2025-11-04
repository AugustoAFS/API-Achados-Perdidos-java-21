package com.AchadosPerdidos.API.Application.DTOs.Estado;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para criação de Estado")
public class EstadoCreateDTO {
    private String nome;
    private String uf;
}


