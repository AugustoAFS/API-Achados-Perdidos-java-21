package com.AchadosPerdidos.API.Application.DTOs.Item;

import com.AchadosPerdidos.API.Domain.Enum.Tipo_ItemEnum;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO completo de item")
public class ItemDTO {
    @Schema(description = "ID do item", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer id;

    @Schema(description = "Nome do item", example = "Celular Samsung Galaxy")
    private String nome;

    @Schema(description = "Descricao do item", example = "Celular preto, modelo Galaxy S21")
    private String descricao;

    @Schema(description = "Tipo do item", example = "PERDIDO")
    private Tipo_ItemEnum tipoItem;

    @Schema(description = "ID do local onde o item foi encontrado/perdido", example = "1")
    private Integer localId;

    @Schema(description = "ID do usuario que relatou o item", example = "1")
    private Integer usuarioRelatorId;

    @Schema(description = "Data de criacao", example = "2024-01-01T00:00:00")
    private java.util.Date dtaCriacao;

    @Schema(description = "Flag de inativacao", example = "false")
    private Boolean flgInativo;

    @Schema(description = "Data de remocao logica", example = "2024-02-01T00:00:00")
    private java.util.Date dtaRemocao;

    public ItemDTO() {
        // Construtor padrao para frameworks de serializacao
    }

    public ItemDTO(Integer id, String nome, String descricao, Tipo_ItemEnum tipoItem, Integer localId, Integer usuarioRelatorId, java.util.Date dtaCriacao, Boolean flgInativo, java.util.Date dtaRemocao) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.tipoItem = tipoItem;
        this.localId = localId;
        this.usuarioRelatorId = usuarioRelatorId;
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

    public Tipo_ItemEnum getTipoItem() {
        return tipoItem;
    }

    public void setTipoItem(Tipo_ItemEnum tipoItem) {
        this.tipoItem = tipoItem;
    }

    public Integer getLocalId() {
        return localId;
    }

    public void setLocalId(Integer localId) {
        this.localId = localId;
    }

    public Integer getUsuarioRelatorId() {
        return usuarioRelatorId;
    }

    public void setUsuarioRelatorId(Integer usuarioRelatorId) {
        this.usuarioRelatorId = usuarioRelatorId;
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

