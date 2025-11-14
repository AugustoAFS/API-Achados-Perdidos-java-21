package com.AchadosPerdidos.API.Application.DTOs.StatusItem;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Date;

@Schema(description = "DTO de Status do Item")
public class StatusItemDTO {
    private Integer id;
    private String nome;
    private String descricao;
    private String statusItem;
    private Date dtaCriacao;
    private Boolean flgInativo;
    private Date dtaRemocao;

    public StatusItemDTO() {
        // Construtor padrao para frameworks de serializacao
    }

    public StatusItemDTO(Integer id, String nome, String descricao, String statusItem, Date dtaCriacao, Boolean flgInativo, Date dtaRemocao) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.statusItem = statusItem;
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

    public String getStatusItem() {
        return statusItem;
    }

    public void setStatusItem(String statusItem) {
        this.statusItem = statusItem;
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

