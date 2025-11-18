package com.AchadosPerdidos.API.Application.DTOs.Fotos;

import com.AchadosPerdidos.API.Domain.Enum.Provedor_Armazenamento;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO para criacao de foto")
public class FotosCreateDTO {
    @Schema(description = "URL da foto", example = "https://bucket.s3.amazonaws.com/fotos/foto_item_123.jpg", required = true)
    private String url;

    @Schema(description = "Provedor de armazenamento", example = "local")
    private Provedor_Armazenamento provedorArmazenamento;

    @Schema(description = "Chave de armazenamento", example = "fotos/foto_item_123.jpg")
    private String chaveArmazenamento;

    @Schema(description = "Nome original do arquivo", example = "foto_item_123.jpg")
    private String nomeArquivoOriginal;

    @Schema(description = "Tamanho em bytes", example = "1024000")
    private Long tamanhoArquivoBytes;

    public FotosCreateDTO() {
        // Construtor padrao para frameworks de serializacao
    }

    public FotosCreateDTO(String url, Provedor_Armazenamento provedorArmazenamento, String chaveArmazenamento, String nomeArquivoOriginal, Long tamanhoArquivoBytes) {
        this.url = url;
        this.provedorArmazenamento = provedorArmazenamento;
        this.chaveArmazenamento = chaveArmazenamento;
        this.nomeArquivoOriginal = nomeArquivoOriginal;
        this.tamanhoArquivoBytes = tamanhoArquivoBytes;
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
}

