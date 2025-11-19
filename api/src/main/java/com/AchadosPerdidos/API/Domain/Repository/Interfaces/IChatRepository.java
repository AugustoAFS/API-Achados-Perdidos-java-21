package com.AchadosPerdidos.API.Domain.Repository.Interfaces;

import com.AchadosPerdidos.API.Domain.Entity.Chat.ChatMessage;
import com.AchadosPerdidos.API.Domain.Enum.Status_Menssagem;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Interface do Repository para ChatMessage.
 */
public interface IChatRepository {
    // Operações CRUD básicas (MongoDB)
    List<ChatMessage> findAll();
    ChatMessage findById(String id);
    ChatMessage save(ChatMessage chatMessage);
    boolean deleteById(String id);

    // Buscas por Chat
    List<ChatMessage> findByChatId(String chatId);
    List<ChatMessage> findByChatIdOrderByDataHora(String chatId);

    // Buscas por Usuário
    List<ChatMessage> findByRemetenteId(String remetenteId);
    List<ChatMessage> findByDestinoId(String destinoId);

    // Buscas por Conversa
    List<ChatMessage> findConversation(String usuario1Id, String usuario2Id);
    List<ChatMessage> findConversationOrderByDataHora(String usuario1Id, String usuario2Id);
    
    // Buscas por Status
    List<ChatMessage> findByStatus(Status_Menssagem status);
    List<ChatMessage> findUnreadMessages(String destinoId);

    // Operações de Status
    boolean markAsRead(String messageId);
    boolean markAsDelivered(String messageId);
    int countUnreadMessages(String destinoId);

    // Operações de Data
    List<ChatMessage> findMessagesBetweenDates(String chatId, LocalDateTime startDate, LocalDateTime endDate);
    ChatMessage findLastMessage(String chatId);
}
