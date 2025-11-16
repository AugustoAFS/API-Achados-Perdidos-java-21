package com.AchadosPerdidos.API.Application.DTOs.Auth;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO para redefinir senha de usuário")
public class RedefinirSenhaDTO {
    
    @Schema(description = "CPF do usuário (opcional se matrícula for fornecida)", example = "12345678901")
    private String cpf;
    
    @Schema(description = "Matrícula do usuário (opcional se CPF for fornecido)", example = "2024001")
    private String matricula;
    
    @Schema(description = "Nova senha em texto plano", example = "novaSenha123", required = true)
    private String novaSenha;
    
    public RedefinirSenhaDTO() {
    }
    
    public RedefinirSenhaDTO(String cpf, String matricula, String novaSenha) {
        this.cpf = cpf;
        this.matricula = matricula;
        this.novaSenha = novaSenha;
    }
    
    public String getCpf() {
        return cpf;
    }
    
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    
    public String getMatricula() {
        return matricula;
    }
    
    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }
    
    public String getNovaSenha() {
        return novaSenha;
    }
    
    public void setNovaSenha(String novaSenha) {
        this.novaSenha = novaSenha;
    }
}
