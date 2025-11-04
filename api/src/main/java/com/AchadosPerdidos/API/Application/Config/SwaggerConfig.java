package com.AchadosPerdidos.API.Application.Config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Value("${swagger.server.url:}")
    private String serverUrl;

    @Value("${swagger.server.description:}")
    private String serverDescription;

    private final Environment environment;

    public SwaggerConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(apiInfo())
                .servers(List.of(
                        new Server()
                                .url(getServerUrl())
                                .description(getServerDescription())
                ))
                .addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
                .components(new Components()
                        .addSecuritySchemes("Bearer Authentication", createAPIKeyScheme()));
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public")
                .pathsToMatch("/api/**")
                .build();
    }

    private String getServerUrl() {
        if (serverUrl != null && !serverUrl.isEmpty()) {
            return serverUrl;
        }
        
        String[] activeProfiles = environment.getActiveProfiles();
        if (activeProfiles.length > 0) {
            String profile = activeProfiles[0];
            return switch (profile.toLowerCase()) {
                case "dev" -> "http://localhost:8080";
                case "prd", "prod", "production" -> "https://api.achadosperdidos.com.br";
                default -> "http://localhost:8080";
            };
        }
        return "http://localhost:8080";
    }

    private String getServerDescription() {
        if (serverDescription != null && !serverDescription.isEmpty()) {
            return serverDescription;
        }
        
        String[] activeProfiles = environment.getActiveProfiles();
        if (activeProfiles.length > 0) {
            String profile = activeProfiles[0];
            return switch (profile.toLowerCase()) {
                case "dev" -> "Servidor de Desenvolvimento";
                case "prd", "prod", "production" -> "Servidor de Produção";
                default -> "Servidor de Desenvolvimento";
            };
        }
        return "Servidor de Desenvolvimento";
    }

    private Info apiInfo() {
        return new Info()
                .title("API Achados e Perdidos")
                .description("API para sistema de achados e perdidos com chat em tempo real")
                .version("1.0.0")
                .contact(new Contact()
                        .name("Equipe de Desenvolvimento")
                        .email("contato@achadosperdidos.com.br")
                        .url("https://api.achadosperdidos.com.br"))
                .license(new License()
                        .name("MIT License")
                        .url("https://opensource.org/licenses/MIT"));
    }

    private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("bearer")
                .description("Insira o token JWT no formato: Bearer {token}");
    }
}

