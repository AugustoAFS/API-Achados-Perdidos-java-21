package com.AchadosPerdidos.API.Application.Services;

import com.AchadosPerdidos.API.Application.Config.OneSignalConfig;
import com.AchadosPerdidos.API.Application.DTOs.ChatMessage.ChatSummaryDTO;
import com.AchadosPerdidos.API.Application.DTOs.ChatMessage.ChatSummaryListDTO;
import com.AchadosPerdidos.API.Application.DTOs.DeviceToken.DeviceTokenDTO;
import com.AchadosPerdidos.API.Application.DTOs.DeviceToken.DeviceTokenListDTO;
import com.AchadosPerdidos.API.Application.Services.Interfaces.IChatService;
import com.AchadosPerdidos.API.Application.Services.Interfaces.IDeviceTokenService;
import com.AchadosPerdidos.API.Application.Services.Interfaces.IUsuariosService;
import com.AchadosPerdidos.API.Domain.Entity.Chat.ChatMessage;
import com.AchadosPerdidos.API.Domain.Enum.Status_Menssagem;
import com.AchadosPerdidos.API.Domain.Enum.Tipo_Menssagem;
import com.AchadosPerdidos.API.Domain.Repository.ChatRepository;
import com.AchadosPerdidos.API.Exeptions.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ChatService implements IChatService {

    private static final Logger logger = LoggerFactory.getLogger(ChatService.class);

    @Autowired
    private ChatRepository chatMessageRepository;

    @Autowired(required = false)
    private IUsuariosService usuariosService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired(required = false)
    private IDeviceTokenService deviceTokenService;

    @Autowired(required = false)
    private OneSignalConfig oneSignalConfig;

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
    public List<ChatMessage> getAllMessages() {
        return chatMessageRepository.findAll();
    }

    @Override
    public List<ChatMessage> getMessagesPrivate(String userId1, String userId2) {
        return chatMessageRepository.findMessagesBetweenUsers(userId1, userId2);
    }

    @Override
    public void markMessagesAsRead(List<String> messageIds) {
        for (String messageId : messageIds) {
            chatMessageRepository.markAsRead(messageId);
        }
    }

    @Override
    public void markMessagesAsUnRead(List<String> messageIds) {
        // Implementação para marcar mensagens como não lidas
        // Pode usar markAsDelivered ou criar um método específico no repository
        for (String messageId : messageIds) {
            // Por enquanto, usando markAsDelivered como alternativa
            // Se necessário, adicionar método markAsUnRead no repository
            chatMessageRepository.markAsDelivered(messageId);
        }
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

    /**
     * Envia mensagem privada via REST e WebSocket
     * - Valida mensagem
     * - Define valores padrão
     * - Salva no MongoDB
     * - Envia via WebSocket
     * - Envia push notification se necessário
     */
    @Override
    public ChatMessage sendPrivateMessage(ChatMessage message) {
        logger.info("Enviando mensagem privada - Remetente: {}, Destino: {}",
            message.getId_Usuario_Remetente(), message.getId_Usuario_Destino());

        // Valida destinatário
        validarDestinatario(message);

        // Define valores padrão
        if (message.getData_Hora_Menssagem() == null) {
            message.setData_Hora_Menssagem(LocalDateTime.now());
        }
        if (message.getStatus() == null) {
            message.setStatus(Status_Menssagem.ENVIADA);
        }
        if (message.getTipo() == null) {
            message.setTipo(Tipo_Menssagem.CHAT);
        }

        // Salva no MongoDB
        ChatMessage savedMessage = saveMessage(message);

        // Envia via WebSocket
        enviarViaWebSocket(savedMessage);

        // Envia push notification
        enviarPushNotificationSeNecessario(savedMessage);

        logger.info("Mensagem privada enviada com sucesso - ID: {}", savedMessage.getId());
        return savedMessage;
    }

    /**
     * Notifica que usuário está online
     */
    @Override
    public ChatMessage userOnline(ChatMessage message) {
        logger.debug("Usuário online - Remetente: {}, Destino: {}",
            message.getId_Usuario_Remetente(), message.getId_Usuario_Destino());

        validarDestinatario(message);

        message.setTipo(Tipo_Menssagem.SYSTEM);
        message.setMenssagem("Usuário está online");
        message.setData_Hora_Menssagem(LocalDateTime.now());
        message.setStatus(Status_Menssagem.ENVIADA);

        ChatMessage savedMessage = saveMessage(message);
        enviarViaWebSocket(savedMessage);

        return savedMessage;
    }

    /**
     * Notifica que usuário está offline
     */
    @Override
    public ChatMessage userOffline(ChatMessage message) {
        logger.debug("Usuário offline - Remetente: {}, Destino: {}",
            message.getId_Usuario_Remetente(), message.getId_Usuario_Destino());

        validarDestinatario(message);

        message.setTipo(Tipo_Menssagem.SYSTEM);
        message.setMenssagem("Usuário está offline");
        message.setData_Hora_Menssagem(LocalDateTime.now());
        message.setStatus(Status_Menssagem.ENVIADA);

        ChatMessage savedMessage = saveMessage(message);
        enviarViaWebSocket(savedMessage);

        return savedMessage;
    }

    /**
     * Notifica que usuário está digitando
     */
    @Override
    public ChatMessage userTyping(ChatMessage message) {
        logger.debug("Usuário digitando - Remetente: {}, Destino: {}",
            message.getId_Usuario_Remetente(), message.getId_Usuario_Destino());

        validarDestinatario(message);

        message.setMenssagem("está digitando...");
        message.setTipo(Tipo_Menssagem.TYPING);
        message.setData_Hora_Menssagem(LocalDateTime.now());
        message.setStatus(Status_Menssagem.ENVIADA);

        // Não salva no banco - apenas envia via WebSocket
        enviarViaWebSocket(message);

        return message;
    }

    /**
     * Notifica que usuário parou de digitar
     */
    @Override
    public ChatMessage userStopTyping(ChatMessage message) {
        logger.debug("Usuário parou de digitar - Remetente: {}, Destino: {}",
            message.getId_Usuario_Remetente(), message.getId_Usuario_Destino());

        validarDestinatario(message);

        message.setMenssagem("parou de digitar");
        message.setTipo(Tipo_Menssagem.STOP_TYPING);
        message.setData_Hora_Menssagem(LocalDateTime.now());
        message.setStatus(Status_Menssagem.ENVIADA);

        // Não salva no banco - apenas envia via WebSocket
        enviarViaWebSocket(message);

        return message;
    }

    // ========== MÉTODOS PRIVADOS ==========

    /**
     * Valida se a mensagem tem destinatário
     */
    private void validarDestinatario(ChatMessage message) {
        if (message.getId_Usuario_Destino() == null || message.getId_Usuario_Destino().isEmpty()) {
            logger.error("Tentativa de enviar mensagem sem destinatário");
            throw new BusinessException("Mensagem", "enviar", "Destinatário é obrigatório");
        }
    }

    /**
     * Envia mensagem via WebSocket
     */
    private void enviarViaWebSocket(ChatMessage message) {
        try {
            String destination = "/topic/private." + message.getId_Usuario_Destino();
            messagingTemplate.convertAndSend(destination, message);
            logger.debug("Mensagem enviada via WebSocket para: {}", destination);
        } catch (Exception e) {
            logger.error("Erro ao enviar mensagem via WebSocket", e);
            // Não lança exceção - não deve quebrar o envio da mensagem
        }
    }

    /**
     * Envia push notification se necessário
     * Só envia para mensagens do tipo CHAT
     */
    private void enviarPushNotificationSeNecessario(ChatMessage message) {
        // Só envia push para mensagens do tipo CHAT
        if (message == null || message.getTipo() != Tipo_Menssagem.CHAT) {
            return;
        }

        if (message.getId_Usuario_Destino() == null || message.getId_Usuario_Destino().isEmpty()) {
            logger.debug("Mensagem sem destinatário, push não enviado");
            return;
        }

        try {
            Integer usuarioDestinoId = Integer.parseInt(message.getId_Usuario_Destino());

            // Busca tokens do usuário destinatário
            if (deviceTokenService == null) {
                logger.warn("DeviceTokenService não disponível");
                return;
            }

            DeviceTokenListDTO tokensList = deviceTokenService.getDeviceTokensByUsuario(usuarioDestinoId);

            if (tokensList == null || tokensList.getDeviceTokens() == null || tokensList.getDeviceTokens().isEmpty()) {
                logger.debug("Usuário {} não possui tokens de dispositivo ativos", usuarioDestinoId);
                return;
            }

            // Extrai os tokens da lista
            List<String> deviceTokens = tokensList.getDeviceTokens().stream()
                    .map(DeviceTokenDTO::getToken)
                    .filter(token -> token != null && !token.trim().isEmpty())
                    .collect(Collectors.toList());

            if (deviceTokens.isEmpty()) {
                logger.debug("Nenhum token válido encontrado para o usuário {}", usuarioDestinoId);
                return;
            }

            // Prepara dados customizados para a notificação
            Map<String, String> data = new HashMap<>();
            data.put("type", "chat_message");
            data.put("messageId", message.getId() != null ? message.getId() : "");
            data.put("chatId", message.getId_Chat() != null ? message.getId_Chat() : "");
            data.put("senderId", message.getId_Usuario_Remetente() != null ? message.getId_Usuario_Remetente() : "");
            data.put("receiverId", message.getId_Usuario_Destino());

            // Título e corpo da notificação
            String title = "Nova mensagem";
            String body = message.getMenssagem() != null ? message.getMenssagem() : "Você recebeu uma nova mensagem";

            // Limita o tamanho do corpo da mensagem (OneSignal tem limite)
            if (body.length() > 200) {
                body = body.substring(0, 197) + "...";
            }

            // Envia push notification
            if (oneSignalConfig != null) {
                logger.info("🔔 Tentando enviar push notification - Usuário: {}, Tokens: {}",
                    usuarioDestinoId, deviceTokens.size());

                int sentCount = oneSignalConfig.sendPushNotificationToMultiple(deviceTokens, title, body, data);

                if (sentCount > 0) {
                    logger.info("✅ Push notification enviada com sucesso para {} dispositivo(s)", sentCount);
                } else {
                    logger.warn("❌ FALHA ao enviar push notification");
                }
            } else {
                logger.warn("OneSignalConfig não disponível");
            }

        } catch (NumberFormatException e) {
            logger.warn("ID do usuário destinatário inválido: {}", message.getId_Usuario_Destino());
        } catch (Exception e) {
            logger.error("Erro ao enviar push notification", e);
            // Não lança exceção - não deve quebrar o envio da mensagem
        }
    }
}
