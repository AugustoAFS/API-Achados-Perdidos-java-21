package com.AchadosPerdidos.API.Application.DTOs.Instituicao;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "DTO para lista de instituicoes")
public class InstituicaoListDTO {
    @Schema(description = "Lista de instituicoes")
    private List<InstituicaoDTO> instituicoes;

    @Schema(description = "Total de instituicoes na lista")
    private int totalCount;

    public InstituicaoListDTO() {
        // Construtor padrao para frameworks de serializacao
    }

    public InstituicaoListDTO(List<InstituicaoDTO> instituicoes, int totalCount) {
        this.instituicoes = instituicoes;
        this.totalCount = totalCount;
    }

    public List<InstituicaoDTO> getInstituicoes() {
        return instituicoes;
    }

    public void setInstituicoes(List<InstituicaoDTO> instituicoes) {
        this.instituicoes = instituicoes;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}

