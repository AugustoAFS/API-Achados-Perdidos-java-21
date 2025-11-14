package com.AchadosPerdidos.API.Application.DTOs.Fotos;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "DTO para lista de fotos")
public class FotosListDTO {
    @Schema(description = "Lista de fotos")
    private List<FotosDTO> fotos;

    @Schema(description = "Total de fotos na lista")
    private int totalCount;

    public FotosListDTO() {
        // Construtor padrao para frameworks de serializacao
    }

    public FotosListDTO(List<FotosDTO> fotos, int totalCount) {
        this.fotos = fotos;
        this.totalCount = totalCount;
    }

    public List<FotosDTO> getFotos() {
        return fotos;
    }

    public void setFotos(List<FotosDTO> fotos) {
        this.fotos = fotos;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}

