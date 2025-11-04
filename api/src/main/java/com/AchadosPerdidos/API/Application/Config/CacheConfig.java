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

@Configuration
@EnableCaching
public class CacheConfig {

    private static final Logger logger = LoggerFactory.getLogger(CacheConfig.class);
    
    // Nomes dos caches utilizados na aplicação
    private static final List<String> CACHE_NAMES = List.of(
        "itens",
        "usuarios", 
        "campus",
        "statusItems",
        "localItems",
        "cidades",
        "estados",
        "enderecos",
        "roles"
    );

    @Value("${cache.enabled:true}")
    private boolean cacheEnabled;

    @Value("${cache.max-size:500}")
    private int maxCacheSize;

    @Value("${cache.expire-minutes:15}")
    private int expireMinutes;

    @Value("${cache.expire-after-access-minutes:5}")
    private int expireAfterAccessMinutes;

    @Bean
    @Primary
    public CacheManager cacheManager() {
        if (!cacheEnabled) {
            logger.info("Cache desabilitado, usando ConcurrentMapCacheManager");
            return createFallbackCacheManager();
        }

        logger.info("Configurando CaffeineCacheManager com maxSize={}, expireMinutes={}", 
                   maxCacheSize, expireMinutes);

        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(createCaffeineBuilder());
        cacheManager.setCacheNames(CACHE_NAMES);
        
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
                .maximumSize(maxCacheSize)
                .expireAfterWrite(Duration.ofMinutes(expireMinutes))
                .expireAfterAccess(Duration.ofMinutes(expireAfterAccessMinutes))
                .recordStats()
                .removalListener((key, value, cause) -> {
                    logger.debug("Cache entry removida: key={}, cause={}", key, cause);
                });
    }

    private ConcurrentMapCacheManager createFallbackCacheManager() {
        return new ConcurrentMapCacheManager(CACHE_NAMES.toArray(String[]::new));
    }
}
