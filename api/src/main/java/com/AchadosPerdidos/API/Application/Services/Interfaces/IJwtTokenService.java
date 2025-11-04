package com.AchadosPerdidos.API.Application.Services.Interfaces;

/**
 * Interface para serviços de JWT
 */
public interface IJwtTokenService {

    String generateToken(String email, String name, String role, String userId);

    boolean validateToken(String token);

    String getEmailFromToken(String token);

    String getUserIdFromToken(String token);

    boolean isTokenExpired(String token);
}