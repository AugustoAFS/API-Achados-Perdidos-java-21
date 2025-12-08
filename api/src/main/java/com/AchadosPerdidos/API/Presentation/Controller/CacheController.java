package com.AchadosPerdidos.API.Presentation.Controller;

import com.AchadosPerdidos.API.Application.Config.CacheConfig;
import com.AchadosPerdidos.API.Application.Services.CacheService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/cache")
@Tag(name = "Cache", description = "API para gerenciamento e monitoramento de cache")
public class CacheController {

    @Autowired
    private CacheService cacheService;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private CacheConfig cacheConfig;

    @GetMapping("/stats")
    @Operation(summary = "Obter estatísticas de todos os caches")
    public ResponseEntity<Map<String, String>> getCacheStats() {
        Map<String, String> stats = new HashMap<>();

        cacheManager.getCacheNames().forEach(cacheName -> {
            stats.put(cacheName, cacheService.getCacheStats(cacheName));
        });

        return ResponseEntity.ok(stats);
    }

    @GetMapping("/stats/{cacheName}")
    @Operation(summary = "Obter estatísticas de um cache específico")
    public ResponseEntity<String> getCacheStatsByCacheName(
            @Parameter(description = "Nome do cache") @PathVariable String cacheName) {
        return ResponseEntity.ok(cacheService.getCacheStats(cacheName));
    }

    @GetMapping("/names")
    @Operation(summary = "Listar todos os nomes de caches configurados")
    public ResponseEntity<Map<String, Object>> getCacheNames() {
        var cacheNames = cacheManager.getCacheNames();
        Map<String, Object> response = new HashMap<>();
        response.put("total", cacheNames.size());
        response.put("caches", cacheNames);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/clear/{cacheName}")
    @Operation(summary = "Limpar um cache específico")
    public ResponseEntity<String> clearCache(
            @Parameter(description = "Nome do cache") @PathVariable String cacheName) {
        cacheService.clear(cacheName);
        return ResponseEntity.ok("Cache '" + cacheName + "' limpo com sucesso");
    }

    @PostMapping("/clear-all")
    @Operation(summary = "Limpar todos os caches")
    public ResponseEntity<String> clearAllCaches() {
        cacheService.clearAll();
        return ResponseEntity.ok("Todos os caches foram limpos com sucesso");
    }

    @DeleteMapping("/evict/{cacheName}/{key}")
    @Operation(summary = "Remover uma entrada específica do cache")
    public ResponseEntity<String> evictCacheEntry(
            @Parameter(description = "Nome do cache") @PathVariable String cacheName,
            @Parameter(description = "Chave do cache") @PathVariable String key) {
        cacheService.evict(cacheName, key);
        return ResponseEntity.ok("Entrada removida do cache '" + cacheName + "' com chave '" + key + "'");
    }

    @GetMapping("/exists/{cacheName}/{key}")
    @Operation(summary = "Verificar se uma chave existe no cache")
    public ResponseEntity<Map<String, Boolean>> checkCacheEntry(
            @Parameter(description = "Nome do cache") @PathVariable String cacheName,
            @Parameter(description = "Chave do cache") @PathVariable String key) {
        boolean exists = cacheService.exists(cacheName, key);
        return ResponseEntity.ok(Map.of("exists", exists));
    }

    @PostMapping("/log-stats")
    @Operation(summary = "Logar estatísticas detalhadas no console")
    public ResponseEntity<String> logCacheStats() {
        cacheConfig.logCacheStats();
        return ResponseEntity.ok("Estatísticas logadas no console");
    }
}
