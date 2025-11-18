package com.AchadosPerdidos.API.Application.DTOs.FotoItem;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO para atualizacao de relacionamento foto-item")
public class FotoItemUpdateDTO {
    @Schema(description = "ID do item", example = "1")
    private Integer itemId;

    @Schema(description = "ID da foto", example = "1")
    private Integer fotoId;

    @Schema(description = "Flag de inativacao", example = "false")
    private Boolean flgInativo;

    public FotoItemUpdateDTO() {
        // Construtor padrao para frameworks de serializacao
    }

    public FotoItemUpdateDTO(Integer itemId, Integer fotoId, Boolean flgInativo) {
        this.itemId = itemId;
        this.fotoId = fotoId;
        this.flgInativo = flgInativo;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getFotoId() {
        return fotoId;
    }

    public void setFotoId(Integer fotoId) {
        this.fotoId = fotoId;
    }

    public Boolean getFlgInativo() {
        return flgInativo;
    }

    public void setFlgInativo(Boolean flgInativo) {
        this.flgInativo = flgInativo;
    }
}

