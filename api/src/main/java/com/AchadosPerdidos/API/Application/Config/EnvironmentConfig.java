package com.AchadosPerdidos.API.Application.Config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;

import java.util.HashMap;
import java.util.Map;

public class EnvironmentConfig implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {

    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
        Environment environment = event.getEnvironment();
        
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
    
    private static final Map<String, String> PREFIX_MAPPINGS = Map.of(
        "JWT", "jwt",
        "GOOGLE", "google.auth",
        "POSTGRES", "spring.datasource",
        "MONGODB", "spring.data.mongodb",
        "AWS", "aws.s3"
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