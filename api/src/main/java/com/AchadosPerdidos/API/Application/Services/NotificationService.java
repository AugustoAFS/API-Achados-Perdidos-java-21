package com.AchadosPerdidos.API.Application.Services;

import com.AchadosPerdidos.API.Application.Services.Interfaces.INotificationService;
import com.AchadosPerdidos.API.Application.Services.Interfaces.IUsuariosService;
import com.AchadosPerdidos.API.Application.Services.Interfaces.IItensService;
import com.AchadosPerdidos.API.Application.Services.Interfaces.IChatService;
import com.AchadosPerdidos.API.Domain.Entity.Chat.ChatMessage;
import com.AchadosPerdidos.API.Domain.Enum.Tipo_Menssagem;
import com.AchadosPerdidos.API.Domain.Enum.Status_Menssagem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

/**
 * Serviço de notificações automáticas para o sistema de achados e perdidos
 * Implementa as funcionalidades mencionadas no TCC para notificações automáticas
 */
@Service
public class NotificationService implements INotificationService {

    @Autowired
    private IUsuariosService usuariosService;

    @Autowired
    private IItensService itensService;

    @Autowired
    private IChatService chatService;

    /**
     * Notifica quando um item é encontrado e registrado no sistema
     * @param itemId ID do item encontrado
     * @param finderId ID do usuário que encontrou
     */
    @Override
    @Async
    public void notifyItemFound(int itemId, int finderId) {
        try {
            // Busca o item e o usuário que encontrou
            var item = itensService.getItemEntityById(itemId);
            var finder = usuariosService.getUsuarioById(finderId);
            
            if (item != null && finder != null) {
                // Cria mensagem de notificação
                String message = String.format(
                    "Novo item encontrado: %s. Local: %s. Encontrado por: %s",
                    item.getNome(),
                    "Campus", // TODO: Implementar busca do local
                    finder.getNomeCompleto()
                );
                
                // Envia notificação para todos os usuários ativos
                sendNotificationToAllUsers(message, "ITEM_ENCONTRADO");
                
                System.out.println("Notificação enviada: Item encontrado - " + item.getNome());
            }
        } catch (Exception e) {
            System.err.println("Erro ao enviar notificação de item encontrado: " + e.getMessage());
        }
    }

    /**
     * Notifica quando um item é reivindicado
     * @param itemId ID do item reivindicado
     * @param claimantId ID do usuário que reivindicou
     * @param ownerId ID do proprietário do item
     */
    @Override
    @Async
    public void notifyItemClaimed(int itemId, int claimantId, int ownerId) {
        try {
            var item = itensService.getItemEntityById(itemId);
            var claimant = usuariosService.getUsuarioById(claimantId);
            var owner = usuariosService.getUsuarioById(ownerId);
            
            if (item != null && claimant != null && owner != null) {
                // Notifica o proprietário
                String ownerMessage = String.format(
                    "Seu item '%s' foi reivindicado por %s. Verifique a reivindicação.",
                    item.getNome(),
                    claimant.getNomeCompleto()
                );
                
                sendNotificationToUser(ownerId, ownerMessage, "ITEM_REIVINDICADO");
                
                // Notifica o reivindicador
                String claimantMessage = String.format(
                    "Sua reivindicação do item '%s' foi registrada. Aguarde confirmação do proprietário.",
                    item.getNome()
                );
                
                sendNotificationToUser(claimantId, claimantMessage, "REIVINDICACAO_REGISTRADA");
                
                System.out.println("Notificações enviadas: Item reivindicado - " + item.getNome());
            }
        } catch (Exception e) {
            System.err.println("Erro ao enviar notificação de item reivindicado: " + e.getMessage());
        }
    }

