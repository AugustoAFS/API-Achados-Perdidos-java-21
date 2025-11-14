package com.AchadosPerdidos.API.Application.DTOs.StatusItem;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "DTO para lista de status de itens")
public class StatusItemListDTO {
    @Schema(description = "Lista de status de itens")
    private List<StatusItemDTO> statusItens;

    @Schema(description = "Total de status na lista")
    private int totalCount;

    public StatusItemListDTO() {
        // Construtor padrao para frameworks de serializacao
    }

    public StatusItemListDTO(List<StatusItemDTO> statusItens, int totalCount) {
        this.statusItens = statusItens;
        this.totalCount = totalCount;
    }

    public List<StatusItemDTO> getStatusItens() {
        return statusItens;
    }

    public void setStatusItens(List<StatusItemDTO> statusItens) {
        this.statusItens = statusItens;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}

