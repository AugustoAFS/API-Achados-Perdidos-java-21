package com.AchadosPerdidos.API.Application.Config;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.RemovalCause;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleCacheErrorHandler;
import org.springframework.cache.interceptor.SimpleCacheResolver;
import org.springframework.cache.interceptor.SimpleKeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.time.Duration;
import java.util.Arrays;

@Configuration
@EnableCaching
public class CacheConfig implements CachingConfigurer {

    private static final Logger logger = LoggerFactory.getLogger(CacheConfig.class);

    private static final String[] CACHE_NAMES = {
        "itens",
        "itensAchados",
        "itensPerdidos",
        "itensDoados",
        "itensDevolvidos",
        "usuarios",
        "campus",
        "instituicoes",
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
    };

    @Value("${cache.enabled:true}")
    private boolean cacheEnabled;

    @Value("${cache.max-size:1000}")
    private int maxCacheSize;

    @Value("${cache.initial-capacity:256}")
    private int initialCapacity;

    @Value("${cache.expire-minutes:30}")
    private int expireMinutes;

    @Value("${cache.expire-after-access-minutes:10}")
    private int expireAfterAccessMinutes;

    @Bean
    @Primary
    @Override
    public CacheManager cacheManager() {
        if (!cacheEnabled) {
            logger.warn("Cache está DESABILITADO - Performance pode ser afetada!");
            return createNoCacheManager();
        }
        logger.info("Configurando Caffeine Cache Manager (Cache-Aside Pattern)");
        logger.info("Configurações: maxSize={}, initialCapacity={}, expireAfterWrite={}min, expireAfterAccess={}min",
                maxCacheSize, initialCapacity, expireMinutes, expireAfterAccessMinutes);
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(caffeineCacheBuilder());
        cacheManager.setCacheNames(Arrays.asList(CACHE_NAMES));
        cacheManager.setAllowNullValues(false);
        logger.info("Cache configurado com {} caches: {}", CACHE_NAMES.length, Arrays.toString(CACHE_NAMES));
        return cacheManager;
    }

    @Bean
    public Caffeine<Object, Object> caffeineCacheBuilder() {
        return Caffeine.newBuilder()
                .initialCapacity(initialCapacity)
                .maximumSize(maxCacheSize)
                .expireAfterWrite(Duration.ofMinutes(expireMinutes))
                .expireAfterAccess(Duration.ofMinutes(expireAfterAccessMinutes))
                .recordStats()
                .removalListener((Object key, Object value, RemovalCause cause) -> {
                    if (cause.wasEvicted()) {
                        logger.debug("🗑️ Cache evicted: key={}, cause={}", key, cause);
                    }
                })
                .scheduler(com.github.benmanes.caffeine.cache.Scheduler.systemScheduler());
    }

    @Bean
    @Override
    public KeyGenerator keyGenerator() {
        return new SimpleKeyGenerator();
    }

    @Bean
    @Override
    public CacheResolver cacheResolver() {
        return new SimpleCacheResolver(cacheManager());
    }

    @Bean
    @Override
    public CacheErrorHandler errorHandler() {
        return new SimpleCacheErrorHandler() {
            @Override
            public void handleCacheGetError(RuntimeException exception, org.springframework.cache.Cache cache, Object key) {
                logger.error("Erro ao buscar do cache '{}' com key '{}': {}",
                    cache.getName(), key, exception.getMessage());
            }

            @Override
            public void handleCachePutError(RuntimeException exception, org.springframework.cache.Cache cache, Object key, Object value) {
                logger.error("Erro ao gravar no cache '{}' com key '{}': {}",
                    cache.getName(), key, exception.getMessage());
            }

            @Override
            public void handleCacheEvictError(RuntimeException exception, org.springframework.cache.Cache cache, Object key) {
                logger.error("Erro ao remover do cache '{}' com key '{}': {}",
                    cache.getName(), key, exception.getMessage());
            }

            @Override
            public void handleCacheClearError(RuntimeException exception, org.springframework.cache.Cache cache) {
                logger.error("Erro ao limpar cache '{}': {}",
                    cache.getName(), exception.getMessage());
            }
        };
    }

    private CacheManager createNoCacheManager() {
        return new org.springframework.cache.support.NoOpCacheManager();
    }

    public void logCacheStats() {
        CacheManager manager = cacheManager();
        if (manager instanceof CaffeineCacheManager) {
            logger.info("Cache Statistics:");
            Arrays.stream(CACHE_NAMES).forEach(cacheName -> {
                var cache = manager.getCache(cacheName);
                if (cache instanceof org.springframework.cache.caffeine.CaffeineCache) {
                    var caffeineCache = ((org.springframework.cache.caffeine.CaffeineCache) cache).getNativeCache();
                    var stats = caffeineCache.stats();

                    logger.info("Cache '{}': hitRate={}, hitCount={}, missCount={}, evictionCount={}, size={}",
                        cacheName,
                        String.format("%.2f%%", stats.hitRate() * 100),
                        stats.hitCount(),
                        stats.missCount(),
                        stats.evictionCount(),
                        caffeineCache.estimatedSize()
                    );
                }
            });
        }
    }
}
