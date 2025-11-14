package com.AchadosPerdidos.API.Domain.Entity;

import java.util.Date;

public class UsuarioCampus {
    private Integer usuarioId;
    private Integer campusId;
    private Date dtaCriacao;
    private Boolean flgInativo;
    private Date dtaRemocao;

    public UsuarioCampus() {}

    public UsuarioCampus(Integer usuarioId, Integer campusId, Date dtaCriacao, Boolean flgInativo, Date dtaRemocao) {
        this.usuarioId = usuarioId;
        this.campusId = campusId;
        this.dtaCriacao = dtaCriacao;
        this.flgInativo = flgInativo;
        this.dtaRemocao = dtaRemocao;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Integer getCampusId() {
        return campusId;
    }

    public void setCampusId(Integer campusId) {
        this.campusId = campusId;
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

