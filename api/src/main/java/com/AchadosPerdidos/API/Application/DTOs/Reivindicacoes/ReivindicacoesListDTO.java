package com.AchadosPerdidos.API.Application.DTOs.Reivindicacoes;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "DTO para lista de reivindicacoes")
public class ReivindicacoesListDTO {
    @Schema(description = "Lista de reivindicacoes")
    private List<ReivindicacoesDTO> reivindicacoes;

    @Schema(description = "Total de reivindicacoes na lista")
    private int totalCount;

    public ReivindicacoesListDTO() {
        // Construtor padrao para frameworks de serializacao
    }

    public ReivindicacoesListDTO(List<ReivindicacoesDTO> reivindicacoes, int totalCount) {
        this.reivindicacoes = reivindicacoes;
        this.totalCount = totalCount;
    }

    public List<ReivindicacoesDTO> getReivindicacoes() {
        return reivindicacoes;
    }

    public void setReivindicacoes(List<ReivindicacoesDTO> reivindicacoes) {
        this.reivindicacoes = reivindicacoes;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}

