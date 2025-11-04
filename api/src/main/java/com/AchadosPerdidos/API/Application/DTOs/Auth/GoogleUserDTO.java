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
@Schema(description = "DTO para dados do usuário do Google")
public class GoogleUserDTO {
    
    @Schema(description = "ID do usuário no Google", example = "123456789")
    private String id;
    
    @Schema(description = "Email do usuário", example = "joao@gmail.com")
    private String email;
    
    @Schema(description = "Nome do usuário", example = "João Silva")
    private String name;
    
    @Schema(description = "URL da foto de perfil", example = "https://lh3.googleusercontent.com/...")
    private String picture;
    
    @Schema(description = "Email verificado", example = "true")
    private boolean verified_email;
}
