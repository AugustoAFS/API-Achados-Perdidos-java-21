package com.AchadosPerdidos.API.Application.DTOs.Auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para resposta de autenticação contendo apenas o token JWT
 * Por questões de segurança, não retorna informações adicionais do usuário
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenResponseDTO {
    private String token;
}

