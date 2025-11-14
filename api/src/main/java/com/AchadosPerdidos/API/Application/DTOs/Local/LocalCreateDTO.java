package com.AchadosPerdidos.API.Application.DTOs.Local;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO para criacao de Local")
public class LocalCreateDTO {
    private String nome;
    private String descricao;
    private Integer campusId;

    public LocalCreateDTO() {
        // Construtor padrao para frameworks de serializacao
    }

    public LocalCreateDTO(String nome, String descricao, Integer campusId) {
        this.nome = nome;
        this.descricao = descricao;
        this.campusId = campusId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getCampusId() {
        return campusId;
    }

    public void setCampusId(Integer campusId) {
        this.campusId = campusId;
    }
}

