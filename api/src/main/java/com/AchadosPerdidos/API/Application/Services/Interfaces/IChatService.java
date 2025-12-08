package com.AchadosPerdidos.API.Application.Services.Interfaces;

import com.AchadosPerdidos.API.Domain.Entity.Chat.ChatMessage;
import java.util.List;

public interface IChatService {
    // Métodos existentes
    List<ChatMessage> getAllMessages();
    ChatMessage saveMessage(ChatMessage message);
    List<ChatMessage> getMessagesPrivate(String userId1, String userId2);
    void markMessagesAsRead(List<String> messageIds);
    void markMessagesAsUnRead(List<String> messageIds);

    // Novos métodos para lógica de negócio do chat
    ChatMessage sendPrivateMessage(ChatMessage message);
    ChatMessage userOnline(ChatMessage message);
    ChatMessage userOffline(ChatMessage message);
    ChatMessage userTyping(ChatMessage message);
    ChatMessage userStopTyping(ChatMessage message);
}

