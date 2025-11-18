package com.AchadosPerdidos.API.Application.DTOs.UsuarioCampus;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "DTO para lista de relacionamentos usuario-campus")
public class UsuarioCampusListDTO {
    @Schema(description = "Lista de relacionamentos usuario-campus")
    private List<UsuarioCampusDTO> usuarioCampus;
    
    @Schema(description = "Total de relacionamentos na lista")
    private int totalCount;

    public UsuarioCampusListDTO() {
        // Construtor padrao para frameworks de serializacao
    }

    public UsuarioCampusListDTO(List<UsuarioCampusDTO> usuarioCampus, int totalCount) {
        this.usuarioCampus = usuarioCampus;
        this.totalCount = totalCount;
    }

    public List<UsuarioCampusDTO> getUsuarioCampus() {
        return usuarioCampus;
    }

    public void setUsuarioCampus(List<UsuarioCampusDTO> usuarioCampus) {
        this.usuarioCampus = usuarioCampus;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}

