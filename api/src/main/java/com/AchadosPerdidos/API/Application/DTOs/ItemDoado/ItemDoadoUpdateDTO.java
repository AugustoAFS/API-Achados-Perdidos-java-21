package com.AchadosPerdidos.API.Application.DTOs.ItemDoado;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(description = "DTO para atualizacao de item doado")
public class ItemDoadoUpdateDTO {
    @Schema(description = "Data em que o item foi doado", example = "2024-01-01T00:00:00")
    private LocalDateTime doadoEm;

    @Schema(description = "Flag de inativacao", example = "false")
    private Boolean flgInativo;

    public ItemDoadoUpdateDTO() {
        // Construtor padrao para frameworks de serializacao
    }

    public ItemDoadoUpdateDTO(LocalDateTime doadoEm, Boolean flgInativo) {
        this.doadoEm = doadoEm;
        this.flgInativo = flgInativo;
    }

    public LocalDateTime getDoadoEm() {
        return doadoEm;
    }

    public void setDoadoEm(LocalDateTime doadoEm) {
        this.doadoEm = doadoEm;
    }

    public Boolean getFlgInativo() {
        return flgInativo;
    }

    public void setFlgInativo(Boolean flgInativo) {
        this.flgInativo = flgInativo;
    }
}

