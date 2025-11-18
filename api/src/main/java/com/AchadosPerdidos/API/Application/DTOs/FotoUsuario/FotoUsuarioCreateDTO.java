package com.AchadosPerdidos.API.Application.DTOs.FotoUsuario;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO para criacao de relacionamento foto-usuario")
public class FotoUsuarioCreateDTO {
    @Schema(description = "ID do usuario", example = "1", required = true)
    private Integer usuarioId;

    @Schema(description = "ID da foto", example = "1", required = true)
    private Integer fotoId;

    public FotoUsuarioCreateDTO() {
        // Construtor padrao para frameworks de serializacao
    }

    public FotoUsuarioCreateDTO(Integer usuarioId, Integer fotoId) {
        this.usuarioId = usuarioId;
        this.fotoId = fotoId;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Integer getFotoId() {
        return fotoId;
    }

    public void setFotoId(Integer fotoId) {
        this.fotoId = fotoId;
    }
}

