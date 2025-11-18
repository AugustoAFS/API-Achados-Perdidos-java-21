package com.AchadosPerdidos.API.Presentation.Controller;

import com.AchadosPerdidos.API.Application.DTOs.Item.ItemListDTO;
import com.AchadosPerdidos.API.Application.Services.Interfaces.IItensService;
import com.AchadosPerdidos.API.Application.Services.Interfaces.INotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador para gerenciamento de prazos e doações
 * Implementa o sistema de controle de prazos mencionado no TCC
 * 
 * NOTA: Algumas funcionalidades foram temporariamente desabilitadas devido à remoção da tabela status_item.
 * A lógica de prazos precisa ser repensada com base no novo schema que usa tipo_item.
 */
@RestController
@RequestMapping("/api/deadline")
@Tag(name = "Deadlines", description = "API para gerenciamento de prazos e doações")
public class DeadlineController {

    @Autowired
    private IItensService itensService;

    @Autowired
    private INotificationService notificationService;

    /**
     * Força a execução do processo de notificação de prazos
     * (Normalmente executado automaticamente via scheduler)
     * @return Resultado da operação
     */
    @PostMapping("/notify-deadlines")
    @Operation(summary = "Notificar prazos", description = "Força a execução do processo de notificação de prazos")
    public ResponseEntity<String> notifyDeadlines() {
        try {
            notificationService.notifyItemsNearDonationDeadline();
            return ResponseEntity.ok("Notificações de prazo enviadas com sucesso");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao enviar notificações: " + e.getMessage());
        }
    }

    /**
     * Força a execução do processo de marcação de itens como doados
     * (Normalmente executado automaticamente via scheduler)
     * @return Resultado da operação
     */
    @PostMapping("/mark-expired-donated")
    @Operation(summary = "Marcar itens expirados como doados", description = "Força a execução do processo de marcação de itens como doados")
    public ResponseEntity<String> markExpiredAsDonated() {
        try {
            notificationService.markItemsAsDonated();
            return ResponseEntity.ok("Itens expirados marcados como doados com sucesso");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao marcar itens como doados: " + e.getMessage());
        }
    }

    /**
     * Estatísticas de prazos e doações
     * @return Estatísticas do sistema
     */
    @GetMapping("/stats")
    @Operation(summary = "Estatísticas de prazos", description = "Retorna estatísticas sobre prazos e doações")
    public ResponseEntity<DeadlineStats> getDeadlineStats() {
        try {
            ItemListDTO perdidos = itensService.getItensByTipo("PERDIDO");
            ItemListDTO achados = itensService.getItensByTipo("ACHADO");
            ItemListDTO doados = itensService.getItensByTipo("DOADO");
            
            DeadlineStats stats = new DeadlineStats();
            stats.setItemsPerdidos(perdidos != null && perdidos.getItens() != null ? perdidos.getItens().size() : 0);
            stats.setItemsAchados(achados != null && achados.getItens() != null ? achados.getItens().size() : 0);
            stats.setItemsDoados(doados != null && doados.getItens() != null ? doados.getItens().size() : 0);
            stats.setTotalProcessed(
                (perdidos != null && perdidos.getItens() != null ? perdidos.getItens().size() : 0) +
                (achados != null && achados.getItens() != null ? achados.getItens().size() : 0) +
                (doados != null && doados.getItens() != null ? doados.getItens().size() : 0)
            );
            
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * Classe para estatísticas de prazos
     */
    public static class DeadlineStats {
        private int itemsPerdidos;
        private int itemsAchados;
        private int itemsDoados;
        private int totalProcessed;

        // Getters e Setters
        public int getItemsPerdidos() { return itemsPerdidos; }
        public void setItemsPerdidos(int itemsPerdidos) { this.itemsPerdidos = itemsPerdidos; }
        
        public int getItemsAchados() { return itemsAchados; }
        public void setItemsAchados(int itemsAchados) { this.itemsAchados = itemsAchados; }
        
        public int getItemsDoados() { return itemsDoados; }
        public void setItemsDoados(int itemsDoados) { this.itemsDoados = itemsDoados; }
        
        public int getTotalProcessed() { return totalProcessed; }
        public void setTotalProcessed(int totalProcessed) { this.totalProcessed = totalProcessed; }
    }
}
