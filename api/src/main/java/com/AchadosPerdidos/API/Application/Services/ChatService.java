package com.AchadosPerdidos.API.Application.Services;

import com.AchadosPerdidos.API.Domain.Entity.Chat.ChatMessage;
import com.AchadosPerdidos.API.Application.Services.Interfaces.IChatService;
import com.AchadosPerdidos.API.Domain.Repository.ChatRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ChatService implements IChatService {

    @Autowired
    private ChatRepository chatMessageRepository;

    @Override
    public ChatMessage saveMessage(ChatMessage message) {
        return chatMessageRepository.save(message);
    }

    @Override
    public List<ChatMessage> getMessagesByChatId(String chatId) {
        return chatMessageRepository.findMessagesByChatId(chatId);
    }

    @Override
    public List<ChatMessage> getMessagesBetweenUsers(String userId1, String userId2) {
        return chatMessageRepository.findMessagesBetweenUsers(userId1, userId2);
    }

    @Override
    public List<ChatMessage> getRecentMessages(String chatId, int limit) {
        return chatMessageRepository.findRecentMessages(chatId, limit);
    }

    @Override
    public List<ChatMessage> getMessagesByPeriod(String chatId, LocalDateTime startTime, LocalDateTime endTime) {
        return chatMessageRepository.findMessagesByPeriod(chatId, startTime, endTime);
    }

    @Override
    public List<ChatMessage> getUnreadMessages(String receiverId) {
        return chatMessageRepository.findUnreadMessages(receiverId);
    }

    @Override
    public void markMessagesAsDelivered(List<String> messageIds) {
        for (String messageId : messageIds) {
            chatMessageRepository.markAsDelivered(messageId);
        }
    }

    @Override
    public void markMessagesAsRead(List<String> messageIds) {
        for (String messageId : messageIds) {
            chatMessageRepository.markAsRead(messageId);
        }
    }

    @Override
    public long getMessageCountByChat(String chatId) {
        return chatMessageRepository.countMessagesByChat(chatId);
    }

    @Override
    public Optional<ChatMessage> getMessageById(String messageId) {
        ChatMessage message = chatMessageRepository.findById(messageId);
        return message != null ? Optional.of(message) : Optional.empty();
    }

    @Override
    public void deleteMessage(String messageId) {
        chatMessageRepository.deleteById(messageId);
    }
}
