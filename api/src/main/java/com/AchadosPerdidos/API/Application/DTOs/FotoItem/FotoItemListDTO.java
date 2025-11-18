package com.AchadosPerdidos.API.Application.DTOs.FotoItem;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "DTO para lista de relacionamentos foto-item")
public class FotoItemListDTO {
    @Schema(description = "Lista de relacionamentos foto-item")
    private List<FotoItemDTO> fotoItens;
    
    @Schema(description = "Total de relacionamentos na lista")
    private int totalCount;

    public FotoItemListDTO() {
        // Construtor padrao para frameworks de serializacao
    }

    public FotoItemListDTO(List<FotoItemDTO> fotoItens, int totalCount) {
        this.fotoItens = fotoItens;
        this.totalCount = totalCount;
    }

    public List<FotoItemDTO> getFotoItens() {
        return fotoItens;
    }

    public void setFotoItens(List<FotoItemDTO> fotoItens) {
        this.fotoItens = fotoItens;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}

