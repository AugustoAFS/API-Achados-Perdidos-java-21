package com.AchadosPerdidos.API.Application.DTOs.Fotos;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO completo de foto")
public class FotosDTO {
    @Schema(description = "ID da foto", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer id;

    @Schema(description = "URL da foto", example = "https://bucket.s3.amazonaws.com/fotos/foto_item_123.jpg")
    private String url;

    @Schema(description = "Provedor de armazenamento", example = "S3")
    private String provedorArmazenamento;

    @Schema(description = "Chave de armazenamento", example = "fotos/foto_item_123.jpg")
    private String chaveArmazenamento;

    @Schema(description = "Nome original do arquivo", example = "foto_item_123.jpg")
    private String nomeArquivoOriginal;

    @Schema(description = "Tamanho em bytes", example = "1024000")
    private Long tamanhoArquivoBytes;

    @Schema(description = "Data de criacao", example = "2024-01-01T00:00:00")
    private java.util.Date dtaCriacao;

    @Schema(description = "Flag de inativacao", example = "false")
    private Boolean flgInativo;

    @Schema(description = "Data de remocao logica", example = "2024-02-01T00:00:00")
    private java.util.Date dtaRemocao;

    public FotosDTO() {
        // Construtor padrao para frameworks de serializacao
    }

    public FotosDTO(Integer id, String url, String provedorArmazenamento, String chaveArmazenamento, String nomeArquivoOriginal, Long tamanhoArquivoBytes, java.util.Date dtaCriacao, Boolean flgInativo, java.util.Date dtaRemocao) {
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

    public String getProvedorArmazenamento() {
        return provedorArmazenamento;
    }

    public void setProvedorArmazenamento(String provedorArmazenamento) {
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

    public java.util.Date getDtaCriacao() {
        return dtaCriacao;
    }

    public void setDtaCriacao(java.util.Date dtaCriacao) {
        this.dtaCriacao = dtaCriacao;
    }

    public Boolean getFlgInativo() {
        return flgInativo;
    }

    public void setFlgInativo(Boolean flgInativo) {
        this.flgInativo = flgInativo;
    }

    public java.util.Date getDtaRemocao() {
        return dtaRemocao;
    }

    public void setDtaRemocao(java.util.Date dtaRemocao) {
        this.dtaRemocao = dtaRemocao;
    }
}

