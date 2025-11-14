package com.AchadosPerdidos.API.Domain.Entity;

import java.util.Date;

public class Campus {
    private Integer id;
    private String nome;
    private Integer instituicaoId;
    private Integer enderecoId;
    private Date dtaCriacao;
    private Boolean flgInativo;
    private Date dtaRemocao;

    public Campus() {}

    public Campus(Integer id, String nome, Integer instituicaoId, Integer enderecoId, Date dtaCriacao, Boolean flgInativo, Date dtaRemocao) {
        this.id = id;
        this.nome = nome;
        this.instituicaoId = instituicaoId;
        this.enderecoId = enderecoId;
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

    public Integer getInstituicaoId() {
        return instituicaoId;
    }

    public void setInstituicaoId(Integer instituicaoId) {
        this.instituicaoId = instituicaoId;
    }

    public Integer getEnderecoId() {
        return enderecoId;
    }

    public void setEnderecoId(Integer enderecoId) {
        this.enderecoId = enderecoId;
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

