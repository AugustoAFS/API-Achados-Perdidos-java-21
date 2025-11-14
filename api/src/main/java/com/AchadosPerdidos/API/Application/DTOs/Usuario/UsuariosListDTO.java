package com.AchadosPerdidos.API.Application.DTOs.Usuario;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "DTO para lista de usuarios")
public class UsuariosListDTO {
    @Schema(description = "Lista de usuarios")
    private List<UsuariosDTO> usuarios;

    @Schema(description = "Total de usuarios na lista")
    private int totalCount;

    public UsuariosListDTO() {
        // Construtor padrao para frameworks de serializacao
    }

    public UsuariosListDTO(List<UsuariosDTO> usuarios, int totalCount) {
        this.usuarios = usuarios;
        this.totalCount = totalCount;
    }

    public List<UsuariosDTO> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<UsuariosDTO> usuarios) {
        this.usuarios = usuarios;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}

