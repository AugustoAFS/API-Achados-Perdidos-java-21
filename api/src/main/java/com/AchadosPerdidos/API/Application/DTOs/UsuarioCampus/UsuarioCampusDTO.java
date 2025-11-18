package com.AchadosPerdidos.API.Application.DTOs.UsuarioCampus;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO completo de relacionamento usuario-campus")
public class UsuarioCampusDTO {
    @Schema(description = "ID do relacionamento", example = "1")
    private Integer id;

    @Schema(description = "ID do usuario", example = "1")
    private Integer usuarioId;

    @Schema(description = "ID do campus", example = "1")
    private Integer campusId;

    @Schema(description = "Data de criacao", example = "2024-01-01T00:00:00")
    private java.util.Date dtaCriacao;

    @Schema(description = "Flag de inativacao", example = "false")
    private Boolean flgInativo;

    @Schema(description = "Data de remocao logica", example = "2024-02-01T00:00:00")
    private java.util.Date dtaRemocao;

    public UsuarioCampusDTO() {
        // Construtor padrao para frameworks de serializacao
    }

    public UsuarioCampusDTO(Integer id, Integer usuarioId, Integer campusId, java.util.Date dtaCriacao, Boolean flgInativo, java.util.Date dtaRemocao) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.campusId = campusId;
        this.dtaCriacao = dtaCriacao;
        this.flgInativo = flgInativo;
        this.dtaRemocao = dtaRemocao;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public java.util.Date getDtaCriacao() {
        return dtaCriacao;
    }

    public void setDtaCriacao(java.util.Date dtaCriacao) {
        this.dtaCriacao = dtaCriacao;
    }

    public Boolean getFlgInativo() {
        return flgInativo;
    }

    public void setFlgInativo(Boolean flgInativo) {
        this.flgInativo = flgInativo;
    }

    public java.util.Date getDtaRemocao() {
        return dtaRemocao;
    }

    public void setDtaRemocao(java.util.Date dtaRemocao) {
        this.dtaRemocao = dtaRemocao;
    }
}

