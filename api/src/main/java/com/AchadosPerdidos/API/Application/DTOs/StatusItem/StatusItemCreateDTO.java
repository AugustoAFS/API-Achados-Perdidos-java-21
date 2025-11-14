package com.AchadosPerdidos.API.Application.DTOs.StatusItem;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO para criacao de Status do Item")
public class StatusItemCreateDTO {
    private String nome;
    private String descricao;
    private String statusItem;

    public StatusItemCreateDTO() {
        // Construtor padrao para frameworks de serializacao
    }

    public StatusItemCreateDTO(String nome, String descricao, String statusItem) {
        this.nome = nome;
        this.descricao = descricao;
        this.statusItem = statusItem;
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
}

