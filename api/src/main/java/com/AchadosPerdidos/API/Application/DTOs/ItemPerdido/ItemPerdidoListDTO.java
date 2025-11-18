package com.AchadosPerdidos.API.Application.DTOs.ItemPerdido;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "DTO para lista de itens perdidos")
public class ItemPerdidoListDTO {
    @Schema(description = "Lista de itens perdidos")
    private List<ItemPerdidoDTO> itensPerdidos;
    
    @Schema(description = "Total de itens perdidos na lista")
    private int totalCount;

    public ItemPerdidoListDTO() {
        // Construtor padrao para frameworks de serializacao
    }

    public ItemPerdidoListDTO(List<ItemPerdidoDTO> itensPerdidos, int totalCount) {
        this.itensPerdidos = itensPerdidos;
        this.totalCount = totalCount;
    }

    public List<ItemPerdidoDTO> getItensPerdidos() {
        return itensPerdidos;
    }

    public void setItensPerdidos(List<ItemPerdidoDTO> itensPerdidos) {
        this.itensPerdidos = itensPerdidos;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}

