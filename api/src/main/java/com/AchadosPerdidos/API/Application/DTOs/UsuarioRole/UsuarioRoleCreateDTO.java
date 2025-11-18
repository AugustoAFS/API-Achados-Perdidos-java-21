package com.AchadosPerdidos.API.Application.DTOs.UsuarioRole;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO para criacao de relacionamento usuario-role")
public class UsuarioRoleCreateDTO {
    @Schema(description = "ID do usuario", example = "1", required = true)
    private Integer usuarioId;

    @Schema(description = "ID da role", example = "1", required = true)
    private Integer roleId;

    public UsuarioRoleCreateDTO() {
        // Construtor padrao para frameworks de serializacao
    }

    public UsuarioRoleCreateDTO(Integer usuarioId, Integer roleId) {
        this.usuarioId = usuarioId;
        this.roleId = roleId;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}

