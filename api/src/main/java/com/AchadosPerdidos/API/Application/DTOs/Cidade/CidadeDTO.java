package com.AchadosPerdidos.API.Application.DTOs.Cidade;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO de Cidade")
public class CidadeDTO {
    private Integer id;
    private String nome;
    private Integer estadoId;
    private Date dtaCriacao;
    private Boolean flgInativo;
    private Date dtaRemocao;
}


