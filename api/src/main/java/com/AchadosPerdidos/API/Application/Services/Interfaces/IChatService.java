package com.AchadosPerdidos.API.Application.Services.Interfaces;

import com.AchadosPerdidos.API.Domain.Entity.Chat.ChatMessage;
import java.util.List;

public interface IChatService {
    List<ChatMessage> getAllMessages();
    ChatMessage saveMessage(ChatMessage message);
    List<ChatMessage> getMessagesPrivate(String userId1, String userId2);
    void markMessagesAsRead(List<String> messageIds);
    void markMessagesAsUnRead(List<String> messageIds);
}