package com.AchadosPerdidos.API.Presentation.Controller;

import com.AchadosPerdidos.API.Application.Config.OneSignalConfig;
import com.AchadosPerdidos.API.Application.DTOs.DeviceToken.DeviceTokenDTO;
import com.AchadosPerdidos.API.Application.DTOs.DeviceToken.DeviceTokenListDTO;
import com.AchadosPerdidos.API.Application.Services.ChatService;
import com.AchadosPerdidos.API.Application.Services.Interfaces.IDeviceTokenService;
import com.AchadosPerdidos.API.Domain.Entity.Chat.ChatMessage;
import com.AchadosPerdidos.API.Domain.Enum.Tipo_Menssagem;
import com.AchadosPerdidos.API.Domain.Enum.Status_Menssagem;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/chat")
@Tag(name = "Chat Híbrido", description = "Endpoints REST que também enviam via WebSocket para mensagens privadas")
public class ChatController {

    private static final Logger logger = LoggerFactory.getLogger(ChatController.class);

    @Autowired
    private ChatService chatService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private IDeviceTokenService deviceTokenService;

    @Autowired
    private OneSignalConfig oneSignalConfig;


    @Operation(summary = "Enviar mensagem privada", description = "Envia mensagem privada via REST e WebSocket")
    @PostMapping("/send")
    public ResponseEntity<ChatMessage> sendMessageRest(@RequestBody ChatMessage message) {
        // Valida se é mensagem privada (deve ter destinatário)
        if (message.getId_Usuario_Destino() == null || message.getId_Usuario_Destino().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        
        // Define valores padrão
        if (message.getData_Hora_Menssagem() == null) {
            message.setData_Hora_Menssagem(LocalDateTime.now());
        }
        if (message.getStatus() == null) {
            message.setStatus(Status_Menssagem.ENVIADA);
        }
        if (message.getTipo() == null) {
            message.setTipo(Tipo_Menssagem.CHAT);
        }
        
        // Salva no MongoDB
        ChatMessage savedMessage = chatService.saveMessage(message);
        
        // Envia via WebSocket para tempo real
        String destination = "/topic/private." + savedMessage.getId_Usuario_Destino();
        messagingTemplate.convertAndSend(destination, savedMessage);
        
        // Envia push notification para o destinatário
        sendPushNotificationIfNeeded(savedMessage);
        
        return ResponseEntity.ok(savedMessage);
    }

    /**
     * REST: Notificar que usuário está online via HTTP
     */
    @Operation(summary = "Usuário online", description = "Notifica que usuário está online")
    @PostMapping("/online")
    public ResponseEntity<ChatMessage> userOnlineRest(@RequestBody ChatMessage message) {
        // Valida se tem destinatário
        if (message.getId_Usuario_Destino() == null || message.getId_Usuario_Destino().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        
        message.setTipo(Tipo_Menssagem.SYSTEM);
        message.setMenssagem("Usuário está online");
        message.setData_Hora_Menssagem(LocalDateTime.now());
        message.setStatus(Status_Menssagem.ENVIADA);
        
        ChatMessage savedMessage = chatService.saveMessage(message);
        
        // Envia via WebSocket
        String destination = "/topic/private." + savedMessage.getId_Usuario_Destino();
        messagingTemplate.convertAndSend(destination, savedMessage);
        
        return ResponseEntity.ok(savedMessage);
    }

    /**
     * REST: Notificar que usuário está offline via HTTP
     */
    @Operation(summary = "Usuário offline", description = "Notifica que usuário está offline")
    @PostMapping("/offline")
    public ResponseEntity<ChatMessage> userOfflineRest(@RequestBody ChatMessage message) {
        // Valida se tem destinatário
        if (message.getId_Usuario_Destino() == null || message.getId_Usuario_Destino().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        
        message.setTipo(Tipo_Menssagem.SYSTEM);
        message.setMenssagem("Usuário está offline");
        message.setData_Hora_Menssagem(LocalDateTime.now());
        message.setStatus(Status_Menssagem.ENVIADA);
        
        ChatMessage savedMessage = chatService.saveMessage(message);
        
        // Envia via WebSocket
        String destination = "/topic/private." + savedMessage.getId_Usuario_Destino();
        messagingTemplate.convertAndSend(destination, savedMessage);
        
        return ResponseEntity.ok(savedMessage);
    }

    /**
     * REST: Enviar mensagem privada via HTTP
     */
    @Operation(summary = "Enviar mensagem privada", description = "Envia mensagem privada via REST e WebSocket")
    @PostMapping("/private")
    public ResponseEntity<ChatMessage> sendPrivateMessageRest(@RequestBody ChatMessage message) {
        message.setData_Hora_Menssagem(LocalDateTime.now());
        message.setStatus(Status_Menssagem.ENVIADA);
        message.setTipo(Tipo_Menssagem.CHAT);
        
        ChatMessage savedMessage = chatService.saveMessage(message);
        
        // Envia via WebSocket para o destinatário
        String destination = "/topic/private." + savedMessage.getId_Usuario_Destino();
        messagingTemplate.convertAndSend(destination, savedMessage);
        
        // Envia push notification para o destinatário
        sendPushNotificationIfNeeded(savedMessage);
        
        return ResponseEntity.ok(savedMessage);
    }

    /**
     * REST: Indicar que está digitando (mensagem privada)
     */
    @Operation(summary = "Indicar digitação", description = "Indica que usuário está digitando")
    @PostMapping("/typing")
    public ResponseEntity<ChatMessage> typingRest(@RequestBody ChatMessage message) {
        // Valida se tem destinatário
        if (message.getId_Usuario_Destino() == null || message.getId_Usuario_Destino().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        
        message.setMenssagem("está digitando...");
        message.setTipo(Tipo_Menssagem.TYPING);
        message.setData_Hora_Menssagem(LocalDateTime.now());
        message.setStatus(Status_Menssagem.ENVIADA);
        
        // Envia via WebSocket
        String destination = "/topic/private." + message.getId_Usuario_Destino();
        messagingTemplate.convertAndSend(destination, message);
        
        return ResponseEntity.ok(message);
    }

    /**
     * REST: Parar indicação de digitação (mensagem privada)
     */
    @Operation(summary = "Parar digitação", description = "Para indicação de digitação")
    @PostMapping("/stop-typing")
    public ResponseEntity<ChatMessage> stopTypingRest(@RequestBody ChatMessage message) {
        // Valida se tem destinatário
        if (message.getId_Usuario_Destino() == null || message.getId_Usuario_Destino().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        
        message.setMenssagem("parou de digitar");
        message.setTipo(Tipo_Menssagem.STOP_TYPING);
        message.setData_Hora_Menssagem(LocalDateTime.now());
        message.setStatus(Status_Menssagem.ENVIADA);
        
        // Envia via WebSocket
        String destination = "/topic/private." + message.getId_Usuario_Destino();
        messagingTemplate.convertAndSend(destination, message);
        
        return ResponseEntity.ok(message);
    }

    /**
     * REST: Buscar mensagens privadas entre dois usuários
     */
    @Operation(summary = "Buscar mensagens privadas", description = "Retorna mensagens privadas entre dois usuários")
    @GetMapping("/messages/users/{userId1}/{userId2}")
    public ResponseEntity<List<ChatMessage>> getMessagesPrivate(
            @Parameter(description = "ID do primeiro usuário") @PathVariable String userId1,
            @Parameter(description = "ID do segundo usuário") @PathVariable String userId2) {
        List<ChatMessage> messages = chatService.getMessagesPrivate(userId1, userId2);
        return ResponseEntity.ok(messages);
    }

    /**
     * REST: Marcar mensagens como lidas
     */
    @Operation(summary = "Marcar como lidas", description = "Marca mensagens como lidas")
    @PutMapping("/mark-read")
    public ResponseEntity<String> markAsReadRest(@RequestBody List<String> messageIds) {
        if (messageIds != null && !messageIds.isEmpty()) {
            chatService.markMessagesAsRead(messageIds);
        }
        return ResponseEntity.ok("Mensagens marcadas como lidas");
    }

    /**
     * REST: Marcar mensagens como não lidas
     */
    @Operation(summary = "Marcar como não lidas", description = "Marca mensagens como não lidas")
    @PutMapping("/mark-unread")
    public ResponseEntity<String> markAsUnReadRest(@RequestBody List<String> messageIds) {
        if (messageIds != null && !messageIds.isEmpty()) {
            chatService.markMessagesAsUnRead(messageIds);
        }
        return ResponseEntity.ok("Mensagens marcadas como não lidas");
    }

    /**
     * REST: Listar todas as mensagens
     */
    @Operation(summary = "Listar todas as mensagens", description = "Retorna todas as mensagens do sistema")
    @GetMapping("/all")
    public ResponseEntity<List<ChatMessage>> getAllMessages() {
        List<ChatMessage> messages = chatService.getAllMessages();
        return ResponseEntity.ok(messages);
    }

    /**
     * Envia push notification para o destinatário da mensagem, se necessário
     * Só envia push para mensagens do tipo CHAT (não envia para SYSTEM, TYPING, etc.)
     */
    private void sendPushNotificationIfNeeded(ChatMessage message) {
        // Só envia push para mensagens do tipo CHAT
        if (message == null || message.getTipo() != Tipo_Menssagem.CHAT) {
            return;
        }

        // Verifica se tem destinatário
        if (message.getId_Usuario_Destino() == null || message.getId_Usuario_Destino().isEmpty()) {
            logger.debug("Mensagem sem destinatário, push não enviado");
            return;
        }

        try {
            // Converte ID do usuário destinatário de String para Integer
            Integer usuarioDestinoId;
            try {
                usuarioDestinoId = Integer.parseInt(message.getId_Usuario_Destino());
            } catch (NumberFormatException e) {
                logger.warn("ID do usuário destinatário inválido: {}", message.getId_Usuario_Destino());
                return;
            }

            // Busca tokens do usuário destinatário
            DeviceTokenListDTO tokensList = deviceTokenService.getDeviceTokensByUsuario(usuarioDestinoId);
            
            if (tokensList == null || tokensList.getDeviceTokens() == null || tokensList.getDeviceTokens().isEmpty()) {
                logger.debug("Usuário {} não possui tokens de dispositivo ativos", usuarioDestinoId);
                return;
            }

            // Extrai os tokens da lista
            List<String> deviceTokens = tokensList.getDeviceTokens().stream()
                    .map(DeviceTokenDTO::getToken)
                    .filter(token -> token != null && !token.trim().isEmpty())
                    .collect(Collectors.toList());

            if (deviceTokens.isEmpty()) {
                logger.debug("Nenhum token válido encontrado para o usuário {}", usuarioDestinoId);
                return;
            }

            // Prepara dados customizados para a notificação
            Map<String, String> data = new HashMap<>();
            data.put("type", "chat_message");
            data.put("messageId", message.getId() != null ? message.getId() : "");
            data.put("chatId", message.getId_Chat() != null ? message.getId_Chat() : "");
            data.put("senderId", message.getId_Usuario_Remetente() != null ? message.getId_Usuario_Remetente() : "");
            data.put("receiverId", message.getId_Usuario_Destino());

            // Título e corpo da notificação
            String title = "Nova mensagem";
            String body = message.getMenssagem() != null ? message.getMenssagem() : "Você recebeu uma nova mensagem";
            
            // Limita o tamanho do corpo da mensagem (OneSignal tem limite)
            if (body.length() > 200) {
                body = body.substring(0, 197) + "...";
            }

            // Envia push notification para todos os dispositivos do usuário
            logger.info("🔔 Tentando enviar push notification - Usuário: {}, Tokens: {}, Título: '{}', Corpo: '{}'", 
                usuarioDestinoId, deviceTokens.size(), title, body.length() > 50 ? body.substring(0, 50) + "..." : body);
            
            int sentCount = oneSignalConfig.sendPushNotificationToMultiple(deviceTokens, title, body, data);
            
            if (sentCount > 0) {
                logger.info("✅ Push notification enviada com SUCESSO para {} dispositivo(s) do usuário {} - Tokens: {}", 
                    sentCount, usuarioDestinoId, deviceTokens);
            } else {
                logger.warn("❌ FALHA ao enviar push notification para o usuário {} - Verifique se OneSignal está configurado corretamente", 
                    usuarioDestinoId);
            }

        } catch (Exception e) {
            logger.error("Erro ao enviar push notification para mensagem: {}", e.getMessage(), e);
            // Não lança exceção para não quebrar o fluxo de envio de mensagem
        }
    }
}
