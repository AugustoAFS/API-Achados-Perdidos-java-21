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

    private final Environment environment;

    public SwaggerConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public OpenAPI customOpenAPI() {
        // Define explicitamente o servidor para ignorar cabeçalhos do proxy DigitalOcean
        String serverUrlValue = getServerUrl();
        String serverDescriptionValue = getServerDescription();
        
        // Logs simplificados para produção
        logger.info(String.format("Swagger Server URL: %s", serverUrlValue));
        logger.info(String.format("Swagger Description: %s", serverDescriptionValue));
        
        Server server = new Server()
                .url(serverUrlValue)
                .description(serverDescriptionValue);
        
        // OPÇÃO: Para mostrar múltiplos servidores no Swagger (produção + desenvolvimento)
        // Descomente as linhas abaixo e comente a linha .servers(List.of(server))
        // List<Server> servers = List.of(
        //     new Server().url("https://api-achadosperdidos.com.br").description("Produção"),
        //     new Server().url("http://localhost:8080").description("Desenvolvimento")
        // );
        
        return new OpenAPI()
                .info(apiInfo())
                .servers(List.of(server)) // Use 'servers' se ativar múltiplos servidores acima
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
        // PRIORIDADE 1: Propriedade do arquivo de configuração (tem prioridade máxima)
        if (serverUrl != null && !serverUrl.isEmpty() && !serverUrl.equals("${swagger.server.url:}")) {
            logger.info(String.format("Usando URL do arquivo de configuração: %s", serverUrl));
            return serverUrl;
        }
        
        // PRIORIDADE 2: Verifica os perfis ativos
        String[] activeProfiles = environment.getActiveProfiles();
        String[] defaultProfiles = environment.getDefaultProfiles();
        
        // Verifica se há perfil de PRODUÇÃO
        boolean isProduction = Arrays.stream(activeProfiles)
                .anyMatch(profile -> {
                    String lowerProfile = profile.toLowerCase();
                    return lowerProfile.equals("prd") || 
                           lowerProfile.equals("prod") || 
                           lowerProfile.equals("production");
                });
        
        // Verifica se há perfil de DESENVOLVIMENTO
        boolean isDevelopment = Arrays.stream(activeProfiles)
                .anyMatch(profile -> {
                    String lowerProfile = profile.toLowerCase();
                    return lowerProfile.equals("dev") || 
                           lowerProfile.equals("development");
                });
        
        // Lógica de decisão baseada nos perfis
        if (isProduction) {
            // Fallback robusto: verifica variável de ambiente DIGITALOCEAN_APP_DOMAIN
            String digitalOceanDomain = System.getenv("DIGITALOCEAN_APP_DOMAIN");
            if (digitalOceanDomain != null && !digitalOceanDomain.isEmpty()) {
                // Remove protocolo se já estiver presente
                String domain = digitalOceanDomain.replaceFirst("^https?://", "");
                String productionUrl = "https://" + domain;
                logger.info(String.format("Usando domínio detectado da DigitalOcean: %s", productionUrl));
                return productionUrl;
            }
            // Domínio padrão de produção
            return "https://api-achadosperdidos.com.br";
        }
        
        if (isDevelopment) {
            return "http://localhost:8080";
        }
        
        // Se não encontrou nenhum perfil conhecido, verifica os perfis padrão
        boolean isDefaultProduction = Arrays.stream(defaultProfiles)
                .anyMatch(profile -> {
                    String lowerProfile = profile.toLowerCase();
                    return lowerProfile.equals("prd") || 
                           lowerProfile.equals("prod") || 
                           lowerProfile.equals("production");
                });
        
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
        logger.severe(String.format("Perfis ativos: %s", Arrays.toString(activeProfiles)));
        logger.severe(String.format("Perfis padrão: %s", Arrays.toString(defaultProfiles)));
        
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
        
        // PRIORIDADE 2: Baseado nos perfis ativos
        String[] activeProfiles = environment.getActiveProfiles();
        
        // Verifica se há perfil de PRODUÇÃO
        boolean isProduction = Arrays.stream(activeProfiles)
                .anyMatch(profile -> {
                    String lowerProfile = profile.toLowerCase();
                    return lowerProfile.equals("prd") || 
                           lowerProfile.equals("prod") || 
                           lowerProfile.equals("production");
                });
        
        if (isProduction) {
            return "Servidor de Produção - DigitalOcean";
        }
        
        // Verifica se há perfil de DESENVOLVIMENTO
        boolean isDevelopment = Arrays.stream(activeProfiles)
                .anyMatch(profile -> {
                    String lowerProfile = profile.toLowerCase();
                    return lowerProfile.equals("dev") || 
                           lowerProfile.equals("development");
                });
        
        if (isDevelopment) {
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

