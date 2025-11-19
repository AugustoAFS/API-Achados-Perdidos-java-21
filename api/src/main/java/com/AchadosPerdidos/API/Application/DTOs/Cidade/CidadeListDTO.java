package com.AchadosPerdidos.API.Application.DTOs.Cidade;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CidadeListDTO {
    private List<CidadeDTO> Cidades;
}