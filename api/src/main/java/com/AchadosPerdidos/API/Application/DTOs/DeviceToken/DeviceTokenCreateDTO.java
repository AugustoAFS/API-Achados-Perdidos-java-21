package com.AchadosPerdidos.API.Application.DTOs.DeviceToken;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO para criação de token de dispositivo")
public class DeviceTokenCreateDTO {
    @Schema(description = "ID do usuário", example = "1", required = true)
    private Integer usuarioId;

    @Schema(description = "Token do dispositivo (OneSignal Player ID)", example = "12345678-1234-1234-1234-123456789012", required = true)
    private String token;

    @Schema(description = "Plataforma do dispositivo", example = "ANDROID", allowableValues = {"ANDROID", "IOS"}, required = true)
    private String plataforma;

    public DeviceTokenCreateDTO() {
        // Construtor padrão para frameworks de serialização
    }

    public DeviceTokenCreateDTO(Integer usuarioId, String token, String plataforma) {
        this.usuarioId = usuarioId;
        this.token = token;
        this.plataforma = plataforma;
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
}

