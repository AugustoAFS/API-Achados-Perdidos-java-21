package com.AchadosPerdidos.API.Application.DTOs.ItemDoado;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "DTO para lista de itens doados")
public class ItemDoadoListDTO {
    @Schema(description = "Lista de itens doados")
    private List<ItemDoadoDTO> itensDoados;
    
    @Schema(description = "Total de itens doados na lista")
    private int totalCount;

    public ItemDoadoListDTO() {
        // Construtor padrao para frameworks de serializacao
    }

    public ItemDoadoListDTO(List<ItemDoadoDTO> itensDoados, int totalCount) {
        this.itensDoados = itensDoados;
        this.totalCount = totalCount;
    }

    public List<ItemDoadoDTO> getItensDoados() {
        return itensDoados;
    }

    public void setItensDoados(List<ItemDoadoDTO> itensDoados) {
        this.itensDoados = itensDoados;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}

