package com.AchadosPerdidos.API.Application.DTOs.Estado;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "DTO com lista de estados")
public class EstadoListDTO {
    @Schema(description = "Lista de estados")
    private List<EstadoDTO> Estados;

    public EstadoListDTO() {
        // Construtor padrao requerido para serializacao
    }

    public EstadoListDTO(List<EstadoDTO> Estados) {
        this.Estados = Estados;
    }

    public List<EstadoDTO> getEstados() {
        return Estados;
    }

    public void setEstados(List<EstadoDTO> Estados) {
        this.Estados = Estados;
    }
}

