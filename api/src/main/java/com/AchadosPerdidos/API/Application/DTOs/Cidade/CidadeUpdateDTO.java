package com.AchadosPerdidos.API.Application.DTOs.Cidade;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO para atualizacao de Cidade")

public class CidadeUpdateDTO{
    private String nome;
    private Integer estadoId;
    private Boolean flgInativo;

    public CidadeUpdateDTO() {
        // Construtor padrao para frameworks de serializacao
    }

    public CidadeUpdateDTO(String nome, Integer estadoId, Boolean flgInativo) {
        this.nome = nome;
        this.estadoId = estadoId;
        this.flgInativo = flgInativo;
    }

    public String getNome() {
        return nome;
    }

    public Integer getEstadoId() {
        return estadoId;
    }

    public Boolean getFlgInativo() {
        return flgInativo;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEstadoId(Integer estadoId) {
        this.estadoId = estadoId;
    }

    public void setFlgInativo(Boolean flgInativo) {
        this.flgInativo = flgInativo;
    }
}