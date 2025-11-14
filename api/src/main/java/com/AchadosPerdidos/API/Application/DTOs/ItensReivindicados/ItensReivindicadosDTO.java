package com.AchadosPerdidos.API.Application.DTOs.ItensReivindicados;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

public class ItensReivindicadosDTO {
    @Schema(description = "ID unico da reivindicacao", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private int id;

    @Schema(description = "Detalhes da reivindicacao", example = "Este item me pertence, perdi na biblioteca")
    private String detalhesReivindicacao;

    @Schema(description = "ID do item reivindicado", example = "1")
    private int itemId;

    @Schema(description = "ID do usuario que fez a reivindicacao", example = "1")
    private int usuarioReivindicadorId;

    @Schema(description = "ID do usuario que achou o item", example = "2", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer usuarioAchouId;

    @Schema(description = "Data de criacao", example = "2024-01-01T00:00:00", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime dtaCriacao;

    @Schema(description = "Flag indicando se esta inativo", example = "false")
    private Boolean flgInativo;

    @Schema(description = "Data de remocao", example = "null", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime dtaRemocao;

    public ItensReivindicadosDTO() {
        // Construtor padrao para frameworks de serializacao
    }

    public ItensReivindicadosDTO(int id, String detalhesReivindicacao, int itemId, int usuarioReivindicadorId, Integer usuarioAchouId, LocalDateTime dtaCriacao, Boolean flgInativo, LocalDateTime dtaRemocao) {
        this.id = id;
        this.detalhesReivindicacao = detalhesReivindicacao;
        this.itemId = itemId;
        this.usuarioReivindicadorId = usuarioReivindicadorId;
        this.usuarioAchouId = usuarioAchouId;
        this.dtaCriacao = dtaCriacao;
        this.flgInativo = flgInativo;
        this.dtaRemocao = dtaRemocao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Integer getUsuarioAchouId() {
        return usuarioAchouId;
    }

    public void setUsuarioAchouId(Integer usuarioAchouId) {
        this.usuarioAchouId = usuarioAchouId;
    }

    public LocalDateTime getDtaCriacao() {
        return dtaCriacao;
    }

    public void setDtaCriacao(LocalDateTime dtaCriacao) {
        this.dtaCriacao = dtaCriacao;
    }

    public Boolean getFlgInativo() {
        return flgInativo;
    }

    public void setFlgInativo(Boolean flgInativo) {
        this.flgInativo = flgInativo;
    }

    public LocalDateTime getDtaRemocao() {
        return dtaRemocao;
    }

    public void setDtaRemocao(LocalDateTime dtaRemocao) {
        this.dtaRemocao = dtaRemocao;
    }
}

