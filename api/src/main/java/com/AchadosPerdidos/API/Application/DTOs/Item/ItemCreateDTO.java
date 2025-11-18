package com.AchadosPerdidos.API.Application.DTOs.Item;

import com.AchadosPerdidos.API.Domain.Enum.Tipo_ItemEnum;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO para criacao de item")
public class ItemCreateDTO {
    @Schema(description = "Nome do item", example = "Celular Samsung Galaxy", required = true)
    private String nome;

    @Schema(description = "Descricao do item", example = "Celular preto, modelo Galaxy S21", required = true)
    private String descricao;

    @Schema(description = "Tipo do item", example = "PERDIDO", required = true)
    private Tipo_ItemEnum tipoItem;

    @Schema(description = "ID do local onde o item foi encontrado/perdido", example = "1", required = true)
    private Integer localId;

    @Schema(description = "ID do usuario que relatou o item", example = "1", required = true)
    private Integer usuarioRelatorId;

    public ItemCreateDTO() {
        // Construtor padrao para frameworks de serializacao
    }

    public ItemCreateDTO(String nome, String descricao, Tipo_ItemEnum tipoItem, Integer localId, Integer usuarioRelatorId) {
        this.nome = nome;
        this.descricao = descricao;
        this.tipoItem = tipoItem;
        this.localId = localId;
        this.usuarioRelatorId = usuarioRelatorId;
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
}

