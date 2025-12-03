package com.AchadosPerdidos.API.Application.Services;

import com.AchadosPerdidos.API.Application.Config.JwtConfig;
import com.AchadosPerdidos.API.Application.Services.Interfaces.IJWTService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JWTService implements IJWTService {
    
    private static final Logger _log = LoggerFactory.getLogger(JWTService.class);
    
    private final SecretKey secretKey;
    private final JwtConfig jwtConfig;

    public JWTService(SecretKey secretKey, JwtConfig jwtConfig) {
        this.secretKey = secretKey;
        this.jwtConfig = jwtConfig;
    }
    
    @Override
    public String createToken(String email, String name, String role, String userId) {
        try {
            Map<String, Object> claims = new HashMap<>();
            claims.put("sub", userId);
            claims.put("email", email);
            claims.put("name", name);
            claims.put("role", role);
            claims.put("jti", java.util.UUID.randomUUID().toString());
            
            Date now = new Date();
            Date expiry = new Date(now.getTime() + (jwtConfig.getExpiryInMinutes() * 60L * 1000));
            
            String token = Jwts.builder()
                .claims(claims)
                .issuer(jwtConfig.getIssuer())
                .issuedAt(now)
                .expiration(expiry)
                .signWith(secretKey)
                .compact();

            _log.info("Token JWT gerado com sucesso para o usuário: {}", email);
            return token;
            
        } catch (Exception e) {
            _log.error("Erro ao gerar token JWT", e);
            throw new RuntimeException("Erro ao gerar token JWT", e);
        }
    }

    @Override
    @Cacheable(value = "jwtTokens", key = "#token", unless = "#result == false")
    public boolean validateToken(String token) {
        try {
            getClaims(token);
            return true;
        } catch (Exception e) {
            _log.warn("Token JWT inválido: {}", e.getMessage());
            return false;
        }
    }

    @Override
    public String getEmailFromToken(String token) {
        try {
            Claims claims = getClaims(token);
            return claims.get("email", String.class);
        } catch (Exception e) {
            _log.error("Erro ao extrair email do token", e);
            return null;
        }
    }

    @Override
    public String getRoleFromToken(String token) {
        try {
            Claims claims = getClaims(token);
            return claims.get("role", String.class);
        } catch (Exception e) {
            _log.error("Erro ao extrair role do token", e);
            return null;
        }
    }


    private Claims getClaims(String token) {
        return Jwts.parser()
            .verifyWith(secretKey)
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }
}
