package com.AchadosPerdidos.API.Application.DTOs.Cidade;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CidadeCreateDTO {
    private String nome;
    private Integer estadoId;

}

