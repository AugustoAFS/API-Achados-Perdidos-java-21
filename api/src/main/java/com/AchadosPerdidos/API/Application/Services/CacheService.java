package com.AchadosPerdidos.API.Application.Services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Supplier;

@Service
public class CacheService {

    private static final Logger logger = LoggerFactory.getLogger(CacheService.class);

    @Autowired
    private CacheManager cacheManager;

    public <T> T getOrLoad(String cacheName, Object key, Supplier<T> valueLoader) {
        Cache cache = getCache(cacheName);
        if (cache == null) {
            logger.warn("Cache '{}' não encontrado, executando valueLoader", cacheName);
            return valueLoader.get();
        }

        Cache.ValueWrapper wrapper = cache.get(key);
        if (wrapper != null) {
            @SuppressWarnings("unchecked")
            T cachedValue = (T) wrapper.get();
            logger.debug("Cache HIT: cache='{}', key='{}'", cacheName, key);
            return cachedValue;
        }

        logger.debug("Cache MISS: cache='{}', key='{}' - buscando da fonte", cacheName, key);
        T value = valueLoader.get();

        if (value != null) {
            cache.put(key, value);
            logger.debug("Valor armazenado no cache: cache='{}', key='{}'", cacheName, key);
        }

        return value;
    }

    public <T> Optional<T> get(String cacheName, Object key) {
        Cache cache = getCache(cacheName);
        if (cache == null) {
            return Optional.empty();
        }

        Cache.ValueWrapper wrapper = cache.get(key);
        if (wrapper != null) {
            @SuppressWarnings("unchecked")
            T value = (T) wrapper.get();
            logger.debug("✅ Cache HIT: cache='{}', key='{}'", cacheName, key);
            return Optional.ofNullable(value);
        }

        logger.debug("❌ Cache MISS: cache='{}', key='{}'", cacheName, key);
        return Optional.empty();
    }

    public void put(String cacheName, Object key, Object value) {
        Cache cache = getCache(cacheName);
        if (cache != null && value != null) {
            cache.put(key, value);
            logger.debug("💾 Valor armazenado no cache: cache='{}', key='{}'", cacheName, key);
        }
    }

    public void evict(String cacheName, Object key) {
        Cache cache = getCache(cacheName);
        if (cache != null) {
            cache.evict(key);
            logger.debug("🗑️ Valor removido do cache: cache='{}', key='{}'", cacheName, key);
        }
    }

    public void clear(String cacheName) {
        Cache cache = getCache(cacheName);
        if (cache != null) {
            cache.clear();
            logger.info("🗑️ Cache limpo completamente: cache='{}'", cacheName);
        }
    }

    public void clearAll() {
        cacheManager.getCacheNames().forEach(cacheName -> {
            Cache cache = cacheManager.getCache(cacheName);
            if (cache != null) {
                cache.clear();
            }
        });
        logger.info("🗑️ TODOS os caches foram limpos");
    }

    public boolean exists(String cacheName, Object key) {
        Cache cache = getCache(cacheName);
        return cache != null && cache.get(key) != null;
    }

    private Cache getCache(String cacheName) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache == null) {
            logger.warn("⚠️ Cache '{}' não encontrado no CacheManager", cacheName);
        }
        return cache;
    }

    public String getCacheStats(String cacheName) {
        Cache cache = getCache(cacheName);
        if (cache instanceof org.springframework.cache.caffeine.CaffeineCache) {
            var caffeineCache = ((org.springframework.cache.caffeine.CaffeineCache) cache).getNativeCache();
            var stats = caffeineCache.stats();

            return String.format(
                "Cache '%s': hitRate=%.2f%%, hits=%d, misses=%d, evictions=%d, size=%d",
                cacheName,
                stats.hitRate() * 100,
                stats.hitCount(),
                stats.missCount(),
                stats.evictionCount(),
                caffeineCache.estimatedSize()
            );
        }
        return "Estatísticas não disponíveis para cache: " + cacheName;
    }
}

