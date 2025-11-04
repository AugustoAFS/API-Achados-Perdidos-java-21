package com.AchadosPerdidos.API.Application.DTOs.Local;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para criação de Local")
public class LocalCreateDTO {
    private String nome;
    private String descricao;
    private Integer campusId;
}


