package com.AchadosPerdidos.API.Application.DTOs.Cidade;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para criação de Cidade")
public class CidadeCreateDTO {
    private String nome;
    private Integer estadoId;
}


