package com.AchadosPerdidos.API.Application.DTOs.DeviceToken;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO para atualização de token de dispositivo")
public class DeviceTokenUpdateDTO {
    @Schema(description = "Token do dispositivo (OneSignal Player ID)", example = "12345678-1234-1234-1234-123456789012")
    private String token;

    @Schema(description = "Plataforma do dispositivo", example = "ANDROID", allowableValues = {"ANDROID", "IOS"})
    private String plataforma;

    @Schema(description = "Flag indicando se o token está inativo", example = "false")
    private Boolean flgInativo;

    public DeviceTokenUpdateDTO() {
        // Construtor padrão para frameworks de serialização
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

    public Boolean getFlgInativo() {
        return flgInativo;
    }

    public void setFlgInativo(Boolean flgInativo) {
        this.flgInativo = flgInativo;
    }
}

