package com.AchadosPerdidos.API.Application.DTOs.Local;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocalUpdateDTO {
    private String nome;
    private String descricao;
    private Integer campusId;
    private Boolean flgInativo;
}
