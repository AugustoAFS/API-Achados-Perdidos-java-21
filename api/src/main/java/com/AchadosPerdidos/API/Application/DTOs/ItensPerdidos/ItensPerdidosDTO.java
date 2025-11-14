package com.AchadosPerdidos.API.Application.DTOs.ItensPerdidos;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

public class ItensPerdidosDTO {
    @Schema(description = "ID unico do item", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private int id;

    @Schema(description = "Nome do item", example = "Chave do Laboratorio")
    private String nome;

    @Schema(description = "Descricao detalhada do item", example = "Chave do laboratorio de informatica, cor prata")
    private String descricao;

    @Schema(description = "Data e hora em que o item foi encontrado", example = "2024-01-01T10:30:00", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime encontradoEm;

    @Schema(description = "ID do usuario que relatou o item", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private int usuarioRelatorId;

    @Schema(description = "ID do local onde o item foi encontrado", example = "2")
    private int localId;

    @Schema(description = "ID do status do item", example = "1")
    private int statusItemId;

    @Schema(description = "Data de criacao", example = "2024-01-01T00:00:00", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime dtaCriacao;

    @Schema(description = "Flag indicando se esta inativo", example = "false")
    private Boolean flgInativo;

    @Schema(description = "Data de remocao", example = "null", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime dtaRemocao;

    public ItensPerdidosDTO() {
        // Construtor padrao para frameworks de serializacao
    }

    public ItensPerdidosDTO(int id, String nome, String descricao, LocalDateTime encontradoEm, int usuarioRelatorId, int localId, int statusItemId, LocalDateTime dtaCriacao, Boolean flgInativo, LocalDateTime dtaRemocao) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.encontradoEm = encontradoEm;
        this.usuarioRelatorId = usuarioRelatorId;
        this.localId = localId;
        this.statusItemId = statusItemId;
        this.dtaCriacao = dtaCriacao;
        this.flgInativo = flgInativo;
        this.dtaRemocao = dtaRemocao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public LocalDateTime getEncontradoEm() {
        return encontradoEm;
    }

    public void setEncontradoEm(LocalDateTime encontradoEm) {
        this.encontradoEm = encontradoEm;
    }

    public int getUsuarioRelatorId() {
        return usuarioRelatorId;
    }

    public void setUsuarioRelatorId(int usuarioRelatorId) {
        this.usuarioRelatorId = usuarioRelatorId;
    }

    public int getLocalId() {
        return localId;
    }

    public void setLocalId(int localId) {
        this.localId = localId;
    }

    public int getStatusItemId() {
        return statusItemId;
    }

    public void setStatusItemId(int statusItemId) {
        this.statusItemId = statusItemId;
    }

    public LocalDateTime getDtaCriacao() {
        return dtaCriacao;
    }

    public void setDtaCriacao(LocalDateTime dtaCriacao) {
        this.dtaCriacao = dtaCriacao;
    }

    public Boolean getFlgInativo() {
        return flgInativo;
    }

    public void setFlgInativo(Boolean flgInativo) {
        this.flgInativo = flgInativo;
    }

    public LocalDateTime getDtaRemocao() {
        return dtaRemocao;
    }

    public void setDtaRemocao(LocalDateTime dtaRemocao) {
        this.dtaRemocao = dtaRemocao;
    }
}

