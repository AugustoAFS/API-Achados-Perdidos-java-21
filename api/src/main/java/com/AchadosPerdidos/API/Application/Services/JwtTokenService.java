package com.AchadosPerdidos.API.Application.Services;

import com.AchadosPerdidos.API.Application.Services.Interfaces.IJwtTokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Implementação do serviço de geração de tokens JWT
 */
@Service
public class JwtTokenService implements IJwtTokenService {
    
    private static final Logger _log = LoggerFactory.getLogger(JwtTokenService.class);
    
    @Value("${jwt.secret-key}")
    private String secretKey;
    
    @Value("${jwt.issuer}")
    private String issuer;
    
    @Value("${jwt.audience}")
    private String audience;
    
    @Value("${jwt.expiry-in-minutes:60}")
    private int expiryInMinutes;
    
    @Override
    public String generateToken(String email, String name, String role, String userId) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());
            
            Map<String, Object> claims = new HashMap<>();
            claims.put("sub", userId);
            claims.put("email", email);
            claims.put("name", name);
            claims.put("role", role);
            claims.put("jti", java.util.UUID.randomUUID().toString());
            
            Date now = new Date();
            Date expiry = new Date(now.getTime() + (expiryInMinutes * 60 * 1000));
            
            String token = Jwts.builder()
                .claims(claims)
                .issuer(issuer)
                .issuedAt(now)
                .expiration(expiry)
                .signWith(key)
                .compact();

            _log.info("Token JWT gerado com sucesso para o usuário: {}", email);
            return token;
            
        } catch (Exception e) {
            _log.error("Erro ao gerar token JWT", e);
            throw new RuntimeException("Erro ao gerar token JWT", e);
        }
    }

    @Override
    public boolean validateToken(String token) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
            Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            _log.warn("Token JWT inválido: {}", e.getMessage());
            return false;
        }
    }

    @Override
    public String getEmailFromToken(String token) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
            Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
            return claims.get("email", String.class);
        } catch (Exception e) {
            _log.error("Erro ao extrair email do token", e);
            return null;
        }
    }

    @Override
    public String getUserIdFromToken(String token) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
            Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
            return claims.getSubject();
        } catch (Exception e) {
            _log.error("Erro ao extrair ID do usuário do token", e);
            return null;
        }
    }

    @Override
    public boolean isTokenExpired(String token) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
            Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
            return claims.getExpiration().before(new Date());
        } catch (Exception e) {
            _log.error("Erro ao verificar expiração do token", e);
            return true;
        }
    }
}
