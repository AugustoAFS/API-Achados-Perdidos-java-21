package com.AchadosPerdidos.API.Application.DTOs.UsuarioRole;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO para atualizacao de relacionamento usuario-role")
public class UsuarioRoleUpdateDTO {
    @Schema(description = "ID do usuario", example = "1")
    private Integer usuarioId;

    @Schema(description = "ID da role", example = "1")
    private Integer roleId;

    @Schema(description = "Flag de inativacao", example = "false")
    private Boolean flgInativo;

    public UsuarioRoleUpdateDTO() {
        // Construtor padrao para frameworks de serializacao
    }

    public UsuarioRoleUpdateDTO(Integer usuarioId, Integer roleId, Boolean flgInativo) {
        this.usuarioId = usuarioId;
        this.roleId = roleId;
        this.flgInativo = flgInativo;
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

    public Boolean getFlgInativo() {
        return flgInativo;
    }

    public void setFlgInativo(Boolean flgInativo) {
        this.flgInativo = flgInativo;
    }
}

