package com.AchadosPerdidos.API.Infrastruture.MongoDB;

import com.AchadosPerdidos.API.Domain.Entity.Chat.ChatMessage;
import com.AchadosPerdidos.API.Domain.Repository.ChatMessageRepository;
import com.AchadosPerdidos.API.Infrastruture.MongoDB.Interfaces.IChatQuery;
import com.AchadosPerdidos.API.Domain.Enum.Tipo_Menssagem;
import com.AchadosPerdidos.API.Domain.Enum.Status_Menssagem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class ChatQuery implements IChatQuery {
    
    @Autowired
    private ChatMessageRepository chatMessageRepository;
    
    @Override
    public List<ChatMessage> findMessagesByChatId(String chatId) {
        return chatMessageRepository.findByChatIdOrderByTimestampAsc(chatId);
    }
    
    @Override
    public List<ChatMessage> findMessagesBetweenUsers(String userId1, String userId2) {
        return chatMessageRepository.findMessagesBetweenUsers(userId1, userId2);
    }
    
    @Override
    public List<ChatMessage> findMessagesByPeriod(String chatId, LocalDateTime startTime, LocalDateTime endTime) {
        return chatMessageRepository.findByChatIdAndTimestampBetween(chatId, startTime, endTime);
    }
    
    @Override
    public List<ChatMessage> findUnreadMessages(String receiverId) {
        return chatMessageRepository.findByReceiverIdAndStatus(receiverId, Status_Menssagem.SENT);
    }
    
    @Override
    public List<ChatMessage> findRecentMessages(String chatId, int limit) {
        return chatMessageRepository.findTopNByChatId(chatId, limit);
    }
    
    @Override
    public long countMessagesByChat(String chatId) {
        return chatMessageRepository.countByChatId(chatId);
    }
    
    @Override
    public List<ChatMessage> findMessagesByType(String chatId, Tipo_Menssagem type) {
        return chatMessageRepository.findByChatIdAndTipoOrderByTimestampAsc(chatId, type);
    }
    
    @Override
    public List<ChatMessage> findMessagesByStatus(String receiverId, Status_Menssagem status) {
        return chatMessageRepository.findByReceiverIdAndStatus(receiverId, status);
    }
}
