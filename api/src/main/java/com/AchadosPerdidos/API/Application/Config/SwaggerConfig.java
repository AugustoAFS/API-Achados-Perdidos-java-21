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

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

@Configuration
public class SwaggerConfig {

    private static final Logger logger = Logger.getLogger(SwaggerConfig.class.getName());

    @Value("${swagger.server.url:}")
    private String serverUrl;

    @Value("${swagger.server.description:}")
    private String serverDescription;

    private final EnvironmentConfig environmentConfig;

    public SwaggerConfig(EnvironmentConfig environmentConfig) {
        this.environmentConfig = environmentConfig;
    }

    @Bean
    public OpenAPI customOpenAPI() {
        // Define explicitamente o servidor para ignorar cabeçalhos do proxy DigitalOcean
        String serverUrlValue = getServerUrl();
        String serverDescriptionValue = getServerDescription();
        
        // Log único e essencial
        logger.info(String.format("Swagger configurado - URL: %s | Descrição: %s", serverUrlValue, serverDescriptionValue));
        
        // Verifica se está em produção para decidir se mostra múltiplos servidores
        boolean isProduction = environmentConfig.isProduction();
        
        List<Server> servers;
        if (isProduction) {
            // Em produção: mostra apenas o servidor de produção
            servers = List.of(
                    new Server()
                            .url(serverUrlValue)
                            .description(serverDescriptionValue)
            );
        } else {
            // Em desenvolvimento: mostra ambos os servidores para facilitar testes
            servers = List.of(
                    new Server()
                            .url(serverUrlValue)
                            .description(serverDescriptionValue),
                    new Server()
                            .url("https://api-achadosperdidos.com.br")
                            .description("Produção (para testes)")
            );
        }
        
        return new OpenAPI()
                .info(apiInfo())
                .servers(servers)
                .addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
                .components(new Components()
                        .addSecuritySchemes("Bearer Authentication", createAPIKeyScheme()));
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("api-achados-perdidos")
                .pathsToMatch("/api/**")
                .packagesToScan("com.AchadosPerdidos.API.Presentation.Controller")
                .build();
    }

    private String getServerUrl() {
        // PRIORIDADE 1: Propriedade do arquivo de configuração (tem prioridade máxima)
        if (serverUrl != null && !serverUrl.isEmpty() && !serverUrl.equals("${swagger.server.url:}")) {
            return serverUrl;
        }
        
        // PRIORIDADE 2: Verifica os perfis ativos usando utilitário centralizado
        boolean isProduction = environmentConfig.isProduction();
        boolean isDevelopment = environmentConfig.isDevelopment();
        
        // Lógica de decisão baseada nos perfis
        if (isProduction) {
            // Fallback robusto: verifica variável de ambiente DIGITALOCEAN_APP_DOMAIN
            String digitalOceanDomain = System.getenv("DIGITALOCEAN_APP_DOMAIN");
            if (digitalOceanDomain != null && !digitalOceanDomain.isEmpty()) {
                // Remove protocolo se já estiver presente
                String domain = digitalOceanDomain.replaceFirst("^https?://", "");
                return "https://" + domain;
            }
            // Domínio padrão de produção
            return "https://api-achadosperdidos.com.br";
        }
        
        if (isDevelopment) {
            return "http://localhost:8080";
        }
        
        // Se não encontrou nenhum perfil conhecido, verifica os perfis padrão
        String[] defaultProfiles = environmentConfig.getEnvironment().getDefaultProfiles();
        boolean isDefaultProduction = Arrays.stream(defaultProfiles)
                .anyMatch(profile -> profile.equalsIgnoreCase("prd"));
        
        if (isDefaultProduction) {
            // Fallback robusto para perfis padrão também
            String digitalOceanDomain = System.getenv("DIGITALOCEAN_APP_DOMAIN");
            if (digitalOceanDomain != null && !digitalOceanDomain.isEmpty()) {
                String domain = digitalOceanDomain.replaceFirst("^https?://", "");
                return "https://" + domain;
            }
            return "https://api-achadosperdidos.com.br";
        }
        
        // TRATAMENTO DE ERRO: Nenhum perfil identificado
        logger.severe("ERRO: Nenhum perfil válido identificado!");
        logger.severe(String.format("Perfis ativos: %s", Arrays.toString(environmentConfig.getActiveProfiles())));
        logger.severe(String.format("Perfis padrão: %s", Arrays.toString(environmentConfig.getEnvironment().getDefaultProfiles())));
        
        // Fallback final: tenta usar domínio da DigitalOcean se disponível
        String digitalOceanDomain = System.getenv("DIGITALOCEAN_APP_DOMAIN");
        if (digitalOceanDomain != null && !digitalOceanDomain.isEmpty()) {
            String domain = digitalOceanDomain.replaceFirst("^https?://", "");
            logger.warning(String.format("Usando domínio DigitalOcean como fallback: %s", domain));
            return "https://" + domain;
        }
        
        // Fallback seguro: assume desenvolvimento
        logger.warning("Usando localhost como fallback (pode estar incorreto!)");
        return "http://localhost:8080";
    }

    private String getServerDescription() {
        // PRIORIDADE 1: Propriedade do arquivo de configuração
        if (serverDescription != null && !serverDescription.isEmpty()) {
            return serverDescription;
        }
        
        // PRIORIDADE 2: Baseado nos perfis ativos usando utilitário centralizado
        if (environmentConfig.isProduction()) {
            return "Servidor de PRD";
        }
        
        if (environmentConfig.isDevelopment()) {
            return "Servidor de Desenvolvimento";
        }
        
        // Fallback
        return "Servidor (perfil não identificado)";
    }

    private Info apiInfo() {
        return new Info()
                .title("API Achados e Perdidos")
                .description("API para sistema de achados e perdidos com chat em tempo real")
                .version("1.0.0")
                .contact(new Contact()
                        .name("Equipe de Desenvolvimento")
                        .email("contato@achadosperdidos.com.br")
                        .url("https://api-achadosperdidos.com.br"))
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

