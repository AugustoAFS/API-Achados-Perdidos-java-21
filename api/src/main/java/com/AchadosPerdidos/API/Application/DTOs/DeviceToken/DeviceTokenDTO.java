package com.AchadosPerdidos.API.Application.DTOs.DeviceToken;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Date;

@Schema(description = "DTO para token de dispositivo")
public class DeviceTokenDTO {
    @Schema(description = "ID do token", example = "1")
    private Integer id;

    @Schema(description = "ID do usuário", example = "1")
    private Integer usuarioId;

    @Schema(description = "Token do dispositivo (OneSignal Player ID)", example = "12345678-1234-1234-1234-123456789012")
    private String token;

    @Schema(description = "Plataforma do dispositivo", example = "ANDROID", allowableValues = {"ANDROID", "IOS"})
    private String plataforma;

    @Schema(description = "Data de criação")
    private Date dtaCriacao;

    @Schema(description = "Data de atualização")
    private Date dtaAtualizacao;

    @Schema(description = "Flag indicando se o token está inativo", example = "false")
    private Boolean flgInativo;

    @Schema(description = "Data de remoção")
    private Date dtaRemocao;

    public DeviceTokenDTO() {
        // Construtor padrão para frameworks de serialização
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(String plataforma) {
        this.plataforma = plataforma;
    }

    public Date getDtaCriacao() {
        return dtaCriacao;
    }

    public void setDtaCriacao(Date dtaCriacao) {
        this.dtaCriacao = dtaCriacao;
    }

    public Date getDtaAtualizacao() {
        return dtaAtualizacao;
    }

    public void setDtaAtualizacao(Date dtaAtualizacao) {
        this.dtaAtualizacao = dtaAtualizacao;
    }

    public Boolean getFlgInativo() {
        return flgInativo;
    }

    public void setFlgInativo(Boolean flgInativo) {
        this.flgInativo = flgInativo;
    }

    public Date getDtaRemocao() {
        return dtaRemocao;
    }

    public void setDtaRemocao(Date dtaRemocao) {
        this.dtaRemocao = dtaRemocao;
    }
}

