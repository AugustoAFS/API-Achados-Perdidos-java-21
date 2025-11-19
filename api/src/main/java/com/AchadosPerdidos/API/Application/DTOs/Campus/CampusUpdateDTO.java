package com.AchadosPerdidos.API.Application.DTOs.Campus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CampusUpdateDTO {
    private String nome;
    private Integer instituicaoId;
    private Integer enderecoId;
    private Boolean flgInativo;
}