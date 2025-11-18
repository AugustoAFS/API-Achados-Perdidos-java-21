package com.AchadosPerdidos.API.Application.DTOs.ItemDevolvido;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "DTO para lista de itens devolvidos")
public class ItemDevolvidoListDTO {
    @Schema(description = "Lista de itens devolvidos")
    private List<ItemDevolvidoDTO> itensDevolvidos;
    
    @Schema(description = "Total de itens devolvidos na lista")
    private int totalCount;

    public ItemDevolvidoListDTO() {
        // Construtor padrao para frameworks de serializacao
    }

    public ItemDevolvidoListDTO(List<ItemDevolvidoDTO> itensDevolvidos, int totalCount) {
        this.itensDevolvidos = itensDevolvidos;
        this.totalCount = totalCount;
    }

    public List<ItemDevolvidoDTO> getItensDevolvidos() {
        return itensDevolvidos;
    }

    public void setItensDevolvidos(List<ItemDevolvidoDTO> itensDevolvidos) {
        this.itensDevolvidos = itensDevolvidos;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}

