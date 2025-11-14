package com.AchadosPerdidos.API.Application.DTOs.Local;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO para atualizacao de Local")
public class LocalUpdateDTO {
    private String nome;
    private String descricao;
    private Integer campusId;
    private Boolean flgInativo;

    public LocalUpdateDTO() {
        // Construtor padrao para frameworks de serializacao
    }

    public LocalUpdateDTO(String nome, String descricao, Integer campusId, Boolean flgInativo) {
        this.nome = nome;
        this.descricao = descricao;
        this.campusId = campusId;
        this.flgInativo = flgInativo;
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

    public Boolean getFlgInativo() {
        return flgInativo;
    }

    public void setFlgInativo(Boolean flgInativo) {
        this.flgInativo = flgInativo;
    }
}

