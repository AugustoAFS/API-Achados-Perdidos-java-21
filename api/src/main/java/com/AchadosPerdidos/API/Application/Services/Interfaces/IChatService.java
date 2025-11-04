package com.AchadosPerdidos.API.Application.Services.Interfaces;

import com.AchadosPerdidos.API.Domain.Entity.Chat.ChatMessage;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface IChatService {

    ChatMessage saveMessage(ChatMessage message);
    Optional<ChatMessage> getMessageById(String messageId);
    void deleteMessage(String messageId);

    List<ChatMessage> getMessagesByChatId(String chatId);
    List<ChatMessage> getMessagesBetweenUsers(String userId1, String userId2);
    List<ChatMessage> getRecentMessages(String chatId, int limit);
    List<ChatMessage> getMessagesByPeriod(String chatId, LocalDateTime startTime, LocalDateTime endTime);
    List<ChatMessage> getUnreadMessages(String receiverId);

    long getMessageCountByChat(String chatId);
    void markMessagesAsDelivered(List<String> messageIds);
    void markMessagesAsRead(List<String> messageIds);
}