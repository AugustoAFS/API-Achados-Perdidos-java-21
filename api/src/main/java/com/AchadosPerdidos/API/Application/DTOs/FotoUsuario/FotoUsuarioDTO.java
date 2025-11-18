package com.AchadosPerdidos.API.Application.DTOs.FotoUsuario;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO completo de relacionamento foto-usuario")
public class FotoUsuarioDTO {
    @Schema(description = "ID do relacionamento", example = "1")
    private Integer id;

    @Schema(description = "ID do usuario", example = "1")
    private Integer usuarioId;

    @Schema(description = "ID da foto", example = "1")
    private Integer fotoId;

    @Schema(description = "Data de criacao", example = "2024-01-01T00:00:00")
    private java.util.Date dtaCriacao;

    @Schema(description = "Flag de inativacao", example = "false")
    private Boolean flgInativo;

    @Schema(description = "Data de remocao logica", example = "2024-02-01T00:00:00")
    private java.util.Date dtaRemocao;

    public FotoUsuarioDTO() {
        // Construtor padrao para frameworks de serializacao
    }

    public FotoUsuarioDTO(Integer id, Integer usuarioId, Integer fotoId, java.util.Date dtaCriacao, Boolean flgInativo, java.util.Date dtaRemocao) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.fotoId = fotoId;
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

    public Integer getFotoId() {
        return fotoId;
    }

    public void setFotoId(Integer fotoId) {
        this.fotoId = fotoId;
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

