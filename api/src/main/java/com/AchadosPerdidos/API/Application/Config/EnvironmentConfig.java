package com.AchadosPerdidos.API.Application.Config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.function.Function;

public class EnvironmentConfig implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {

    private static final Logger _log = LoggerFactory.getLogger(EnvironmentConfig.class);

    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
        Environment environment = event.getEnvironment();
        String[] activeProfiles = environment.getActiveProfiles();
        boolean isProduction = activeProfiles.length > 0 && "prd".equals(activeProfiles[0]);
        
        Dotenv dotenv = null;
        try {
            dotenv = Dotenv.configure()
                    .directory("./")
                    .ignoreIfMalformed()
                    .ignoreIfMissing()
                    .load();
            
            if (isProduction) {
                _log.info("Modo PRODUÇÃO detectado - Variáveis de ambiente do sistema têm prioridade sobre .env");
            } else {
                _log.info("Modo DESENVOLVIMENTO detectado - Carregando variáveis de ambiente do arquivo .env");
            }
        } catch (RuntimeException e) {
            if (isProduction) {
                _log.info("Modo PRODUÇÃO detectado - Arquivo .env não encontrado, usando variáveis de ambiente do sistema");
            } else {
                _log.warn("Arquivo .env não encontrado, tentando usar variáveis de ambiente do sistema: {}", e.getMessage());
            }
        }

        Map<String, Object> envProperties = new HashMap<>();
        
        System.getenv().forEach((key, value) -> {
            if (value != null) {
                String trimmedValue = value.trim();
                if (!trimmedValue.isEmpty()) {
                    envProperties.put(key, trimmedValue);
                    System.setProperty(key, trimmedValue);
                }
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
    
    private static final Map<String, String> PREFIX_MAPPINGS = Map.of(
        "JWT", "jwt",
        "GOOGLE", "google.auth",
        "POSTGRES", "spring.datasource",
        "MONGODB", "spring.data.mongodb",
        "AWS", "aws.s3"
    );
    
    private static final Map<String, Function<String, String>> VALUE_TRANSFORMERS = Map.of(
        "POSTGRES_URL", value -> value.startsWith("jdbc:") ? value : "jdbc:" + value
    );
    
    private void mapEnvToSpringProperties(Map<String, Object> envProperties) {
        Map<String, String> propertiesToAdd = new HashMap<>();
        
        for (String envKey : new ArrayList<>(envProperties.keySet())) {
            Object valueObj = envProperties.get(envKey);
            if (!(valueObj instanceof String value) || value.isEmpty()) {
                continue;
            }
            
            String trimmedValue = value.trim();
            
            String springProperty = convertToSpringProperty(envKey);
            if (springProperty != null) {
                String finalValue = VALUE_TRANSFORMERS.containsKey(envKey)
                    ? VALUE_TRANSFORMERS.get(envKey).apply(trimmedValue)
                    : trimmedValue;
                
                if ("POSTGRES_URL".equals(envKey) && finalValue.startsWith("jdbc:") && !trimmedValue.startsWith("jdbc:")) {
                    _log.info("Prefix 'jdbc:' adicionado automaticamente");
                }
                
                propertiesToAdd.put(springProperty, finalValue);
            }
        }
        
        propertiesToAdd.forEach((key, value) -> {
            envProperties.put(key, value);
            System.setProperty(key, value);
        });
    }
    
    private String convertToSpringProperty(String envKey) {
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
}