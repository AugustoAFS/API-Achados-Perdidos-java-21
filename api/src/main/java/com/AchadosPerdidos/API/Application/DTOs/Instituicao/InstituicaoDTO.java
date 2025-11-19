package com.AchadosPerdidos.API.Application.DTOs.Instituicao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InstituicaoDTO {
    private Integer id;
    private String nome;
    private String codigo;
    private String tipo;
    private String cnpj;
    private LocalDateTime dtaCriacao;
    private Boolean flgInativo;
    private LocalDateTime dtaRemocao;


    public LocalDateTime getDtaRemocao() {
        return dtaRemocao;
    }

    public void setDtaRemocao(LocalDateTime dtaRemocao) {
        this.dtaRemocao = dtaRemocao;
    }
}

