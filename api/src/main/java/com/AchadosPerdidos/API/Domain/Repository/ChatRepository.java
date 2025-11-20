package com.AchadosPerdidos.API.Domain.Repository;

import com.AchadosPerdidos.API.Domain.Entity.Chat.ChatMessage;
import com.AchadosPerdidos.API.Domain.Enum.Status_Menssagem;
import com.AchadosPerdidos.API.Domain.Enum.Tipo_Menssagem;
import com.AchadosPerdidos.API.Domain.Repository.Interfaces.IChatRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class ChatRepository implements IChatRepository {
    
    private static final Logger logger = LoggerFactory.getLogger(ChatRepository.class);
    
    @Autowired
    private MongoTemplate mongoTemplate;
    
    private static final String COLLECTION_NAME = "chat_messages";

    // ==================== CRUD BÁSICO ====================

    @Override
    public List<ChatMessage> findAll() {
        return mongoTemplate.findAll(ChatMessage.class, COLLECTION_NAME);
    }

    @Override
    public ChatMessage findById(String id) {
        return mongoTemplate.findById(id, ChatMessage.class, COLLECTION_NAME);
    }

    @Override
    public ChatMessage save(ChatMessage chatMessage) {
        try {
            logger.debug("Tentando salvar mensagem no MongoDB - Collection: {}", COLLECTION_NAME);
            String mensagemPreview = chatMessage.getMenssagem();
            if (mensagemPreview != null && mensagemPreview.length() > 50) {
                mensagemPreview = mensagemPreview.substring(0, 50) + "...";
            }
            logger.debug("Mensagem - ID: {}, ChatId: {}, Remetente: {}, Destino: {}, Mensagem: {}", 
                chatMessage.getId(), chatMessage.getId_Chat(), 
                chatMessage.getId_Usuario_Remetente(), chatMessage.getId_Usuario_Destino(),
                mensagemPreview != null ? mensagemPreview : "null");
            
            if (chatMessage.getId() == null || chatMessage.getId().isEmpty()) {
                // MongoDB gera ID automaticamente se for null
                if (chatMessage.getData_Hora_Menssagem() == null) {
                    chatMessage.setData_Hora_Menssagem(LocalDateTime.now());
                }
                ChatMessage saved = mongoTemplate.insert(chatMessage, COLLECTION_NAME);
                logger.info("Mensagem inserida no MongoDB com sucesso - Novo ID: {}", saved.getId());
                return saved;
            } else {
                ChatMessage saved = mongoTemplate.save(chatMessage, COLLECTION_NAME);
                logger.info("Mensagem atualizada no MongoDB com sucesso - ID: {}", saved.getId());
                return saved;
            }
        } catch (Exception e) {
            logger.error("Erro ao salvar mensagem no MongoDB: {}", e.getMessage(), e);
            throw new RuntimeException("Erro ao salvar mensagem no MongoDB: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean deleteById(String id) {
        Query query = new Query(Criteria.where("_id").is(id));
        return mongoTemplate.remove(query, ChatMessage.class, COLLECTION_NAME).getDeletedCount() > 0;
    }

    // ==================== BUSCAS POR CHAT ====================

    @Override
    public List<ChatMessage> findByChatId(String chatId) {
        logger.debug("Buscando mensagens por chatId: {}", chatId);
        Query query = new Query(Criteria.where("id_Chat").is(chatId));
        List<ChatMessage> messages = mongoTemplate.find(query, ChatMessage.class, COLLECTION_NAME);
        logger.debug("Encontradas {} mensagens para chatId: {}", messages.size(), chatId);
        return messages;
    }

    @Override
    public List<ChatMessage> findByChatIdOrderByDataHora(String chatId) {
        Query query = new Query(Criteria.where("id_Chat").is(chatId))
                .with(Sort.by(Sort.Direction.ASC, "data_Hora_Menssagem"));
        return mongoTemplate.find(query, ChatMessage.class, COLLECTION_NAME);
    }

    // ==================== BUSCAS POR USUÁRIO ====================

    @Override
    public List<ChatMessage> findByRemetenteId(String remetenteId) {
        Query query = new Query(Criteria.where("id_Usuario_Remetente").is(remetenteId));
        return mongoTemplate.find(query, ChatMessage.class, COLLECTION_NAME);
    }

    @Override
    public List<ChatMessage> findByDestinoId(String destinoId) {
        Query query = new Query(Criteria.where("id_Usuario_Destino").is(destinoId));
        return mongoTemplate.find(query, ChatMessage.class, COLLECTION_NAME);
    }

    // ==================== BUSCAS POR CONVERSA ====================

    @Override
    public List<ChatMessage> findConversation(String usuario1Id, String usuario2Id) {
        Criteria criteria = new Criteria().orOperator(
                Criteria.where("id_Usuario_Remetente").is(usuario1Id)
                        .and("id_Usuario_Destino").is(usuario2Id),
                Criteria.where("id_Usuario_Remetente").is(usuario2Id)
                        .and("id_Usuario_Destino").is(usuario1Id)
        );
        Query query = new Query(criteria);
        return mongoTemplate.find(query, ChatMessage.class, COLLECTION_NAME);
    }

    @Override
    public List<ChatMessage> findConversationOrderByDataHora(String usuario1Id, String usuario2Id) {
        Criteria criteria = new Criteria().orOperator(
                Criteria.where("id_Usuario_Remetente").is(usuario1Id)
                        .and("id_Usuario_Destino").is(usuario2Id),
                Criteria.where("id_Usuario_Remetente").is(usuario2Id)
                        .and("id_Usuario_Destino").is(usuario1Id)
        );
        Query query = new Query(criteria)
                .with(Sort.by(Sort.Direction.ASC, "data_Hora_Menssagem"));
        return mongoTemplate.find(query, ChatMessage.class, COLLECTION_NAME);
    }

    // ==================== BUSCAS POR STATUS ====================

    @Override
    public List<ChatMessage> findByStatus(Status_Menssagem status) {
        Query query = new Query(Criteria.where("status").is(status));
        return mongoTemplate.find(query, ChatMessage.class, COLLECTION_NAME);
    }

    @Override
    public List<ChatMessage> findUnreadMessages(String destinoId) {
        Query query = new Query(
                Criteria.where("id_Usuario_Destino").is(destinoId)
                        .and("status").ne(Status_Menssagem.LIDA)
        );
        return mongoTemplate.find(query, ChatMessage.class, COLLECTION_NAME);
    }

    // ==================== OPERAÇÕES DE STATUS ====================

    @Override
    public boolean markAsRead(String messageId) {
        Query query = new Query(Criteria.where("_id").is(messageId));
        Update update = new Update().set("status", Status_Menssagem.LIDA);
        return mongoTemplate.updateFirst(query, update, ChatMessage.class, COLLECTION_NAME)
                .getModifiedCount() > 0;
    }

    @Override
    public boolean markAsDelivered(String messageId) {
        Query query = new Query(Criteria.where("_id").is(messageId));
        Update update = new Update().set("status", Status_Menssagem.RECEBIDA);
        return mongoTemplate.updateFirst(query, update, ChatMessage.class, COLLECTION_NAME)
                .getModifiedCount() > 0;
    }

    @Override
    public int countUnreadMessages(String destinoId) {
        Query query = new Query(
                Criteria.where("id_Usuario_Destino").is(destinoId)
                        .and("status").ne(Status_Menssagem.LIDA)
        );
        return (int) mongoTemplate.count(query, ChatMessage.class, COLLECTION_NAME);
    }

    // ==================== BUSCAS POR DATA ====================

    @Override
    public List<ChatMessage> findMessagesBetweenDates(String chatId, LocalDateTime startDate, LocalDateTime endDate) {
        Query query = new Query(
                Criteria.where("id_Chat").is(chatId)
                        .and("data_Hora_Menssagem").gte(startDate).lte(endDate)
        ).with(Sort.by(Sort.Direction.ASC, "data_Hora_Menssagem"));
        return mongoTemplate.find(query, ChatMessage.class, COLLECTION_NAME);
    }

    @Override
    public ChatMessage findLastMessage(String chatId) {
        Query query = new Query(Criteria.where("id_Chat").is(chatId))
                .with(Sort.by(Sort.Direction.DESC, "data_Hora_Menssagem"))
                .limit(1);
        return mongoTemplate.findOne(query, ChatMessage.class, COLLECTION_NAME);
    }
    
    // ==================== MÉTODOS AUXILIARES ====================
    
    public List<ChatMessage> findAllById(List<String> ids) {
        return findAll().stream()
                .filter(msg -> ids.contains(msg.getId()))
                .toList();
    }
    
    public List<ChatMessage> saveAll(List<ChatMessage> messages) {
        return messages.stream()
                .map(this::save)
                .toList();
    }
    
    // Métodos adicionais para IChatQuery (usados pelo ChatService)
    public List<ChatMessage> findMessagesByChatId(String chatId) {
        return findByChatIdOrderByDataHora(chatId);
    }
    
    public List<ChatMessage> findMessagesBetweenUsers(String userId1, String userId2) {
        return findConversationOrderByDataHora(userId1, userId2);
    }
    
    public List<ChatMessage> findMessagesByPeriod(String chatId, LocalDateTime startTime, LocalDateTime endTime) {
        return findMessagesBetweenDates(chatId, startTime, endTime);
    }
    
    public List<ChatMessage> findRecentMessages(String chatId, int limit) {
        Query query = new Query(Criteria.where("id_Chat").is(chatId))
                .with(Sort.by(Sort.Direction.DESC, "data_Hora_Menssagem"))
                .limit(limit);
        return mongoTemplate.find(query, ChatMessage.class, COLLECTION_NAME);
    }
    
    public long countMessagesByChat(String chatId) {
        Query query = new Query(Criteria.where("id_Chat").is(chatId));
        return mongoTemplate.count(query, ChatMessage.class, COLLECTION_NAME);
    }
    
    public List<ChatMessage> findMessagesByType(String chatId, Tipo_Menssagem type) {
        Query query = new Query(
                Criteria.where("id_Chat").is(chatId)
                        .and("tipo").is(type)
        ).with(Sort.by(Sort.Direction.ASC, "data_Hora_Menssagem"));
        return mongoTemplate.find(query, ChatMessage.class, COLLECTION_NAME);
    }
    
    public List<ChatMessage> findMessagesByStatus(String receiverId, Status_Menssagem status) {
        Query query = new Query(
                Criteria.where("id_Usuario_Destino").is(receiverId)
                        .and("status").is(status)
        );
        return mongoTemplate.find(query, ChatMessage.class, COLLECTION_NAME);
    }
}

