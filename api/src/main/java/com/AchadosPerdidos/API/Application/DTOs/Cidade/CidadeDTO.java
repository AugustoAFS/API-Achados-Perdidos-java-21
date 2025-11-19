package com.AchadosPerdidos.API.Application.DTOs.Cidade;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CidadeDTO {
    private Integer id;
    private String nome;
    private Integer estadoId;
    private LocalDateTime dtaCriacao;
    private Boolean flgInativo;
    private LocalDateTime dtaRemocao;
}