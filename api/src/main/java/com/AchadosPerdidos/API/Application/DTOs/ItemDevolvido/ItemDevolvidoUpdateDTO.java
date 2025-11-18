package com.AchadosPerdidos.API.Application.DTOs.ItemDevolvido;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO para atualizacao de item devolvido")
public class ItemDevolvidoUpdateDTO {
    @Schema(description = "Detalhes da devolucao", example = "Item devolvido ao proprietario apos confirmacao de identidade")
    private String detalhesDevolucao;

    @Schema(description = "ID do usuario que devolveu o item", example = "1")
    private Integer usuarioDevolvedorId;

    @Schema(description = "ID do usuario que achou o item (opcional)", example = "2")
    private Integer usuarioAchouId;

    @Schema(description = "Flag de inativacao", example = "false")
    private Boolean flgInativo;

    public ItemDevolvidoUpdateDTO() {
        // Construtor padrao para frameworks de serializacao
    }

    public ItemDevolvidoUpdateDTO(String detalhesDevolucao, Integer usuarioDevolvedorId, Integer usuarioAchouId, Boolean flgInativo) {
        this.detalhesDevolucao = detalhesDevolucao;
        this.usuarioDevolvedorId = usuarioDevolvedorId;
        this.usuarioAchouId = usuarioAchouId;
        this.flgInativo = flgInativo;
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

    public Boolean getFlgInativo() {
        return flgInativo;
    }

    public void setFlgInativo(Boolean flgInativo) {
        this.flgInativo = flgInativo;
    }
}

