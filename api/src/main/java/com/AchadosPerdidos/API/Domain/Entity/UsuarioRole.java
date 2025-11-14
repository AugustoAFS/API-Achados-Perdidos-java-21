package com.AchadosPerdidos.API.Domain.Entity;

import java.util.Date;

public class UsuarioRole {
    private Integer usuarioId;
    private Integer roleId;
    private Date dtaCriacao;
    private Boolean flgInativo;
    private Date dtaRemocao;

    public UsuarioRole() {}

    public UsuarioRole(Integer usuarioId, Integer roleId, Date dtaCriacao, Boolean flgInativo, Date dtaRemocao) {
        this.usuarioId = usuarioId;
        this.roleId = roleId;
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

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
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

