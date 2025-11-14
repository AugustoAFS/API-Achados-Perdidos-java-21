package com.AchadosPerdidos.API.Application.DTOs.Role;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "DTO para lista de roles")
public class RoleListDTO {
    @Schema(description = "Lista de roles")
    private List<RoleDTO> Roles;

    public RoleListDTO() {
        // Construtor padrao para frameworks de serializacao
    }

    public RoleListDTO(List<RoleDTO> Roles) {
        this.Roles = Roles;
    }

    public List<RoleDTO> getRoles() {
        return Roles;
    }

    public void setRoles(List<RoleDTO> Roles) {
        this.Roles = Roles;
    }
}

