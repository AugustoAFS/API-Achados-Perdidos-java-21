package com.AchadosPerdidos.API.Application.DTOs.ItensReivindicados;

import io.swagger.v3.oas.annotations.media.Schema;

public class ItensReivindicadosCreateDTO {
    @Schema(description = "Detalhes da reivindicacao", example = "Este item me pertence, perdi na biblioteca", required = true)
    private String detalhesReivindicacao;

    @Schema(description = "ID do item reivindicado", example = "1", required = true)
    private int itemId;

    @Schema(description = "ID do usuario que fez a reivindicacao", example = "1", required = true)
    private int usuarioReivindicadorId;

    public ItensReivindicadosCreateDTO() {
        // Construtor padrao para frameworks de serializacao
    }

    public ItensReivindicadosCreateDTO(String detalhesReivindicacao, int itemId, int usuarioReivindicadorId) {
        this.detalhesReivindicacao = detalhesReivindicacao;
        this.itemId = itemId;
        this.usuarioReivindicadorId = usuarioReivindicadorId;
    }

    public String getDetalhesReivindicacao() {
        return detalhesReivindicacao;
    }

    public void setDetalhesReivindicacao(String detalhesReivindicacao) {
        this.detalhesReivindicacao = detalhesReivindicacao;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getUsuarioReivindicadorId() {
        return usuarioReivindicadorId;
    }

    public void setUsuarioReivindicadorId(int usuarioReivindicadorId) {
        this.usuarioReivindicadorId = usuarioReivindicadorId;
    }
}

