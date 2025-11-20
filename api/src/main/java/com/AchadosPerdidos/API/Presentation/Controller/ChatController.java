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
import java.util.Optional;
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
     * REST: Buscar mensagens do chat
     */
    @Operation(summary = "Buscar mensagens do chat", description = "Retorna mensagens de um chat específico")
    @GetMapping("/messages/{chatId}")
    public ResponseEntity<List<ChatMessage>> getChatMessages(
            @Parameter(description = "ID do chat") @PathVariable String chatId) {
        List<ChatMessage> messages = chatService.getMessagesByChatId(chatId);
        return ResponseEntity.ok(messages);
    }

    /**
     * REST: Buscar mensagens entre usuários
     */
    @Operation(summary = "Buscar mensagens entre usuários", description = "Retorna mensagens entre dois usuários")
    @GetMapping("/messages/users/{userId1}/{userId2}")
    public ResponseEntity<List<ChatMessage>> getMessagesBetweenUsers(
            @Parameter(description = "ID do primeiro usuário") @PathVariable String userId1,
            @Parameter(description = "ID do segundo usuário") @PathVariable String userId2) {
        List<ChatMessage> messages = chatService.getMessagesBetweenUsers(userId1, userId2);
        return ResponseEntity.ok(messages);
    }

    /**
     * REST: Marcar mensagens como lidas
     */
    @Operation(summary = "Marcar como lidas", description = "Marca mensagens como lidas")
    @PutMapping("/mark-read")
    public ResponseEntity<String> markAsReadRest(@RequestBody ChatMessage message) {
        List<ChatMessage> unreadMessages = chatService.getUnreadMessages(message.getId_Usuario_Destino());
        
        if (!unreadMessages.isEmpty()) {
            chatService.markMessagesAsRead(
                unreadMessages.stream()
                    .map(ChatMessage::getId)
                    .toList()
            );
            
            // Notifica via WebSocket
            String destination = "/topic/private." + message.getId_Usuario_Remetente();
            ChatMessage notificationMessage = new ChatMessage();
            notificationMessage.setId_Chat("");
            notificationMessage.setId_Usuario_Remetente("");
            notificationMessage.setId_Usuario_Destino("");
            notificationMessage.setMenssagem("Mensagens marcadas como lidas");
            notificationMessage.setData_Hora_Menssagem(LocalDateTime.now());
            notificationMessage.setStatus(Status_Menssagem.LIDA);
            notificationMessage.setTipo(Tipo_Menssagem.SYSTEM);
            messagingTemplate.convertAndSend(destination, notificationMessage);
        }
        
        return ResponseEntity.ok("Mensagens marcadas como lidas");
    }

    /**
     * REST: Buscar mensagem por ID
     */
    @Operation(summary = "Buscar mensagem por ID", description = "Retorna uma mensagem específica")
    @GetMapping("/message/{messageId}")
    public ResponseEntity<ChatMessage> getMessageById(
            @Parameter(description = "ID da mensagem") @PathVariable String messageId) {
        Optional<ChatMessage> message = chatService.getMessageById(messageId);
        return message.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * REST: Contar mensagens do chat
     */
    @Operation(summary = "Contar mensagens", description = "Retorna número de mensagens em um chat")
    @GetMapping("/count/{chatId}")
    public ResponseEntity<Long> getMessageCount(
            @Parameter(description = "ID do chat") @PathVariable String chatId) {
        long count = chatService.getMessageCountByChat(chatId);
        return ResponseEntity.ok(count);
    }

    /**
     * REST: Buscar histórico de chats do usuário
     */
    @Operation(summary = "Buscar histórico de chats", description = "Retorna lista de chats do usuário com última mensagem, contador de não lidas e nome do outro usuário")
    @GetMapping("/chats/{userId}")
    public ResponseEntity<com.AchadosPerdidos.API.Application.DTOs.ChatMessage.ChatSummaryListDTO> getUserChats(
            @Parameter(description = "ID do usuário") @PathVariable String userId) {
        try {
            com.AchadosPerdidos.API.Application.DTOs.ChatMessage.ChatSummaryListDTO chats = chatService.getUserChats(userId);
            return ResponseEntity.ok(chats);
        } catch (Exception e) {
            logger.error("Erro ao buscar histórico de chats para usuário {}: {}", userId, e.getMessage(), e);
            return ResponseEntity.status(org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * REST: Buscar mensagens não lidas do usuário
     */
    @Operation(summary = "Buscar mensagens não lidas", description = "Retorna lista de mensagens não lidas do usuário")
    @GetMapping("/unread/{userId}")
    public ResponseEntity<List<ChatMessage>> getUnreadMessages(
            @Parameter(description = "ID do usuário") @PathVariable String userId) {
        try {
            List<ChatMessage> unreadMessages = chatService.getUnreadMessages(userId);
            logger.info("Encontradas {} mensagens não lidas para o usuário {}", unreadMessages.size(), userId);
            return ResponseEntity.ok(unreadMessages);
        } catch (Exception e) {
            logger.error("Erro ao buscar mensagens não lidas para usuário {}: {}", userId, e.getMessage(), e);
            return ResponseEntity.status(org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
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

            // Busca tokens ativos do usuário destinatário
            DeviceTokenListDTO tokensList = deviceTokenService.getActiveDeviceTokensByUsuarioId(usuarioDestinoId);
            
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
