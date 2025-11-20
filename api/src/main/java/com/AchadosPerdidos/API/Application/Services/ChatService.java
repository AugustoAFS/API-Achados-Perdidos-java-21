package com.AchadosPerdidos.API.Application.Services;

import com.AchadosPerdidos.API.Application.DTOs.ChatMessage.ChatSummaryDTO;
import com.AchadosPerdidos.API.Application.DTOs.ChatMessage.ChatSummaryListDTO;
import com.AchadosPerdidos.API.Application.Services.Interfaces.IChatService;
import com.AchadosPerdidos.API.Application.Services.Interfaces.IUsuariosService;
import com.AchadosPerdidos.API.Domain.Entity.Chat.ChatMessage;
import com.AchadosPerdidos.API.Domain.Repository.ChatRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ChatService implements IChatService {

    private static final Logger logger = LoggerFactory.getLogger(ChatService.class);

    @Autowired
    private ChatRepository chatMessageRepository;

    @Autowired(required = false)
    private IUsuariosService usuariosService;

    @Override
    public ChatMessage saveMessage(ChatMessage message) {
        if (message == null) {
            logger.error("Tentativa de salvar mensagem nula");
            throw new IllegalArgumentException("Mensagem não pode ser nula");
        }

        // Gera id_Chat se não existir (baseado nos IDs dos usuários)
        if (message.getId_Chat() == null || message.getId_Chat().isEmpty()) {
            String idChat = generateChatId(message.getId_Usuario_Remetente(), message.getId_Usuario_Destino());
            message.setId_Chat(idChat);
            logger.debug("id_Chat gerado automaticamente: {}", idChat);
        }

        // Garante que a data está definida
        if (message.getData_Hora_Menssagem() == null) {
            message.setData_Hora_Menssagem(LocalDateTime.now());
        }

        logger.info("Salvando mensagem no MongoDB - Remetente: {}, Destino: {}, ChatId: {}", 
            message.getId_Usuario_Remetente(), message.getId_Usuario_Destino(), message.getId_Chat());

        ChatMessage saved = chatMessageRepository.save(message);
        
        logger.info("Mensagem salva com sucesso - ID: {}", saved.getId());
        
        return saved;
    }

    /**
     * Gera um ID único para o chat baseado nos IDs dos usuários
     * O ID é sempre o mesmo independente da ordem dos usuários
     */
    private String generateChatId(String userId1, String userId2) {
        if (userId1 == null || userId2 == null) {
            throw new IllegalArgumentException("IDs dos usuários não podem ser nulos");
        }
        
        // Ordena os IDs para garantir que o mesmo chat tenha o mesmo ID
        String sorted = userId1.compareTo(userId2) < 0 
            ? userId1 + "_" + userId2 
            : userId2 + "_" + userId1;
        
        return "chat_" + sorted;
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

    /**
     * Busca o histórico de chats de um usuário
     * Retorna lista de chats com última mensagem, contador de não lidas e nome do outro usuário
     */
    public ChatSummaryListDTO getUserChats(String userId) {
        logger.info("Buscando histórico de chats para usuário: {}", userId);
        
        // Busca todas as últimas mensagens de cada chat do usuário
        List<ChatMessage> lastMessages = chatMessageRepository.findUserChats(userId);
        
        List<ChatSummaryDTO> chatSummaries = new ArrayList<>();
        
        for (ChatMessage lastMessage : lastMessages) {
            String chatId = lastMessage.getId_Chat();
            
            // Identifica o outro usuário do chat
            String outroUsuarioId = userId.equals(lastMessage.getId_Usuario_Remetente())
                ? lastMessage.getId_Usuario_Destino()
                : lastMessage.getId_Usuario_Remetente();
            
            // Busca nome do outro usuário
            String otherUserName = null;
            if (usuariosService != null && outroUsuarioId != null && !outroUsuarioId.isEmpty()) {
                try {
                    Integer outroUsuarioIdInt = Integer.parseInt(outroUsuarioId);
                    var usuarioListDTO = usuariosService.getUsuarioById(outroUsuarioIdInt);
                    if (usuarioListDTO != null && usuarioListDTO.getUsuarios() != null && !usuarioListDTO.getUsuarios().isEmpty()) {
                        var outroUsuario = usuarioListDTO.getUsuarios().get(0);
                        if (outroUsuario != null) {
                            otherUserName = outroUsuario.getNomeCompleto();
                        }
                    }
                } catch (NumberFormatException e) {
                    logger.debug("ID do usuário inválido: {}", outroUsuarioId);
                } catch (Exception e) {
                    logger.debug("Não foi possível buscar nome do usuário {}: {}", outroUsuarioId, e.getMessage());
                }
            }
            
            // Conta mensagens não lidas neste chat
            int naoLidas = chatMessageRepository.countUnreadMessagesByChat(chatId, userId);
            
            ChatSummaryDTO summary = new ChatSummaryDTO();
            summary.setChatId(chatId);
            summary.setOtherUserId(outroUsuarioId);
            summary.setOtherUserName(otherUserName);
            summary.setLastMessage(lastMessage.getMenssagem());
            summary.setLastMessageDate(lastMessage.getData_Hora_Menssagem());
            summary.setUnreadCount(naoLidas);
            summary.setIdUltimaMensagem(lastMessage.getId());
            
            chatSummaries.add(summary);
        }
        
        logger.info("Encontrados {} chats para o usuário {}", chatSummaries.size(), userId);
        
        return new ChatSummaryListDTO(chatSummaries, chatSummaries.size());
    }
}
