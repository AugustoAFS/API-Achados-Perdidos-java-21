package com.AchadosPerdidos.API.Application.DTOs.ItemAchado;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(description = "DTO para atualizacao de item achado")
public class ItemAchadoUpdateDTO {
    @Schema(description = "Data em que o item foi encontrado", example = "2024-01-01T00:00:00")
    private LocalDateTime encontradoEm;

    @Schema(description = "Flag de inativacao", example = "false")
    private Boolean flgInativo;

    public ItemAchadoUpdateDTO() {
        // Construtor padrao para frameworks de serializacao
    }

    public ItemAchadoUpdateDTO(LocalDateTime encontradoEm, Boolean flgInativo) {
        this.encontradoEm = encontradoEm;
        this.flgInativo = flgInativo;
    }

    public LocalDateTime getEncontradoEm() {
        return encontradoEm;
    }

    public void setEncontradoEm(LocalDateTime encontradoEm) {
        this.encontradoEm = encontradoEm;
    }

    public Boolean getFlgInativo() {
        return flgInativo;
    }

    public void setFlgInativo(Boolean flgInativo) {
        this.flgInativo = flgInativo;
    }
}

