package com.AchadosPerdidos.API.Domain.Repository;

import com.AchadosPerdidos.API.Domain.Entity.Chat.ChatMessage;
import com.AchadosPerdidos.API.Domain.Enum.Tipo_Menssagem;
import com.AchadosPerdidos.API.Domain.Enum.Status_Menssagem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {

    /**
     * Busca mensagens por chatId ordenadas por timestamp
     */
    @Query("{'Id_Chat': ?0}")
    List<ChatMessage> findByChatIdOrderByTimestampAsc(String chatId);

    /**
     * Busca mensagens por chatId e tipo ordenadas por timestamp
     */
    @Query("{'Id_Chat': ?0, 'Tipo': ?1}")
    List<ChatMessage> findByChatIdAndTipoOrderByTimestampAsc(String chatId, Tipo_Menssagem type);

    /**
     * Busca mensagens por senderId ordenadas por timestamp
     */
    @Query("{'Id_Usuario_Remetente': ?0}")
    List<ChatMessage> findBySenderIdOrderByTimestampDesc(String senderId);

    /**
     * Busca mensagens por receiverId ordenadas por timestamp
     */
    @Query("{'Id_Usuario_Destino': ?0}")
    List<ChatMessage> findByReceiverIdOrderByTimestampDesc(String receiverId);

    /**
     * Busca mensagens entre dois usuários ordenadas por timestamp
     */
    @Query("{'$or': [{'Id_Usuario_Remetente': ?0, 'Id_Usuario_Destino': ?1}, {'Id_Usuario_Remetente': ?1, 'Id_Usuario_Destino': ?0}]}")
    List<ChatMessage> findMessagesBetweenUsers(String userId1, String userId2);

    /**
     * Busca mensagens por chatId em um período específico
     */
    @Query("{'Id_Chat': ?0, 'Data_Hora_Menssagem': {'$gte': ?1, '$lte': ?2}}")
    List<ChatMessage> findByChatIdAndTimestampBetween(String chatId, LocalDateTime startTime, LocalDateTime endTime);

    /**
     * Conta mensagens por chatId
     */
    @Query(value = "{'Id_Chat': ?0}", count = true)
    long countByChatId(String chatId);

    /**
     * Busca mensagens não lidas por receiverId
     */
    @Query("{'Id_Usuario_Destino': ?0, 'Status': ?1}")
    List<ChatMessage> findByReceiverIdAndStatus(String receiverId, Status_Menssagem status);

    /**
     * Busca as últimas N mensagens de um chat
     */
    @Query(value = "{'Id_Chat': ?0}", sort = "{'Data_Hora_Menssagem': -1}")
    List<ChatMessage> findTopNByChatId(String chatId, int limit);
}
