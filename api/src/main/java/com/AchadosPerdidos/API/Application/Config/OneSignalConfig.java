package com.AchadosPerdidos.API.Application.Config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Configuração do OneSignal para notificações push
 * Plano gratuito: 10.000 notificações por mês
 * Suporta Android e iOS
 */
@Configuration
public class OneSignalConfig {

    private static final Logger logger = LoggerFactory.getLogger(OneSignalConfig.class);
    
    @Value("${onesignal.app.id:}")
    private String oneSignalAppId;
    
    @Value("${onesignal.rest.api.key:}")
    private String oneSignalRestApiKey;
    
    @Value("${onesignal.enabled:false}")
    private boolean oneSignalEnabled;
    
    @Value("${onesignal.api.url:https://api.onesignal.com/notifications}")
    private String oneSignalApiUrl;
    
    private final RestTemplate restTemplate;

    public OneSignalConfig() {
        this.restTemplate = new RestTemplate();
    }

    /**
     * Verifica se o OneSignal está habilitado e configurado
     */
    public boolean isEnabled() {
        return oneSignalEnabled && 
               oneSignalAppId != null && !oneSignalAppId.trim().isEmpty() && 
               oneSignalRestApiKey != null && !oneSignalRestApiKey.trim().isEmpty();
    }

    /**
     * Envia notificação push para um dispositivo específico
     * @param deviceToken Token do dispositivo
     * @param title Título da notificação
     * @param body Corpo da mensagem
     * @param data Dados adicionais (opcional)
     * @return true se enviado com sucesso, false caso contrário
     */
    public boolean sendPushNotification(String deviceToken, String title, String body, Map<String, String> data) {
        if (!isEnabled()) {
            logger.debug("OneSignal desabilitado ou não configurado. Notificação não enviada.");
            return false;
        }
        
        if (deviceToken == null || deviceToken.trim().isEmpty()) {
            logger.warn("Token do dispositivo é nulo ou vazio");
            return false;
        }
        
        try {
            Map<String, Object> payload = new HashMap<>();
            payload.put("app_id", oneSignalAppId);
            payload.put("include_player_ids", List.of(deviceToken));
            
            Map<String, String> contents = new HashMap<>();
            contents.put("en", body);
            payload.put("contents", contents);
            
            Map<String, String> headings = new HashMap<>();
            headings.put("en", title);
            payload.put("headings", headings);
            
            // Adiciona dados customizados se fornecidos
            if (data != null && !data.isEmpty()) {
                payload.put("data", data);
            }
            
            HttpHeaders headers = createHeaders();
            HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);
            ResponseEntity<String> response = restTemplate.exchange(
                oneSignalApiUrl,
                HttpMethod.POST,
                request,
                String.class
            );
            
            logger.info("Notificação push enviada com sucesso. Response: {}", response.getBody());
            return response.getStatusCode().is2xxSuccessful();
            
        } catch (Exception e) {
            logger.error("Erro ao enviar notificação push: {}", e.getMessage(), e);
            return false;
        }
    }

    /**
     * Envia notificação push para múltiplos dispositivos
     * @param deviceTokens Lista de tokens dos dispositivos
     * @param title Título da notificação
     * @param body Corpo da mensagem
     * @param data Dados adicionais (opcional)
     * @return Número de notificações enviadas com sucesso
     */
    public int sendPushNotificationToMultiple(List<String> deviceTokens, String title, String body, Map<String, String> data) {
        if (!isEnabled()) {
            logger.debug("OneSignal desabilitado ou não configurado. Notificações não enviadas.");
            return 0;
        }
        
        if (deviceTokens == null || deviceTokens.isEmpty()) {
            logger.warn("Lista de tokens de dispositivos está vazia");
            return 0;
        }
        
        try {
            Map<String, Object> payload = new HashMap<>();
            payload.put("app_id", oneSignalAppId);
            payload.put("include_player_ids", deviceTokens);
            
            Map<String, String> contents = new HashMap<>();
            contents.put("en", body);
            payload.put("contents", contents);
            
            Map<String, String> headings = new HashMap<>();
            headings.put("en", title);
            payload.put("headings", headings);
            
            // Adiciona dados customizados se fornecidos
            if (data != null && !data.isEmpty()) {
                payload.put("data", data);
            }
            
            HttpHeaders headers = createHeaders();
            HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);
            ResponseEntity<String> response = restTemplate.exchange(
                oneSignalApiUrl,
                HttpMethod.POST,
                request,
                String.class
            );
            
            logger.info("Notificações push enviadas para {} dispositivos. Response: {}", deviceTokens.size(), response.getBody());
            return response.getStatusCode().is2xxSuccessful() ? deviceTokens.size() : 0;
            
        } catch (Exception e) {
            logger.error("Erro ao enviar notificações push em lote: {}", e.getMessage(), e);
            return 0;
        }
    }

    /**
     * Valida se um token de dispositivo é válido
     * @param deviceToken Token do dispositivo
     * @return true se válido, false caso contrário
     */
    public boolean validateDeviceToken(String deviceToken) {
        if (deviceToken == null || deviceToken.trim().isEmpty()) {
            return false;
        }
        
        // Validação básica: tokens OneSignal geralmente têm formato UUID ou similar
        // Esta é uma validação básica, a validação real acontece ao tentar enviar
        return deviceToken.length() > 20 && deviceToken.length() < 200;
    }

    /**
     * Cria os headers HTTP para autenticação no OneSignal
     */
    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        // OneSignal requer Basic Auth com a API Key em base64
        String auth = Base64.getEncoder().encodeToString((oneSignalRestApiKey + ":").getBytes(StandardCharsets.UTF_8));
        headers.set("Authorization", "Basic " + auth);
        return headers;
    }
}

