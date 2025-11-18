package com.AchadosPerdidos.API.Application.DTOs.FotoItem;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO para criacao de relacionamento foto-item")
public class FotoItemCreateDTO {
    @Schema(description = "ID do item", example = "1", required = true)
    private Integer itemId;

    @Schema(description = "ID da foto", example = "1", required = true)
    private Integer fotoId;

    public FotoItemCreateDTO() {
        // Construtor padrao para frameworks de serializacao
    }

    public FotoItemCreateDTO(Integer itemId, Integer fotoId) {
        this.itemId = itemId;
        this.fotoId = fotoId;
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
}