    /**
     * Notifica quando um item é devolvido
     * @param itemId ID do item devolvido
     * @param ownerId ID do proprietário
     * @param finderId ID do usuário que encontrou
     */
    @Override
    @Async
    public void notifyItemReturned(int itemId, int ownerId, int finderId) {
        try {
            var item = itensService.getItemEntityById(itemId);
            var owner = usuariosService.getUsuarioById(ownerId);
            var finder = usuariosService.getUsuarioById(finderId);
            
            if (item != null && owner != null && finder != null) {
                // Notifica o proprietário
                String ownerMessage = String.format(
                    "Seu item '%s' foi devolvido com sucesso! Obrigado por usar o sistema de achados e perdidos.",
                    item.getNome()
                );
                
                sendNotificationToUser(ownerId, ownerMessage, "ITEM_DEVOLVIDO");
                
                // Notifica quem encontrou
                String finderMessage = String.format(
                    "O item '%s' foi devolvido ao proprietário. Obrigado pela colaboração!",
                    item.getNome()
                );
                
                sendNotificationToUser(finderId, finderMessage, "ITEM_DEVOLVIDO");
                
                System.out.println("Notificações enviadas: Item devolvido - " + item.getNome());
            }
        } catch (Exception e) {
            System.err.println("Erro ao enviar notificação de item devolvido: " + e.getMessage());
        }
    }

    /**
     * Notifica sobre itens próximos do prazo de doação (30 dias)
     * Executado diariamente às 9:00
     */
    @Override
    @Scheduled(cron = "0 0 9 * * ?")
    public void notifyItemsNearDonationDeadline() {
        try {
            // Busca itens próximos do prazo (25+ dias)
            List<com.AchadosPerdidos.API.Domain.Entity.Itens> itemsNearDeadline = itensService.getItemsNearDonationDeadline(25);
            
            for (com.AchadosPerdidos.API.Domain.Entity.Itens item : itemsNearDeadline) {
                String message = String.format(
                    "ATENÇÃO: O item '%s' está próximo do prazo de doação (30 dias). " +
                    "Se não for reivindicado em breve, será destinado à doação.",
                    item.getNome()
                );
                
                sendNotificationToUser(item.getUsuarioRelatorId(), message, "PRAZO_DOACAO");
                
                System.out.println("Notificação de prazo enviada para item: " + item.getNome());
            }
        } catch (Exception e) {
            System.err.println("Erro ao enviar notificações de prazo: " + e.getMessage());
        }
    }

    /**
     * Marca itens como "Doado" após 30 dias sem reivindicação
     * Executado diariamente às 10:00
     */
    @Override
    @Scheduled(cron = "0 0 10 * * ?")
    public void markItemsAsDonated() {
        try {
            List<com.AchadosPerdidos.API.Domain.Entity.Itens> expiredItems = itensService.getExpiredItems(30);
            
            for (com.AchadosPerdidos.API.Domain.Entity.Itens item : expiredItems) {
                // Atualiza status para "Doado"
                itensService.markItemAsDonated(item.getId());
                
                // Notifica o usuário que encontrou
                String message = String.format(
                    "O item '%s' foi destinado à doação após 30 dias sem reivindicação, " +
                    "conforme política do sistema.",
                    item.getNome()
                );
                
                sendNotificationToUser(item.getUsuarioRelatorId(), message, "ITEM_DOADO");
                
                System.out.println("Item marcado como doado: " + item.getNome());
            }
        } catch (Exception e) {
            System.err.println("Erro ao marcar itens como doados: " + e.getMessage());
        }
    }

    /**
     * Envia notificação para um usuário específico
     */
    private void sendNotificationToUser(int userId, String message, String type) {
        try {
            // Cria mensagem de chat para notificação
            ChatMessage notification = new ChatMessage(
                "system", // Chat ID do sistema
                String.valueOf(userId), // Destinatário
                "system", // Remetente (sistema)
                message,
                Tipo_Menssagem.SYSTEM,
                Status_Menssagem.SENT
            );
            
            // Salva no MongoDB
            chatService.saveMessage(notification);
            
            // TODO: Implementar notificação push para dispositivos móveis
            
        } catch (Exception e) {
            System.err.println("Erro ao enviar notificação para usuário " + userId + ": " + e.getMessage());
        }
    }

    /**
     * Envia notificação para todos os usuários ativos
     */
    private void sendNotificationToAllUsers(String message, String type) {
        try {
            // TODO: Implementar busca de todos os usuários ativos
            // Por enquanto, apenas log da notificação
            System.out.println("Notificação geral: " + message);
            
        } catch (Exception e) {
            System.err.println("Erro ao enviar notificação geral: " + e.getMessage());
        }
    }
}
