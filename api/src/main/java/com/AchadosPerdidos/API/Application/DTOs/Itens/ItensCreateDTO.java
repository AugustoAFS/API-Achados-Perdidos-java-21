package com.AchadosPerdidos.API.Application.DTOs.Itens;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Date;

@Schema(description = "DTO para criacao de item")
public class ItensCreateDTO {
    @Schema(description = "Nome do item", example = "Chave do Laboratorio", required = true)
    private String nome;

    @Schema(description = "Descricao do item", example = "Chave do laboratorio de informatica, cor prata", required = true)
    private String descricao;

    @Schema(description = "ID do status do item", example = "1", required = true)
    private Integer statusItemId;

    @Schema(description = "ID do local onde o item foi encontrado", example = "2", required = true)
    private Integer localId;

    @Schema(description = "Data/hora em que o item foi encontrado", example = "2024-01-01T10:30:00")
    private Date encontradoEm;

    public ItensCreateDTO() {
        // Construtor padrao para frameworks de serializacao
    }

    public ItensCreateDTO(String nome, String descricao, Integer statusItemId, Integer localId, Date encontradoEm) {
        this.nome = nome;
        this.descricao = descricao;
        this.statusItemId = statusItemId;
        this.localId = localId;
        this.encontradoEm = encontradoEm;
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

    public Integer getStatusItemId() {
        return statusItemId;
    }

    public void setStatusItemId(Integer statusItemId) {
        this.statusItemId = statusItemId;
    }

    public Integer getLocalId() {
        return localId;
    }

    public void setLocalId(Integer localId) {
        this.localId = localId;
    }

    public Date getEncontradoEm() {
        return encontradoEm;
    }

    public void setEncontradoEm(Date encontradoEm) {
        this.encontradoEm = encontradoEm;
    }
}

