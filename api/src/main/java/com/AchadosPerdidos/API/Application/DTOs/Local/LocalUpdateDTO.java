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
@Schema(description = "DTO para atualização de Local")
public class LocalUpdateDTO {
    private String nome;
    private String descricao;
    private Integer campusId;
    private Boolean flgInativo;
}


