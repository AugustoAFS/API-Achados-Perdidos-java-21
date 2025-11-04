package com.AchadosPerdidos.API.Application.Services.Interfaces;

/**
 * Interface para o serviço de notificações automáticas
 * Implementa as funcionalidades de notificação mencionadas no TCC
 */
public interface INotificationService {
    
    /**
     * Notifica quando um item é encontrado e registrado no sistema
     * @param itemId ID do item encontrado
     * @param finderId ID do usuário que encontrou
     */
    void notifyItemFound(int itemId, int finderId);
    
    /**
     * Notifica quando um item é reivindicado
     * @param itemId ID do item reivindicado
     * @param claimantId ID do usuário que reivindicou
     * @param ownerId ID do proprietário do item
     */
    void notifyItemClaimed(int itemId, int claimantId, int ownerId);
    
    /**
     * Notifica quando um item é devolvido
     * @param itemId ID do item devolvido
     * @param ownerId ID do proprietário
     * @param finderId ID do usuário que encontrou
     */
    void notifyItemReturned(int itemId, int ownerId, int finderId);
    
    /**
     * Notifica sobre itens próximos do prazo de doação
     * Executado automaticamente via scheduler
     */
    void notifyItemsNearDonationDeadline();
    
    /**
     * Marca itens como "Doado" após 30 dias sem reivindicação
     * Executado automaticamente via scheduler
     */
    void markItemsAsDonated();
}
