package com.AchadosPerdidos.API.Application.DTOs.Cidade;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO para criacao de Cidade")

public class CidadeCreateDTO{
    private String nome;
    private Integer estadoId;

    public CidadeCreateDTO() {
        // Construtor padrao para frameworks de serializacao
    }

    public CidadeCreateDTO(String nome, Integer estadoId) {
        this.nome = nome;
        this.estadoId = estadoId;
    }

    public String getNome() {
        return nome;
    }
    public Integer getEstadoId() {
        return estadoId;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    public void setEstadoId(Integer estadoId) {
        this.estadoId = estadoId;
    }

}

