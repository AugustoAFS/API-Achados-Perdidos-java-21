package com.AchadosPerdidos.API.Application.DTOs.Itens;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Date;

@Schema(description = "DTO completo de item")
public class ItensDTO {
    @Schema(description = "ID do item", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer id;

    @Schema(description = "Nome do item", example = "Chave do Laboratorio")
    private String nome;

    @Schema(description = "Descricao do item", example = "Chave do laboratorio de informatica, cor prata")
    private String descricao;

    @Schema(description = "Data/hora em que o item foi encontrado", example = "2024-01-01T10:30:00")
    private Date encontradoEm;

    @Schema(description = "ID do usuario relator", example = "1")
    private Integer usuarioRelatorId;

    @Schema(description = "ID do local", example = "2")
    private Integer localId;

    @Schema(description = "ID do status do item", example = "1")
    private Integer statusItemId;

    @Schema(description = "Data de criacao do registro", example = "2024-01-01T00:00:00")
    private Date dtaCriacao;

    @Schema(description = "Flag de inativacao", example = "false")
    private Boolean flgInativo;

    @Schema(description = "Data de remocao logica", example = "2024-02-01T00:00:00")
    private Date dtaRemocao;

    public ItensDTO() {
        // Construtor padrao para frameworks de serializacao
    }

    public ItensDTO(Integer id, String nome, String descricao, Date encontradoEm, Integer usuarioRelatorId, Integer localId, Integer statusItemId, Date dtaCriacao, Boolean flgInativo, Date dtaRemocao) {
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

    public Date getEncontradoEm() {
        return encontradoEm;
    }

    public void setEncontradoEm(Date encontradoEm) {
        this.encontradoEm = encontradoEm;
    }

    public Integer getUsuarioRelatorId() {
        return usuarioRelatorId;
    }

    public void setUsuarioRelatorId(Integer usuarioRelatorId) {
        this.usuarioRelatorId = usuarioRelatorId;
    }

    public Integer getLocalId() {
        return localId;
    }

    public void setLocalId(Integer localId) {
        this.localId = localId;
    }

    public Integer getStatusItemId() {
        return statusItemId;
    }

    public void setStatusItemId(Integer statusItemId) {
        this.statusItemId = statusItemId;
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

