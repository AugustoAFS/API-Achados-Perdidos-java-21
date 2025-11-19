package com.AchadosPerdidos.API.Domain.Entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "fotos", schema = "ap_achados_perdidos")
public class Foto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer Id;

    @Column(name = "URL", columnDefinition = "TEXT", nullable = false)
    private String URL;

    @Column(name = "Provedor_armazenamento", length = 100, nullable = false)
    private String Provedor_armazenamento;

    @Column(name = "Chave_armazenamento", columnDefinition = "TEXT")
    private String Chave_armazenamento;

    @Column(name = "Nome_arquivo_original", length = 255)
    private String Nome_arquivo_original;

    @Column(name = "Tamanho_arquivo_bytes")
    private Long Tamanho_arquivo_bytes;

    @Column(name = "Dta_Criacao", nullable = false, updatable = false)
    private LocalDateTime Dta_Criacao;

    @Column(name = "Flg_Inativo", nullable = false)
    private Boolean Flg_Inativo;

    @Column(name = "Dta_Remocao")
    private LocalDateTime Dta_Remocao;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        this.Id = id;
    }

    public String getUrl() {
        return URL;
    }

    public void setUrl(String url) {
        this.URL = url;
    }

    public String getProvedorArmazenamento() {
        return Provedor_armazenamento;
    }

    public void setProvedorArmazenamento(String provedor_armazenamento) {
        this.Provedor_armazenamento = provedor_armazenamento;
    }

    public String getChaveArmazenamento() {
        return Chave_armazenamento;
    }

    public void setChaveArmazenamento(String chave_armazenamento) {
        this.Chave_armazenamento = chave_armazenamento;
    }

    public String getNomeArquivoOriginal() {
        return Nome_arquivo_original;
    }

    public void setNomeArquivoOriginal(String nome_arquivo_original) {
        this.Nome_arquivo_original = nome_arquivo_original;
    }

    public Long getTamanhoArquivoBytes() {
        return Tamanho_arquivo_bytes;
    }

    public void setTamanhoArquivoBytes(Long tamanho_arquivo_bytes) {
        this.Tamanho_arquivo_bytes = tamanho_arquivo_bytes;
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


