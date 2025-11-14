package com.AchadosPerdidos.API.Application.DTOs.ItensReivindicados;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public class ItensReivindicadosListDTO {
    @Schema(description = "Lista de itens reivindicados")
    private List<ItensReivindicadosDTO> itensReivindicados;

    @Schema(description = "Total de reivindicacoes na lista")
    private int totalCount;

    public ItensReivindicadosListDTO() {
        // Construtor padrao para frameworks de serializacao
    }

    public ItensReivindicadosListDTO(List<ItensReivindicadosDTO> itensReivindicados, int totalCount) {
        this.itensReivindicados = itensReivindicados;
        this.totalCount = totalCount;
    }

    public List<ItensReivindicadosDTO> getItensReivindicados() {
        return itensReivindicados;
    }

    public void setItensReivindicados(List<ItensReivindicadosDTO> itensReivindicados) {
        this.itensReivindicados = itensReivindicados;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}

