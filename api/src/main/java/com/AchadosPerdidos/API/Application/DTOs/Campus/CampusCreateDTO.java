package com.AchadosPerdidos.API.Application.DTOs.Campus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CampusCreateDTO {
    private String nome;
    private Integer instituicaoId;
    private Integer enderecoId;
}
