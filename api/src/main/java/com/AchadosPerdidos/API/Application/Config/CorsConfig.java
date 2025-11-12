package com.AchadosPerdidos.API.Application.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

@Configuration
public class CorsConfig {

    private final Environment environment;

    public CorsConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@NonNull CorsRegistry registry) {
                String[] activeProfiles = environment.getActiveProfiles();
                
                // Verifica se está em produção
                boolean isProduction = Arrays.stream(activeProfiles)
                        .anyMatch(profile -> {
                            String lowerProfile = profile.toLowerCase();
                            return lowerProfile.equals("prd") || 
                                   lowerProfile.equals("prod") || 
                                   lowerProfile.equals("production");
                        });
                
                // Log do ambiente ativo para debug
                System.out.println("Ambiente ativo: " + Arrays.toString(activeProfiles));
                
                if (isProduction) {
                    registry.addMapping("/**")
                            .allowedOrigins(
                                    "https://api-achadosperdidos.com.br",
                                    "https://www.api-achadosperdidos.com.br",
                                    "https://achadosperdidos.com",
                                    "https://www.achadosperdidos.com",
                                    "https://api.achadosperdidos.com"
                            )
                            .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                            .allowedHeaders("*")
                            .exposedHeaders("Authorization", "Content-Type", "X-Requested-With", "X-Token-Expiry")
                            .allowCredentials(true)
                            .maxAge(3600);
                } else {
                    // Desenvolvimento: remove "*" para compatibilidade com allowCredentials(true)
                    registry.addMapping("/**")
                            .allowedOriginPatterns(
                                    "http://localhost:*",
                                    "http://127.0.0.1:*"
                            )
                            .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                            .allowedHeaders("*")
                            .exposedHeaders("Authorization", "Content-Type", "X-Requested-With", "X-Token-Expiry")
                            .allowCredentials(true)
                            .maxAge(3600);
                }
            }
        };
    }
}

