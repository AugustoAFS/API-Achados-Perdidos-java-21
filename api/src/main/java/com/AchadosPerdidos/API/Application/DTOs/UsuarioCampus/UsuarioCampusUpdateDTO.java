package com.AchadosPerdidos.API.Application.DTOs.UsuarioCampus;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO para atualizacao de relacionamento usuario-campus")
public class UsuarioCampusUpdateDTO {
    @Schema(description = "ID do usuario", example = "1")
    private Integer usuarioId;

    @Schema(description = "ID do campus", example = "1")
    private Integer campusId;

    @Schema(description = "Flag de inativacao", example = "false")
    private Boolean flgInativo;

    public UsuarioCampusUpdateDTO() {
        // Construtor padrao para frameworks de serializacao
    }

    public UsuarioCampusUpdateDTO(Integer usuarioId, Integer campusId, Boolean flgInativo) {
        this.usuarioId = usuarioId;
        this.campusId = campusId;
        this.flgInativo = flgInativo;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Integer getCampusId() {
        return campusId;
    }

    public void setCampusId(Integer campusId) {
        this.campusId = campusId;
    }

    public Boolean getFlgInativo() {
        return flgInativo;
    }

    public void setFlgInativo(Boolean flgInativo) {
        this.flgInativo = flgInativo;
    }
}

