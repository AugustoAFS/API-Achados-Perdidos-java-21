package com.AchadosPerdidos.API.Application.DTOs.ItemDoado;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(description = "DTO para criacao de item doado")
public class ItemDoadoCreateDTO {
    @Schema(description = "ID do item relacionado", example = "1", required = true)
    private Integer itemId;

    @Schema(description = "Data em que o item foi doado", example = "2024-01-01T00:00:00")
    private LocalDateTime doadoEm;

    public ItemDoadoCreateDTO() {
        // Construtor padrao para frameworks de serializacao
    }

    public ItemDoadoCreateDTO(Integer itemId, LocalDateTime doadoEm) {
        this.itemId = itemId;
        this.doadoEm = doadoEm;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public LocalDateTime getDoadoEm() {
        return doadoEm;
    }

    public void setDoadoEm(LocalDateTime doadoEm) {
        this.doadoEm = doadoEm;
    }
}

