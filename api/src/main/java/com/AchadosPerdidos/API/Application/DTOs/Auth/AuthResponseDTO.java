package com.AchadosPerdidos.API.Application.DTOs.Auth;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO de resposta de autenticacao")
public class AuthResponseDTO {
    @Schema(description = "Token JWT de autenticacao", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String token;

    @Schema(description = "Tipo do token", example = "Bearer")
    private String tokenType;  

    @Schema(description = "Tempo de expiracao em segundos", example = "3600")
    private long expiresIn;

    @Schema(description = "ID do usuario", example = "1")
    private int id;     

    @Schema(description = "Nome do usuario", example = "Joao Silva")
    private String nome;

    @Schema(description = "Email do usuario", example = "joao@ifpr.edu.br")
    private String email;   

    @Schema(description = "Tipo de role do usuario", example = "Admin")
    private String role;

    @Schema(description = "Nome do campus", example = "IFPR - Sede Curitiba")
    private String campus;

    public AuthResponseDTO() {
        // Construtor padrao para frameworks de serializacao
    }
    
    public AuthResponseDTO(String token, String tokenType, long expiresIn, int id, String nome, String email, String role, String campus) {
        this.token = token;
        this.tokenType = tokenType;
        this.expiresIn = expiresIn;
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.role = role;
        this.campus = campus;
    }

    public String getToken() {
        return token;
    }

    public String getTokenType() {
        return tokenType;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public String getCampus() {
        return campus;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }
}