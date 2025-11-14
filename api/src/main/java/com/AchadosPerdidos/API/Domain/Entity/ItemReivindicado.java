package com.AchadosPerdidos.API.Domain.Entity;

import java.util.Date;

public class ItemReivindicado {
    private Integer id;
    private String detalhesReivindicacao;
    private Integer itemId;
    private Integer usuarioReivindicadorId;
    private Integer usuarioAchouId;
    private Date dtaCriacao;
    private Boolean flgInativo;
    private Date dtaRemocao;

    public ItemReivindicado() {}

    public ItemReivindicado(Integer id, String detalhesReivindicacao, Integer itemId, Integer usuarioReivindicadorId, Integer usuarioAchouId, Date dtaCriacao, Boolean flgInativo, Date dtaRemocao) {
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

