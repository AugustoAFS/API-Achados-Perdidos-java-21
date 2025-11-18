package com.AchadosPerdidos.API.Application.DTOs.FotoUsuario;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO para atualizacao de relacionamento foto-usuario")
public class FotoUsuarioUpdateDTO {
    @Schema(description = "ID do usuario", example = "1")
    private Integer usuarioId;

    @Schema(description = "ID da foto", example = "1")
    private Integer fotoId;

    @Schema(description = "Flag de inativacao", example = "false")
    private Boolean flgInativo;

    public FotoUsuarioUpdateDTO() {
        // Construtor padrao para frameworks de serializacao
    }

    public FotoUsuarioUpdateDTO(Integer usuarioId, Integer fotoId, Boolean flgInativo) {
        this.usuarioId = usuarioId;
        this.fotoId = fotoId;
        this.flgInativo = flgInativo;
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

    public Boolean getFlgInativo() {
        return flgInativo;
    }

    public void setFlgInativo(Boolean flgInativo) {
        this.flgInativo = flgInativo;
    }
}

