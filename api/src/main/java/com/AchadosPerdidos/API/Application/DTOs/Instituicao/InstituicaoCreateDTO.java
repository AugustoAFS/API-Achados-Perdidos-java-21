package com.AchadosPerdidos.API.Application.DTOs.Instituicao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InstituicaoCreateDTO {
    private String nome;
    private String codigo;
    private String tipo;
    private String cnpj;
}
