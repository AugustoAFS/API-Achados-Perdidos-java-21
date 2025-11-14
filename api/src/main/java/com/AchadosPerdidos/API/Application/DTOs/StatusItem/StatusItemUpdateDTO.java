package com.AchadosPerdidos.API.Application.DTOs.StatusItem;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO para atualizacao de Status do Item")
public class StatusItemUpdateDTO {
    private String nome;
    private String descricao;
    private String statusItem;
    private Boolean flgInativo;

    public StatusItemUpdateDTO() {
        // Construtor padrao para frameworks de serializacao
    }

    public StatusItemUpdateDTO(String nome, String descricao, String statusItem, Boolean flgInativo) {
        this.nome = nome;
        this.descricao = descricao;
        this.statusItem = statusItem;
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

    public String getStatusItem() {
        return statusItem;
    }

    public void setStatusItem(String statusItem) {
        this.statusItem = statusItem;
    }

    public Boolean getFlgInativo() {
        return flgInativo;
    }

    public void setFlgInativo(Boolean flgInativo) {
        this.flgInativo = flgInativo;
    }
}

