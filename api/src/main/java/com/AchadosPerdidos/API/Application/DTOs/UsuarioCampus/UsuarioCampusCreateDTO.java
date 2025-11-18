package com.AchadosPerdidos.API.Application.DTOs.UsuarioCampus;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO para criacao de relacionamento usuario-campus")
public class UsuarioCampusCreateDTO {
    @Schema(description = "ID do usuario", example = "1", required = true)
    private Integer usuarioId;

    @Schema(description = "ID do campus", example = "1", required = true)
    private Integer campusId;

    public UsuarioCampusCreateDTO() {
        // Construtor padrao para frameworks de serializacao
    }

    public UsuarioCampusCreateDTO(Integer usuarioId, Integer campusId) {
        this.usuarioId = usuarioId;
        this.campusId = campusId;
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
}

