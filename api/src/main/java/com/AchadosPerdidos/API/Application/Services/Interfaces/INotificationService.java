public interface INotificationService {

    /**
     * Envia notificação quando um item é encontrado.
     */
    void sendItemFoundNotification(int itemId, int finderId);

    /**
     * Envia notificação quando um item é reivindicado.
     */
    void sendItemClaimedNotification(int itemId, int claimantId, int ownerId);

    /**
     * Envia notificação quando um item é devolvido.
     */
    void sendItemReturnedNotification(int itemId, int ownerId, int finderId);

    /**
     * Envia notificação para itens próximos do prazo de doação.
     */
    void sendDonationDeadlineWarning();
}
