package com.AchadosPerdidos.API.Application.DTOs.Auth;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO para requisição de login")
public class LoginRequestDTO {

    @Schema(description = "Email do usuário", example = "usuario@ifpr.edu.br", required = true)
    private String email;

    @Schema(description = "Senha do usuário", example = "senha123", required = true)
    private String senha;

    @Schema(description = "Token do dispositivo para push notifications (OneSignal Player ID)", example = "12345678-1234-1234-1234-123456789012", required = false)
    private String deviceToken;

    @Schema(description = "Plataforma do dispositivo", example = "ANDROID", allowableValues = {"ANDROID", "IOS"}, required = false)
    private String plataforma;

    public LoginRequestDTO() {
    }

    public LoginRequestDTO(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }

    public LoginRequestDTO(String email, String senha, String deviceToken, String plataforma) {
        this.email = email;
        this.senha = senha;
        this.deviceToken = deviceToken;
        this.plataforma = plataforma;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(String plataforma) {
        this.plataforma = plataforma;
    }
}
