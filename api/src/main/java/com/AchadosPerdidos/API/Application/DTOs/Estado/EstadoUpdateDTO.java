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
@Schema(description = "DTO para atualização de Estado")
public class EstadoUpdateDTO {
    private String nome;
    private String uf;
    private Boolean flgInativo;
}


