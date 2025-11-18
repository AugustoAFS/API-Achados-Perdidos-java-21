package com.AchadosPerdidos.API.Application.DTOs.FotoUsuario;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "DTO para lista de relacionamentos foto-usuario")
public class FotoUsuarioListDTO {
    @Schema(description = "Lista de relacionamentos foto-usuario")
    private List<FotoUsuarioDTO> fotoUsuarios;
    
    @Schema(description = "Total de relacionamentos na lista")
    private int totalCount;

    public FotoUsuarioListDTO() {
        // Construtor padrao para frameworks de serializacao
    }

    public FotoUsuarioListDTO(List<FotoUsuarioDTO> fotoUsuarios, int totalCount) {
        this.fotoUsuarios = fotoUsuarios;
        this.totalCount = totalCount;
    }

    public List<FotoUsuarioDTO> getFotoUsuarios() {
        return fotoUsuarios;
    }

    public void setFotoUsuarios(List<FotoUsuarioDTO> fotoUsuarios) {
        this.fotoUsuarios = fotoUsuarios;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}

