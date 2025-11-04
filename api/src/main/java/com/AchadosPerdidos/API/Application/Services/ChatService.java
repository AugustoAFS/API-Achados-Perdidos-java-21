package com.AchadosPerdidos.API.Application.Services;

import com.AchadosPerdidos.API.Domain.Entity.Chat.ChatMessage;
import com.AchadosPerdidos.API.Domain.Repository.ChatMessageRepository;
import com.AchadosPerdidos.API.Infrastruture.MongoDB.Interfaces.IChatQuery;
import com.AchadosPerdidos.API.Application.Services.Interfaces.IChatService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ChatService implements IChatService {

    @Autowired
    private ChatMessageRepository chatMessageRepository;
    
    @Autowired
    private IChatQuery chatQuery;

    @Override
    public ChatMessage saveMessage(ChatMessage message) {
        return chatMessageRepository.save(message);
    }

    @Override
    public List<ChatMessage> getMessagesByChatId(String chatId) {
        return chatQuery.findMessagesByChatId(chatId);
    }

    @Override
    public List<ChatMessage> getMessagesBetweenUsers(String userId1, String userId2) {
        return chatQuery.findMessagesBetweenUsers(userId1, userId2);
    }

    @Override
    public List<ChatMessage> getRecentMessages(String chatId, int limit) {
        return chatQuery.findRecentMessages(chatId, limit);
    }

    @Override
    public List<ChatMessage> getMessagesByPeriod(String chatId, LocalDateTime startTime, LocalDateTime endTime) {
        return chatQuery.findMessagesByPeriod(chatId, startTime, endTime);
    }

    @Override
    public List<ChatMessage> getUnreadMessages(String receiverId) {
        return chatQuery.findUnreadMessages(receiverId);
    }

    @Override
    public void markMessagesAsDelivered(List<String> messageIds) {
        List<ChatMessage> messages = chatMessageRepository.findAllById(messageIds);
        messages.forEach(message -> message.setStatus(com.AchadosPerdidos.API.Domain.Enum.Status_Menssagem.DELIVERED));
        chatMessageRepository.saveAll(messages);
    }

    @Override
    public void markMessagesAsRead(List<String> messageIds) {
        List<ChatMessage> messages = chatMessageRepository.findAllById(messageIds);
        messages.forEach(message -> message.setStatus(com.AchadosPerdidos.API.Domain.Enum.Status_Menssagem.READ));
        chatMessageRepository.saveAll(messages);
    }

    @Override
    public long getMessageCountByChat(String chatId) {
        return chatQuery.countMessagesByChat(chatId);
    }

    @Override
    public Optional<ChatMessage> getMessageById(String messageId) {
        return chatMessageRepository.findById(messageId);
    }

    @Override
    public void deleteMessage(String messageId) {
        chatMessageRepository.deleteById(messageId);
    }
}
