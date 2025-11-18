package com.AchadosPerdidos.API.Application.DTOs.ItemAchado;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "DTO para lista de itens achados")
public class ItemAchadoListDTO {
    @Schema(description = "Lista de itens achados")
    private List<ItemAchadoDTO> itensAchados;
    
    @Schema(description = "Total de itens achados na lista")
    private int totalCount;

    public ItemAchadoListDTO() {
        // Construtor padrao para frameworks de serializacao
    }

    public ItemAchadoListDTO(List<ItemAchadoDTO> itensAchados, int totalCount) {
        this.itensAchados = itensAchados;
        this.totalCount = totalCount;
    }

    public List<ItemAchadoDTO> getItensAchados() {
        return itensAchados;
    }

    public void setItensAchados(List<ItemAchadoDTO> itensAchados) {
        this.itensAchados = itensAchados;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}

