package com.AchadosPerdidos.API.Application.DTOs.Estado;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO para atualizacao de Estado")
public class EstadoUpdateDTO {
    private String nome;
    private String uf;
    private Boolean flgInativo;

    public EstadoUpdateDTO() {
        // Construtor padrao requerido para serializacao
    }

    public EstadoUpdateDTO(String nome, String uf, Boolean flgInativo) {
        this.nome = nome;
        this.uf = uf;
        this.flgInativo = flgInativo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public Boolean getFlgInativo() {
        return flgInativo;
    }

    public void setFlgInativo(Boolean flgInativo) {
        this.flgInativo = flgInativo;
    }
}

