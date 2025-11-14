package com.AchadosPerdidos.API.Domain.Entity;

import java.util.Date;

public class Itens {
    private Integer id;
    private String nome;
    private String descricao;
    private Date encontradoEm;
    private Integer usuarioRelatorId;
    private Integer localId;
    private Integer statusItemId;
    private Date dtaCriacao;
    private Boolean flgInativo;
    private Date dtaRemocao;

    public Itens() {}

    public Itens(Integer id, String nome, String descricao, Date encontradoEm, Integer usuarioRelatorId, Integer localId, Integer statusItemId, Date dtaCriacao, Boolean flgInativo, Date dtaRemocao) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.encontradoEm = encontradoEm;
        this.usuarioRelatorId = usuarioRelatorId;
        this.localId = localId;
        this.statusItemId = statusItemId;
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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getEncontradoEm() {
        return encontradoEm;
    }

    public void setEncontradoEm(Date encontradoEm) {
        this.encontradoEm = encontradoEm;
    }

    public Integer getUsuarioRelatorId() {
        return usuarioRelatorId;
    }

    public void setUsuarioRelatorId(Integer usuarioRelatorId) {
        this.usuarioRelatorId = usuarioRelatorId;
    }

    public Integer getLocalId() {
        return localId;
    }

    public void setLocalId(Integer localId) {
        this.localId = localId;
    }

    public Integer getStatusItemId() {
        return statusItemId;
    }

    public void setStatusItemId(Integer statusItemId) {
        this.statusItemId = statusItemId;
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

