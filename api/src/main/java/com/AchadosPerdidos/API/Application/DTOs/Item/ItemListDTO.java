package com.AchadosPerdidos.API.Application.DTOs.Item;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "DTO para lista de itens")
public class ItemListDTO {
    @Schema(description = "Lista de itens")
    private List<ItemDTO> itens;
    
    @Schema(description = "Total de itens na lista")
    private int totalCount;

    public ItemListDTO() {
        // Construtor padrao para frameworks de serializacao
    }

    public ItemListDTO(List<ItemDTO> itens, int totalCount) {
        this.itens = itens;
        this.totalCount = totalCount;
    }

    public List<ItemDTO> getItens() {
        return itens;
    }

    public void setItens(List<ItemDTO> itens) {
        this.itens = itens;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}

