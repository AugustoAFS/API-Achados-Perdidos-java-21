package com.AchadosPerdidos.API.Application.DTOs.ItemPerdido;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(description = "DTO para atualizacao de item perdido")
public class ItemPerdidoUpdateDTO {
    @Schema(description = "Data em que o item foi perdido", example = "2024-01-01T00:00:00")
    private LocalDateTime perdidoEm;

    @Schema(description = "Flag de inativacao", example = "false")
    private Boolean flgInativo;

    public ItemPerdidoUpdateDTO() {
        // Construtor padrao para frameworks de serializacao
    }

    public ItemPerdidoUpdateDTO(LocalDateTime perdidoEm, Boolean flgInativo) {
        this.perdidoEm = perdidoEm;
        this.flgInativo = flgInativo;
    }

    public LocalDateTime getPerdidoEm() {
        return perdidoEm;
    }

    public void setPerdidoEm(LocalDateTime perdidoEm) {
        this.perdidoEm = perdidoEm;
    }

    public Boolean getFlgInativo() {
        return flgInativo;
    }

    public void setFlgInativo(Boolean flgInativo) {
        this.flgInativo = flgInativo;
    }
}

