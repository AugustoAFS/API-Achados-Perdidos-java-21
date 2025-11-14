package com.AchadosPerdidos.API.Application.DTOs.ItensReivindicados;

import io.swagger.v3.oas.annotations.media.Schema;

public class ItensReivindicadosUpdateDTO {
    @Schema(description = "Detalhes da reivindicacao", example = "Este item me pertence, perdi na biblioteca")
    private String detalhesReivindicacao;

    @Schema(description = "ID do usuario que achou o item", example = "2")
    private Integer usuarioAchouId;

    @Schema(description = "Flag indicando se esta inativo", example = "false")
    private Boolean flgInativo;

    public ItensReivindicadosUpdateDTO() {
        // Construtor padrao para frameworks de serializacao
    }

    public ItensReivindicadosUpdateDTO(String detalhesReivindicacao, Integer usuarioAchouId, Boolean flgInativo) {
        this.detalhesReivindicacao = detalhesReivindicacao;
        this.usuarioAchouId = usuarioAchouId;
        this.flgInativo = flgInativo;
    }

    public String getDetalhesReivindicacao() {
        return detalhesReivindicacao;
    }

    public void setDetalhesReivindicacao(String detalhesReivindicacao) {
        this.detalhesReivindicacao = detalhesReivindicacao;
    }

    public Integer getUsuarioAchouId() {
        return usuarioAchouId;
    }

    public void setUsuarioAchouId(Integer usuarioAchouId) {
        this.usuarioAchouId = usuarioAchouId;
    }

    public Boolean getFlgInativo() {
        return flgInativo;
    }

    public void setFlgInativo(Boolean flgInativo) {
        this.flgInativo = flgInativo;
    }
}

