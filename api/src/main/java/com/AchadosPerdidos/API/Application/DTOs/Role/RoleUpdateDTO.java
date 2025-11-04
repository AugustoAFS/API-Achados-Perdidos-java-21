package com.AchadosPerdidos.API.Application.DTOs.Role;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO para atualização de role")
public class RoleUpdateDTO {
    
    @Schema(description = "Nome da role", example = "Admin")
    private String nome;
    
    @Schema(description = "Descrição da role", example = "Administrador do sistema")
    private String descricao;
    
    @Schema(description = "Flag indicando se está inativo", example = "false")
    private Boolean flgInativo;

    // Getters e Setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public Boolean getFlgInativo() { return flgInativo; }
    public void setFlgInativo(Boolean flgInativo) { this.flgInativo = flgInativo; }
}

