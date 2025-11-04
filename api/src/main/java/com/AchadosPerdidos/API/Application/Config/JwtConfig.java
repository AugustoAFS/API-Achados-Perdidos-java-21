package com.AchadosPerdidos.API.Application.Config;

import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

/**
 * Configuração específica para JWT
 * Contém beans e configurações relacionadas ao JWT
 */
@Configuration
public class JwtConfig {

    @Value("${jwt.secret-key}")
    private String SecretKey;

    @Value("${jwt.issuer}")
    private String Issuer;

    @Value("${jwt.audience}")
    private String Audience;

    @Value("${jwt.expiry-in-minutes:60}")
    private int ExpiryInMinutes;


    @Bean
    public SecretKey jwtSecretKey() {
        return Keys.hmacShaKeyFor(SecretKey.getBytes(StandardCharsets.UTF_8));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public String getIssuer() {
        return Issuer;
    }

    public String getAudience() {
        return Audience;
    }

    public int getExpiryInMinutes() {
        return ExpiryInMinutes;
    }
}

