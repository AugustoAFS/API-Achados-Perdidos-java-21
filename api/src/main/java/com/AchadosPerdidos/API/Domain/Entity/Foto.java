package com.AchadosPerdidos.API.Domain.Entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "fotos", schema = "ap_achados_perdidos")
public class Foto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String url;

    private String provedor_armazenamento;

    private String chave_armazenamento;

    private String nome_arquivo_original;

    private Long tamanho_arquivo_bytes;

    private LocalDateTime Dta_Criacao;

    private Boolean Flg_Inativo;

    private LocalDateTime Dta_Remocao;

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

    public String getProvedorArmazenamento() {
        return provedor_armazenamento;
    }

    public void setProvedorArmazenamento(String provedor_armazenamento) {
        this.provedor_armazenamento = provedor_armazenamento;
    }

    public String getChaveArmazenamento() {
        return chave_armazenamento;
    }

    public void setChaveArmazenamento(String chave_armazenamento) {
        this.chave_armazenamento = chave_armazenamento;
    }

    public String getNomeArquivoOriginal() {
        return nome_arquivo_original;
    }

    public void setNomeArquivoOriginal(String nome_arquivo_original) {
        this.nome_arquivo_original = nome_arquivo_original;
    }

    public Long getTamanhoArquivoBytes() {
        return tamanho_arquivo_bytes;
    }

    public void setTamanhoArquivoBytes(Long tamanho_arquivo_bytes) {
        this.tamanho_arquivo_bytes = tamanho_arquivo_bytes;
    }

    public LocalDateTime getDtaCriacao() {
        return Dta_Criacao;
    }

    public void setDtaCriacao(LocalDateTime Dta_Criacao) {
        this.Dta_Criacao = Dta_Criacao;
    }

    public Boolean getFlgInativo() {
        return Flg_Inativo;
    }

    public void setFlgInativo(Boolean Flg_Inativo) {
        this.Flg_Inativo = Flg_Inativo;
    }

    public LocalDateTime getDtaRemocao() {
        return Dta_Remocao;
    }

    public void setDtaRemocao(LocalDateTime Dta_Remocao) {
        this.Dta_Remocao = Dta_Remocao;
    }
}


