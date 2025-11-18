package com.AchadosPerdidos.API.Application.DTOs.ItemDevolvido;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO completo de item devolvido")
public class ItemDevolvidoDTO {
    @Schema(description = "ID do item devolvido", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer id;

    @Schema(description = "ID do item relacionado", example = "1")
    private Integer itemId;

    @Schema(description = "Detalhes da devolucao", example = "Item devolvido ao proprietario apos confirmacao de identidade")
    private String detalhesDevolucao;

    @Schema(description = "ID do usuario que devolveu o item", example = "1")
    private Integer usuarioDevolvedorId;

    @Schema(description = "ID do usuario que achou o item (opcional)", example = "2")
    private Integer usuarioAchouId;

    @Schema(description = "Data de criacao", example = "2024-01-01T00:00:00")
    private java.util.Date dtaCriacao;

    @Schema(description = "Flag de inativacao", example = "false")
    private Boolean flgInativo;

    @Schema(description = "Data de remocao logica", example = "2024-02-01T00:00:00")
    private java.util.Date dtaRemocao;

    public ItemDevolvidoDTO() {
        // Construtor padrao para frameworks de serializacao
    }

    public ItemDevolvidoDTO(Integer id, Integer itemId, String detalhesDevolucao, Integer usuarioDevolvedorId, Integer usuarioAchouId, java.util.Date dtaCriacao, Boolean flgInativo, java.util.Date dtaRemocao) {
        this.id = id;
        this.itemId = itemId;
        this.detalhesDevolucao = detalhesDevolucao;
        this.usuarioDevolvedorId = usuarioDevolvedorId;
        this.usuarioAchouId = usuarioAchouId;
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

    public String getDetalhesDevolucao() {
        return detalhesDevolucao;
    }

    public void setDetalhesDevolucao(String detalhesDevolucao) {
        this.detalhesDevolucao = detalhesDevolucao;
    }

    public Integer getUsuarioDevolvedorId() {
        return usuarioDevolvedorId;
    }

    public void setUsuarioDevolvedorId(Integer usuarioDevolvedorId) {
        this.usuarioDevolvedorId = usuarioDevolvedorId;
    }

    public Integer getUsuarioAchouId() {
        return usuarioAchouId;
    }

    public void setUsuarioAchouId(Integer usuarioAchouId) {
        this.usuarioAchouId = usuarioAchouId;
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

