package com.AchadosPerdidos.API.Application.DTOs.Auth;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO para redefinir senha de usuário")
public class RedefinirSenhaDTO {
    
    @Schema(description = "Nova senha em texto plano", example = "novaSenha123", required = true)
    private String novaSenha;
    
    public RedefinirSenhaDTO() {
    }
    
    public RedefinirSenhaDTO(String novaSenha) {
        this.novaSenha = novaSenha;
    }
    
    public String getNovaSenha() {
        return novaSenha;
    }
    
    public void setNovaSenha(String novaSenha) {
        this.novaSenha = novaSenha;
    }
}

