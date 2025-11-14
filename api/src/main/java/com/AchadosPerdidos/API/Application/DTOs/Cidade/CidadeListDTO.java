package com.AchadosPerdidos.API.Application.DTOs.Cidade;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;


public class CidadeListDTO{
    @Schema(description = "Lista de cidades")
    private List<CidadeDTO> Cidades;

    public CidadeListDTO() {
        // Construtor padrao para frameworks de serializacao
    }

    public CidadeListDTO(List<CidadeDTO> cidades) {
        this.Cidades = cidades;
    }

    public List<CidadeDTO> getCidades() {
        return Cidades;
    }
    public void setCidades(List<CidadeDTO> cidades) {
        Cidades = cidades;
    }
}