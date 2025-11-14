package com.AchadosPerdidos.API.Application.DTOs.Estado;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO para criacao de Estado")
public class EstadoCreateDTO {
    private String nome;
    private String uf;

    public EstadoCreateDTO() {
        // Construtor padrao requerido para serializacao
    }

    public EstadoCreateDTO(String nome, String uf) {
        this.nome = nome;
        this.uf = uf;
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
}
