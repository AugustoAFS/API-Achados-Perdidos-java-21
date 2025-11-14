package com.AchadosPerdidos.API.Application.DTOs.Cidade;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Date;

@Schema(description = "DTO de Cidade")

public class CidadeDTO{
    private Integer id;
    private String nome;
    private Integer estadoId;
    private Date dtaCriacao;
    private Boolean flgInativo;     
    private Date dtaRemocao;

    public CidadeDTO() {
        // Construtor padrao para frameworks de serializacao
    }

    public CidadeDTO(Integer id, String nome, Integer estadoId, Date dtaCriacao, Boolean flgInativo, Date dtaRemocao) {
        this.id = id;
        this.nome = nome;
        this.estadoId = estadoId;
        this.dtaCriacao = dtaCriacao;
        this.flgInativo = flgInativo;
        this.dtaRemocao = dtaRemocao;
    }

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Integer getEstadoId() {
        return estadoId;
    }

    public Date getDtaCriacao() {
        return dtaCriacao;
    }

    public Boolean getFlgInativo() {
        return flgInativo;
    }

    public Date getDtaRemocao() {
        return dtaRemocao;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEstadoId(Integer estadoId) {
        this.estadoId = estadoId;
    }

    public void setDtaCriacao(Date dtaCriacao) {
        this.dtaCriacao = dtaCriacao;
    }

    public void setFlgInativo(Boolean flgInativo) {
        this.flgInativo = flgInativo;
    }

    public void setDtaRemocao(Date dtaRemocao) {
        this.dtaRemocao = dtaRemocao;
    }
}