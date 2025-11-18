package com.AchadosPerdidos.API.Application.Config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Component
public class EnvironmentConfig implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {
    
    private Environment environment;

    @Override
    public void onApplicationEvent(@NonNull ApplicationEnvironmentPreparedEvent event) {
        this.environment = event.getEnvironment();
        
        String[] activeProfiles = environment.getActiveProfiles();
        String activeProfile = activeProfiles.length > 0 ? activeProfiles[0] : 
                              environment.getProperty("spring.profiles.active", "dev");
        
        logEnvironmentInfo(activeProfile);
        
        Dotenv dotenv = Dotenv.configure()
                .directory("./")
                .ignoreIfMalformed()
                .ignoreIfMissing()
                .load();

        Map<String, Object> envProperties = new HashMap<>();
        
        System.getenv().forEach((key, value) -> {
            if (value != null && !value.trim().isEmpty()) {
                envProperties.put(key, value.trim());
                System.setProperty(key, value.trim());
            }
        });
        
        if (dotenv != null) {
            for (var entry : dotenv.entries()) {
                String key = entry.getKey();
                String value = entry.getValue();
                if (value != null) {
                    value = value.trim();
                }
                if (!envProperties.containsKey(key) && value != null && !value.isEmpty()) {
                    envProperties.put(key, value);
                    System.setProperty(key, value);
                }
            }
        }
        
        mapEnvToSpringProperties(envProperties);
        
        MutablePropertySources propertySources = ((org.springframework.core.env.ConfigurableEnvironment) environment).getPropertySources();
        propertySources.addFirst(new MapPropertySource("dotenv", envProperties));
    }
    
    /**
     * Loga informações sobre o ambiente detectado
     */
    private void logEnvironmentInfo(String activeProfile) {
        String propertiesFile = "application-" + activeProfile + ".properties";
        
        // Detecta a fonte do perfil
        String source;
        if (System.getenv("SPRING_PROFILES_ACTIVE") != null) {
            source = "variável de ambiente SPRING_PROFILES_ACTIVE";
        } else if (System.getProperty("spring.profiles.active") != null) {
            source = "system property spring.profiles.active";
        } else {
            source = "application.properties (padrão: dev)";
        }
        
        // Verifica se o arquivo existe (apenas para informação)
        String filePath = "src/main/resources/" + propertiesFile;
        boolean fileExists = new File(filePath).exists() || 
                            Paths.get("target/classes/" + propertiesFile).toFile().exists() ||
                            getClass().getClassLoader().getResource(propertiesFile) != null;
        
        System.out.println("═══════════════════════════════════════════════════════════");
        System.out.println("🌍 AMBIENTE DETECTADO:");
        System.out.println("   Perfil: " + activeProfile);
        System.out.println("   Arquivo: " + propertiesFile + (fileExists ? " ✓" : " (não encontrado)"));
        System.out.println("   Fonte: " + source);
        System.out.println("═══════════════════════════════════════════════════════════");
    }
    
    private static final Map<String, String> PREFIX_MAPPINGS = Map.of(
        "JWT", "jwt",
        "GOOGLE", "google.auth",
        "POSTGRES", "spring.datasource",
        "MONGODB", "spring.data.mongodb",
        "AWS", "aws.s3",
        "ONESIGNAL", "onesignal"
    );
    
    private void mapEnvToSpringProperties(Map<String, Object> envProperties) {
        Map<String, String> propertiesToAdd = new HashMap<>();
        
        for (String envKey : envProperties.keySet()) {
            Object valueObj = envProperties.get(envKey);
            if (!(valueObj instanceof String value) || value.isEmpty()) {
                continue;
            }
            
            String springProperty = convertToSpringProperty(envKey);
            if (springProperty != null) {
                propertiesToAdd.put(springProperty, value.trim());
            }
        }
        
        propertiesToAdd.forEach((key, value) -> {
            envProperties.put(key, value);
            System.setProperty(key, value);
        });
    }
    
    private String convertToSpringProperty(String envKey) {
        // Mapeamento específico para variáveis OneSignal
        if (envKey.equals("ONESIGNAL_APP_ID")) {
            return "onesignal.app.id";
        } else if (envKey.equals("ONESIGNAL_REST_API_KEY")) {
            return "onesignal.rest.api.key";
        } else if (envKey.equals("ONESIGNAL_API_URL")) {
            return "onesignal.api.url";
        } else if (envKey.equals("ONESIGNAL_ENABLED")) {
            return "onesignal.enabled";
        }
        
        // Mapeamento genérico para outras variáveis
        for (Map.Entry<String, String> prefix : PREFIX_MAPPINGS.entrySet()) {
            if (envKey.startsWith(prefix.getKey() + "_")) {
                String suffix = envKey.substring(prefix.getKey().length() + 1);
                String kebabCase = toKebabCase(suffix);
                return prefix.getValue() + "." + kebabCase;
            }
        }
        return null;
    }
    
    private String toKebabCase(String upperSnake) {
        return upperSnake.toLowerCase().replace("_", "-");
    }
    
    // ============== MÉTODOS AUXILIARES DE DETECÇÃO DE AMBIENTE ==============
    
    /**
     * Verifica se o ambiente atual é de PRODUÇÃO
     * @return true se o perfil for prd
     */
    public boolean isProduction() {
        if (environment == null) {
            return false;
        }
        String[] activeProfiles = environment.getActiveProfiles();
        return Arrays.stream(activeProfiles)
                .anyMatch(profile -> profile.equalsIgnoreCase("prd"));
    }

    /**
     * Verifica se o ambiente atual é de DESENVOLVIMENTO
     * @return true se o perfil for dev
     */
    public boolean isDevelopment() {
        if (environment == null) {
            return false;
        }
        String[] activeProfiles = environment.getActiveProfiles();
        return Arrays.stream(activeProfiles)
                .anyMatch(profile -> profile.equalsIgnoreCase("dev"));
    }

    /**
     * Obtém o perfil ativo principal
     * @return Nome do perfil ativo ou "dev" como padrão
     */
    public String getActiveProfile() {
        if (environment == null) {
            return "dev";
        }
        String[] activeProfiles = environment.getActiveProfiles();
        if (activeProfiles.length > 0) {
            return activeProfiles[0];
        }
        return environment.getProperty("spring.profiles.active", "dev");
    }

    /**
     * Obtém todos os perfis ativos
     * @return Array com todos os perfis ativos
     */
    public String[] getActiveProfiles() {
        if (environment == null) {
            return new String[]{"dev"};
        }
        return environment.getActiveProfiles();
    }

    /**
     * Obtém o nome do arquivo de properties baseado no perfil ativo
     * @return Nome do arquivo (ex: application-prd.properties)
     */
    public String getPropertiesFileName() {
        String profile = getActiveProfile();
        return "application-" + profile + ".properties";
    }

    /**
     * Obtém o Environment do Spring (se necessário acessar diretamente)
     * @return Environment do Spring
     */
    public Environment getEnvironment() {
        return environment;
    }
}