package com.AchadosPerdidos.API.Application.Config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import java.time.Duration;
import java.util.List;
import java.util.Objects;

@Configuration
@EnableCaching
public class CacheConfig {

    private static final Logger logger = LoggerFactory.getLogger(CacheConfig.class);
    
    private static final List<String> CACHE_NAMES = List.of(
        "itens",
        "itensAchados",
        "itensPerdidos",
        "itensDoados",
        "itensDevolvidos",
        "usuarios", 
        "campus",
        "localItems",
        "cidades",
        "estados",
        "enderecos",
        "roles",
        "fotos",
        "fotosUsuario",
        "fotosItem",
        "usuarioRoles",
        "usuarioCampus",
        "deviceTokens",
        "jwtTokens",
        "jwtUserIds"
    );

    @Value("${CACHE_ENABLED:true}")
    private boolean cacheEnabled;

    @Value("${CACHE_MAX_SIZE:500}")
    private int maxCacheSize;

    @Value("${CACHE_INITIAL_CAPACITY:128}")
    private int initialCapacity;

    @Value("${CACHE_EXPIRE_MINUTES:15}")
    private int expireMinutes;

    @Value("${CACHE_EXPIRE_AFTER_ACCESS_MINUTES:5}")
    private int expireAfterAccessMinutes;

    @Bean
    @Primary
    public CacheManager cacheManager() {
        if (!cacheEnabled) {
            logger.info("Cache desabilitado, usando ConcurrentMapCacheManager");
            return createFallbackCacheManager();
        }

        logger.info(
                "Configurando CaffeineCacheManager com initialCapacity={}, maxSize={}, expireMinutes={}, expireAfterAccessMinutes={}",
                initialCapacity,
                maxCacheSize,
                expireMinutes,
                expireAfterAccessMinutes
        );

        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(Objects.requireNonNull(createCaffeineBuilder()));
        cacheManager.setCacheNames(CACHE_NAMES);
        cacheManager.setAllowNullValues(false);
        
        return cacheManager;
    }

    @Bean
    public CacheManager fallbackCacheManager() {
        return createFallbackCacheManager();
    }

    @Bean
    @Profile("test")
    public CacheManager testCacheManager() {
        logger.info("Configurando cache para ambiente de teste");
        return new ConcurrentMapCacheManager();
    }

    private Caffeine<Object, Object> createCaffeineBuilder() {
        return Caffeine.newBuilder()
                .initialCapacity(initialCapacity)
                .maximumSize(maxCacheSize)
                .expireAfterWrite(Duration.ofMinutes(expireMinutes))
                .expireAfterAccess(Duration.ofMinutes(expireAfterAccessMinutes))
                .recordStats()
                .removalListener((key, value, cause) ->
                        logger.debug("Cache entry removida: key={}, cause={}", key, cause));
    }

    private ConcurrentMapCacheManager createFallbackCacheManager() {
        ConcurrentMapCacheManager cacheManager = new ConcurrentMapCacheManager();
        cacheManager.setCacheNames(CACHE_NAMES);
        return cacheManager;
    }
}
