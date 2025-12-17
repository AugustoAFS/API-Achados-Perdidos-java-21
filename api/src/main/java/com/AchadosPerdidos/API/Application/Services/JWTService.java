package com.AchadosPerdidos.API.Application.Services;

import com.AchadosPerdidos.API.Application.Config.JwtConfig;
import com.AchadosPerdidos.API.Application.DTOs.Auth.TokenValidationDTO;
import com.AchadosPerdidos.API.Application.Services.Interfaces.IJWTService;
import com.AchadosPerdidos.API.Application.Services.Interfaces.IUsuariosService;
import com.AchadosPerdidos.API.Application.DTOs.Usuario.UsuariosDTO;
import com.AchadosPerdidos.API.Exeptions.ValidationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
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
    
    @Autowired
    @Lazy
    private IUsuariosService usuariosService;

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

    @Override
    public UsuariosDTO getUsuarioFromToken(String token) {
        try {
            // Extrair email do token
            String email = getEmailFromToken(token);
            if (email == null || email.trim().isEmpty()) {
                _log.warn("Não foi possível extrair email do token");
                return null;
            }
            
            // Buscar usuário completo pelo email usando UsuariosService
            if (usuariosService == null) {
                _log.error("UsuariosService não está disponível");
                return null;
            }
            
            UsuariosDTO usuario = usuariosService.getUsuarioByEmail(email);
            _log.debug("Usuário encontrado pelo token: {}", email);
            return usuario;
            
        } catch (Exception e) {
            _log.error("Erro ao buscar usuário do token", e);
            return null;
        }
    }

    @Override
    public String getUserIdFromToken(String token) {
        try {
            Claims claims = getClaims(token);
            return claims.get("sub", String.class);
        } catch (Exception e) {
            _log.error("Erro ao extrair userId do token", e);
            return null;
        }
    }

    @Override
    public String extractToken(String authorizationHeader) {
        if (authorizationHeader == null || authorizationHeader.trim().isEmpty()) {
            throw new ValidationException("Header Authorization é obrigatório");
        }

        if (!authorizationHeader.startsWith("Bearer ")) {
            throw new ValidationException("Header Authorization deve estar no formato 'Bearer {token}'");
        }

        String token = authorizationHeader.substring(7);

        if (token.trim().isEmpty()) {
            throw new ValidationException("Token não pode estar vazio");
        }

        return token;
    }

    @Override
    public String logout(String authorizationHeader) {
        _log.debug("Processando logout");

        String token = extractToken(authorizationHeader);

        if (validateToken(token)) {
            _log.info("Logout realizado com sucesso");
            return "Logout realizado com sucesso. Descarte o token localmente.";
        }

        throw new ValidationException("Token inválido ou expirado");
    }

    @Override
    public TokenValidationDTO validateTokenAndGetInfo(String authorizationHeader) {
        _log.debug("Validando token e extraindo informações");

        String token = extractToken(authorizationHeader);

        if (validateToken(token)) {
            String email = getEmailFromToken(token);
            String role = getRoleFromToken(token);
            _log.info("Token válido para usuário: {}", email);
            return new TokenValidationDTO(true, email, role, "Token válido");
        }

        throw new ValidationException("Token inválido ou expirado");
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
            .verifyWith(secretKey)
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }
}
