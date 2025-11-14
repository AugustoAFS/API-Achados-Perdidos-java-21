package com.AchadosPerdidos.API.Application.DTOs.Role;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO para atualizacao de role")
public class RoleUpdateDTO {
    @Schema(description = "Nome da role", example = "Admin")
    private String nome;

    @Schema(description = "Descricao da role", example = "Administrador do sistema")
    private String descricao;

    @Schema(description = "Flag indicando se esta inativo", example = "false")
    private Boolean flgInativo;

    public RoleUpdateDTO() {
        // Construtor padrao para frameworks de serializacao
    }

    public RoleUpdateDTO(String nome, String descricao, Boolean flgInativo) {
        this.nome = nome;
        this.descricao = descricao;
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

    public Boolean getFlgInativo() {
        return flgInativo;
    }

    public void setFlgInativo(Boolean flgInativo) {
        this.flgInativo = flgInativo;
    }
}

