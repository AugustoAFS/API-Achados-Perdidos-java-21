package com.AchadosPerdidos.API.Application.DTOs.Auth;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO de requisição de login")
public class LoginRequestDTO {
    
    @Schema(description = "Email do usuário", example = "usuario@example.com", required = true)
    private String email;
    
    @Schema(description = "Senha do usuário", example = "senha123", required = true)
    private String senha;
    
    public LoginRequestDTO() {
    }
    
    public LoginRequestDTO(String email, String senha) {
        this.email = email;
        this.senha = senha;
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
}

