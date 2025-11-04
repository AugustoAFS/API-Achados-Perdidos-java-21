package com.AchadosPerdidos.API.Application.DTOs.Auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO de resposta de autenticação")
public class AuthResponseDTO {
    
    @Schema(description = "Token JWT de autenticação", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String token;
    
    @Schema(description = "Tipo do token", example = "Bearer")
    private String tokenType;
    
    @Schema(description = "Tempo de expiração em segundos", example = "3600")
    private long expiresIn;
    
    @Schema(description = "Informações do usuário autenticado")
    private UserInfoDTO user;
    
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "Informações do usuário")
    public static class UserInfoDTO {
        
        @Schema(description = "ID do usuário", example = "1")
        private int id;
        
        @Schema(description = "Nome do usuário", example = "João Silva")
        private String nome;
        
        @Schema(description = "Email do usuário", example = "joao@ifpr.edu.br")
        private String email;
        
        @Schema(description = "Tipo de role do usuário", example = "Admin")
        private String role;
        
        @Schema(description = "Nome do campus", example = "IFPR - Sede Curitiba")
        private String campus;
    }
}
