package com.AchadosPerdidos.API.Application.DTOs.Role;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO para criacao de role")
public class RoleCreateDTO {
    @Schema(description = "Nome da role", example = "Admin", required = true)
    private String nome;

    @Schema(description = "Descricao da role", example = "Administrador do sistema")
    private String descricao;

    public RoleCreateDTO() {
        // Construtor padrao para frameworks de serializacao
    }

    public RoleCreateDTO(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
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
}

