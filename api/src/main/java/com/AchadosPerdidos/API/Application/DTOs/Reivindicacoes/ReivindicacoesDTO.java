package com.AchadosPerdidos.API.Application.DTOs.Reivindicacoes;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Date;

@Schema(description = "DTO completo de reivindicacao")
public class ReivindicacoesDTO {
    @Schema(description = "ID da reivindicacao", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer id;

    @Schema(description = "Detalhes da reivindicacao", example = "Este item me pertence, perdi na biblioteca")
    private String detalhesReivindicacao;

    @Schema(description = "ID do item", example = "1")
    private Integer itemId;

    @Schema(description = "ID do usuario reivindicador", example = "1")
    private Integer usuarioReivindicadorId;

    @Schema(description = "ID do usuario que achou o item", example = "2")
    private Integer usuarioAchouId;

    @Schema(description = "Data de criacao", example = "2024-01-01T00:00:00")
    private Date dtaCriacao;

    @Schema(description = "Flag de inativacao", example = "false")
    private Boolean flgInativo;

    @Schema(description = "Data de remocao logica", example = "2024-02-01T00:00:00")
    private Date dtaRemocao;

    public ReivindicacoesDTO() {
        // Construtor padrao para frameworks de serializacao
    }

    public ReivindicacoesDTO(Integer id, String detalhesReivindicacao, Integer itemId, Integer usuarioReivindicadorId, Integer usuarioAchouId, Date dtaCriacao, Boolean flgInativo, Date dtaRemocao) {
        this.id = id;
        this.detalhesReivindicacao = detalhesReivindicacao;
        this.itemId = itemId;
        this.usuarioReivindicadorId = usuarioReivindicadorId;
        this.usuarioAchouId = usuarioAchouId;
        this.dtaCriacao = dtaCriacao;
        this.flgInativo = flgInativo;
        this.dtaRemocao = dtaRemocao;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDetalhesReivindicacao() {
        return detalhesReivindicacao;
    }

    public void setDetalhesReivindicacao(String detalhesReivindicacao) {
        this.detalhesReivindicacao = detalhesReivindicacao;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getUsuarioReivindicadorId() {
        return usuarioReivindicadorId;
    }

    public void setUsuarioReivindicadorId(Integer usuarioReivindicadorId) {
        this.usuarioReivindicadorId = usuarioReivindicadorId;
    }

    public Integer getUsuarioAchouId() {
        return usuarioAchouId;
    }

    public void setUsuarioAchouId(Integer usuarioAchouId) {
        this.usuarioAchouId = usuarioAchouId;
    }

    public Date getDtaCriacao() {
        return dtaCriacao;
    }

    public void setDtaCriacao(Date dtaCriacao) {
        this.dtaCriacao = dtaCriacao;
    }

    public Boolean getFlgInativo() {
        return flgInativo;
    }

    public void setFlgInativo(Boolean flgInativo) {
        this.flgInativo = flgInativo;
    }

    public Date getDtaRemocao() {
        return dtaRemocao;
    }

    public void setDtaRemocao(Date dtaRemocao) {
        this.dtaRemocao = dtaRemocao;
    }
}

