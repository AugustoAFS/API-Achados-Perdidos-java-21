package com.AchadosPerdidos.API.Application.Services.Interfaces;

public interface IJWTService {

    String createToken(String email, String name, String role, String userId);

    boolean validateToken(String token);

    String getEmailFromToken(String token);

    String getRoleFromToken(String token);
}
