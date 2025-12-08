package com.AchadosPerdidos.API.Presentation.Controller;

import com.AchadosPerdidos.API.Application.Services.Interfaces.IChatService;
import com.AchadosPerdidos.API.Domain.Entity.Chat.ChatMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller para gerenciamento de chat
 * Responsabilidade: Camada de apresentação - recebe requisições HTTP e delega para os services
 */
@RestController
@RequestMapping("/api/chat")
@Tag(name = "Chat", description = "Endpoints REST e WebSocket para mensagens privadas")
public class ChatController {

    private static final Logger logger = LoggerFactory.getLogger(ChatController.class);

    @Autowired
    private IChatService chatService;

    @Operation(summary = "Enviar mensagem privada", description = "Envia mensagem privada via REST e WebSocket")
    @PostMapping("/send")
    public ResponseEntity<ChatMessage> sendMessage(@RequestBody ChatMessage message) {
        return ResponseEntity.ok(chatService.sendPrivateMessage(message));
    }

    @Operation(summary = "Usuário online", description = "Notifica que usuário está online")
    @PostMapping("/online")
    public ResponseEntity<ChatMessage> userOnline(@RequestBody ChatMessage message) {
        return ResponseEntity.ok(chatService.userOnline(message));
    }

    @Operation(summary = "Usuário offline", description = "Notifica que usuário está offline")
    @PostMapping("/offline")
    public ResponseEntity<ChatMessage> userOffline(@RequestBody ChatMessage message) {
        return ResponseEntity.ok(chatService.userOffline(message));
    }

    @Operation(summary = "Enviar mensagem privada", description = "Envia mensagem privada via REST e WebSocket")
    @PostMapping("/private")
    public ResponseEntity<ChatMessage> sendPrivateMessage(@RequestBody ChatMessage message) {
        return ResponseEntity.ok(chatService.sendPrivateMessage(message));
    }

    @Operation(summary = "Indicar digitação", description = "Indica que usuário está digitando")
    @PostMapping("/typing")
    public ResponseEntity<ChatMessage> typing(@RequestBody ChatMessage message) {
        return ResponseEntity.ok(chatService.userTyping(message));
    }

    @Operation(summary = "Parar digitação", description = "Para indicação de digitação")
    @PostMapping("/stop-typing")
    public ResponseEntity<ChatMessage> stopTyping(@RequestBody ChatMessage message) {
        return ResponseEntity.ok(chatService.userStopTyping(message));
    }

    @Operation(summary = "Buscar mensagens privadas", description = "Retorna mensagens privadas entre dois usuários")
    @GetMapping("/messages/users/{userId1}/{userId2}")
    public ResponseEntity<List<ChatMessage>> getMessagesPrivate(
            @Parameter(description = "ID do primeiro usuário") @PathVariable String userId1,
            @Parameter(description = "ID do segundo usuário") @PathVariable String userId2) {
        return ResponseEntity.ok(chatService.getMessagesPrivate(userId1, userId2));
    }

    @Operation(summary = "Marcar como lidas", description = "Marca mensagens como lidas")
    @PutMapping("/mark-read")
    public ResponseEntity<String> markAsRead(@RequestBody List<String> messageIds) {
        chatService.markMessagesAsRead(messageIds);
        return ResponseEntity.ok("Mensagens marcadas como lidas");
    }

    @Operation(summary = "Marcar como não lidas", description = "Marca mensagens como não lidas")
    @PutMapping("/mark-unread")
    public ResponseEntity<String> markAsUnRead(@RequestBody List<String> messageIds) {
        chatService.markMessagesAsUnRead(messageIds);
        return ResponseEntity.ok("Mensagens marcadas como não lidas");
    }

    @Operation(summary = "Listar todas as mensagens", description = "Retorna todas as mensagens do sistema")
    @GetMapping("/all")
    public ResponseEntity<List<ChatMessage>> getAllMessages() {
        return ResponseEntity.ok(chatService.getAllMessages());
    }
}
