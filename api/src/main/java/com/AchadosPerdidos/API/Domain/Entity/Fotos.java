package com.AchadosPerdidos.API.Domain.Entity;

import com.AchadosPerdidos.API.Domain.Enum.Provedor_Armazenamento;
import java.util.Date;

public class Fotos {
    private Integer id;
    private String url;
    private Provedor_Armazenamento provedorArmazenamento;
    private String chaveArmazenamento;
    private String nomeArquivoOriginal;
    private Long tamanhoArquivoBytes;
    private Date dtaCriacao;
    private Boolean flgInativo;
    private Date dtaRemocao;

    public Fotos() {}

    public Fotos(Integer id, String url, Provedor_Armazenamento provedorArmazenamento, String chaveArmazenamento, String nomeArquivoOriginal, Long tamanhoArquivoBytes, Date dtaCriacao, Boolean flgInativo, Date dtaRemocao) {
        this.id = id;
        this.url = url;
        this.provedorArmazenamento = provedorArmazenamento;
        this.chaveArmazenamento = chaveArmazenamento;
        this.nomeArquivoOriginal = nomeArquivoOriginal;
        this.tamanhoArquivoBytes = tamanhoArquivoBytes;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Provedor_Armazenamento getProvedorArmazenamento() {
        return provedorArmazenamento;
    }

    public void setProvedorArmazenamento(Provedor_Armazenamento provedorArmazenamento) {
        this.provedorArmazenamento = provedorArmazenamento;
    }

    public String getChaveArmazenamento() {
        return chaveArmazenamento;
    }

    public void setChaveArmazenamento(String chaveArmazenamento) {
        this.chaveArmazenamento = chaveArmazenamento;
    }

    public String getNomeArquivoOriginal() {
        return nomeArquivoOriginal;
    }

    public void setNomeArquivoOriginal(String nomeArquivoOriginal) {
        this.nomeArquivoOriginal = nomeArquivoOriginal;
    }

    public Long getTamanhoArquivoBytes() {
        return tamanhoArquivoBytes;
    }

    public void setTamanhoArquivoBytes(Long tamanhoArquivoBytes) {
        this.tamanhoArquivoBytes = tamanhoArquivoBytes;
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

