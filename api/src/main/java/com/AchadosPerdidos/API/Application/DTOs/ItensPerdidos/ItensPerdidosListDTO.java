package com.AchadosPerdidos.API.Application.DTOs.ItensPerdidos;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public class ItensPerdidosListDTO {
    @Schema(description = "Lista de itens perdidos")
    private List<ItensPerdidosDTO> itensPerdidos;

    @Schema(description = "Total de itens na lista")
    private int totalCount;

    public ItensPerdidosListDTO() {
        // Construtor padrao para frameworks de serializacao
    }

    public ItensPerdidosListDTO(List<ItensPerdidosDTO> itensPerdidos, int totalCount) {
        this.itensPerdidos = itensPerdidos;
        this.totalCount = totalCount;
    }

    public List<ItensPerdidosDTO> getItensPerdidos() {
        return itensPerdidos;
    }

    public void setItensPerdidos(List<ItensPerdidosDTO> itensPerdidos) {
        this.itensPerdidos = itensPerdidos;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}

