package com.AchadosPerdidos.API.Application.DTOs.ItemDoado;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO completo de item doado")
public class ItemDoadoDTO {
    @Schema(description = "ID do item doado", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer id;

    @Schema(description = "ID do item relacionado", example = "1")
    private Integer itemId;

    @Schema(description = "Data em que o item foi doado", example = "2024-01-01T00:00:00")
    private java.util.Date doadoEm;

    @Schema(description = "Data de criacao", example = "2024-01-01T00:00:00")
    private java.util.Date dtaCriacao;

    @Schema(description = "Flag de inativacao", example = "false")
    private Boolean flgInativo;

    @Schema(description = "Data de remocao logica", example = "2024-02-01T00:00:00")
    private java.util.Date dtaRemocao;

    public ItemDoadoDTO() {
        // Construtor padrao para frameworks de serializacao
    }

    public ItemDoadoDTO(Integer id, Integer itemId, java.util.Date doadoEm, java.util.Date dtaCriacao, Boolean flgInativo, java.util.Date dtaRemocao) {
        this.id = id;
        this.itemId = itemId;
        this.doadoEm = doadoEm;
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

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public java.util.Date getDoadoEm() {
        return doadoEm;
    }

    public void setDoadoEm(java.util.Date doadoEm) {
        this.doadoEm = doadoEm;
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

