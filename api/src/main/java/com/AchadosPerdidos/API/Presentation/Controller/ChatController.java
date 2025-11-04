package com.AchadosPerdidos.API.Presentation.Controller;

import com.AchadosPerdidos.API.Application.Services.ChatService;
import com.AchadosPerdidos.API.Domain.Entity.Chat.ChatMessage;
import com.AchadosPerdidos.API.Domain.Enum.Tipo_Menssagem;
import com.AchadosPerdidos.API.Domain.Enum.Status_Menssagem;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/chat")
@Tag(name = "Chat Híbrido", description = "Endpoints REST que também enviam via WebSocket para mensagens privadas")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;


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
            message.setStatus(Status_Menssagem.SENT);
        }
        if (message.getTipo() == null) {
            message.setTipo(Tipo_Menssagem.CHAT);
        }
        
        // Salva no MongoDB
        ChatMessage savedMessage = chatService.saveMessage(message);
        
        // Envia via WebSocket para tempo real
        String destination = "/topic/private." + savedMessage.getId_Usuario_Destino();
        messagingTemplate.convertAndSend(destination, savedMessage);
        
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
        message.setStatus(Status_Menssagem.SENT);
        
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
        message.setStatus(Status_Menssagem.SENT);
        
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
        message.setStatus(Status_Menssagem.SENT);
        message.setTipo(Tipo_Menssagem.CHAT);
        
        ChatMessage savedMessage = chatService.saveMessage(message);
        
        // Envia via WebSocket para o destinatário
        String destination = "/topic/private." + savedMessage.getId_Usuario_Destino();
        messagingTemplate.convertAndSend(destination, savedMessage);
        
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
        message.setStatus(Status_Menssagem.SENT);
        
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
        message.setStatus(Status_Menssagem.SENT);
        
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
            notificationMessage.setStatus(Status_Menssagem.READ);
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
}
