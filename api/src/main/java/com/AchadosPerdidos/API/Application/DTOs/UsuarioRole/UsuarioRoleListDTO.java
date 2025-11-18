package com.AchadosPerdidos.API.Application.DTOs.UsuarioRole;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "DTO para lista de relacionamentos usuario-role")
public class UsuarioRoleListDTO {
    @Schema(description = "Lista de relacionamentos usuario-role")
    private List<UsuarioRoleDTO> usuarioRoles;
    
    @Schema(description = "Total de relacionamentos na lista")
    private int totalCount;

    public UsuarioRoleListDTO() {
        // Construtor padrao para frameworks de serializacao
    }

    public UsuarioRoleListDTO(List<UsuarioRoleDTO> usuarioRoles, int totalCount) {
        this.usuarioRoles = usuarioRoles;
        this.totalCount = totalCount;
    }

    public List<UsuarioRoleDTO> getUsuarioRoles() {
        return usuarioRoles;
    }

    public void setUsuarioRoles(List<UsuarioRoleDTO> usuarioRoles) {
        this.usuarioRoles = usuarioRoles;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}

