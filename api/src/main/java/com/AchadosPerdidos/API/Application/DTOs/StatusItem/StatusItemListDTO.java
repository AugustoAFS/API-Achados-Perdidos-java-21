package com.AchadosPerdidos.API.Application.DTOs.StatusItem;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "DTO para lista de status de itens")
public class StatusItemListDTO {
    
    @Schema(description = "Lista de status de itens")
    private List<StatusItemDTO> statusItens;
    
    @Schema(description = "Total de status na lista")
    private int totalCount;

    // Getters e Setters
    public List<StatusItemDTO> getStatusItens() { return statusItens; }
    public void setStatusItens(List<StatusItemDTO> statusItens) { this.statusItens = statusItens; }

    public int getTotalCount() { return totalCount; }
    public void setTotalCount(int totalCount) { this.totalCount = totalCount; }
}

