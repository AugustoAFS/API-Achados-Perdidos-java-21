package com.AchadosPerdidos.API.Application.DTOs.ItemDevolvido;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO para criacao de item devolvido")
public class ItemDevolvidoCreateDTO {
    @Schema(description = "ID do item relacionado", example = "1", required = true)
    private Integer itemId;

    @Schema(description = "Detalhes da devolucao", example = "Item devolvido ao proprietario apos confirmacao de identidade", required = true)
    private String detalhesDevolucao;

    @Schema(description = "ID do usuario que devolveu o item", example = "1", required = true)
    private Integer usuarioDevolvedorId;

    @Schema(description = "ID do usuario que achou o item (opcional)", example = "2")
    private Integer usuarioAchouId;

    public ItemDevolvidoCreateDTO() {
        // Construtor padrao para frameworks de serializacao
    }

    public ItemDevolvidoCreateDTO(Integer itemId, String detalhesDevolucao, Integer usuarioDevolvedorId, Integer usuarioAchouId) {
        this.itemId = itemId;
        this.detalhesDevolucao = detalhesDevolucao;
        this.usuarioDevolvedorId = usuarioDevolvedorId;
        this.usuarioAchouId = usuarioAchouId;
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
}

