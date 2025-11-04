package com.AchadosPerdidos.API.Presentation.Controller;

import com.AchadosPerdidos.API.Application.DTOs.Itens.ItensListDTO;
import com.AchadosPerdidos.API.Application.Services.Interfaces.IItensService;
import com.AchadosPerdidos.API.Application.Services.Interfaces.INotificationService;
import com.AchadosPerdidos.API.Domain.Entity.Itens;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador para gerenciamento de prazos e doações
 * Implementa o sistema de controle de prazos mencionado no TCC
 */
@RestController
@RequestMapping("/api/deadline")
@CrossOrigin(origins = "*")
@Tag(name = "Deadlines", description = "API para gerenciamento de prazos e doações")
public class DeadlineController {

    @Autowired
    private IItensService itensService;

    @Autowired
    private INotificationService notificationService;

    /**
     * Busca itens próximos do prazo de doação
     * @param daysFromNow dias a partir de hoje (padrão: 25)
     * @return Lista de itens próximos do prazo
     */
    @GetMapping("/near-deadline")
    public ResponseEntity<List<Itens>> getItemsNearDeadline(
            @RequestParam(defaultValue = "25") int daysFromNow) {
        List<Itens> items = itensService.getItemsNearDonationDeadline(daysFromNow);
        return ResponseEntity.ok(items);
    }

    /**
     * Busca itens expirados (sem reivindicação por X dias)
     * @param daysExpired dias de expiração (padrão: 30)
     * @return Lista de itens expirados
     */
    @GetMapping("/expired")
    public ResponseEntity<List<Itens>> getExpiredItems(
            @RequestParam(defaultValue = "30") int daysExpired) {
        List<Itens> items = itensService.getExpiredItems(daysExpired);
        return ResponseEntity.ok(items);
    }

    /**
     * Marca um item específico como doado
     * @param itemId ID do item
     * @return Resultado da operação
     */
    @PostMapping("/mark-donated/{itemId}")
    public ResponseEntity<String> markItemAsDonated(@PathVariable int itemId) {
        boolean success = itensService.markItemAsDonated(itemId);
        if (success) {
            return ResponseEntity.ok("Item marcado como doado com sucesso");
        } else {
            return ResponseEntity.badRequest().body("Erro ao marcar item como doado");
        }
    }

    /**
     * Força a execução do processo de notificação de prazos
     * (Normalmente executado automaticamente via scheduler)
     * @return Resultado da operação
     */
    @PostMapping("/notify-deadlines")
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
    public ResponseEntity<DeadlineStats> getDeadlineStats() {
        try {
            List<Itens> nearDeadline = itensService.getItemsNearDonationDeadline(25);
            List<Itens> expired = itensService.getExpiredItems(30);
            ItensListDTO donated = itensService.getItensByStatus(3); // Status "Doado"
            
            DeadlineStats stats = new DeadlineStats();
            stats.setItemsNearDeadline(nearDeadline.size());
            stats.setExpiredItems(expired.size());
            stats.setDonatedItems(donated.getTotalCount());
            stats.setTotalProcessed(nearDeadline.size() + expired.size() + donated.getTotalCount());
            
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * Classe para estatísticas de prazos
     */
    public static class DeadlineStats {
        private int itemsNearDeadline;
        private int expiredItems;
        private int donatedItems;
        private int totalProcessed;

        // Getters e Setters
        public int getItemsNearDeadline() { return itemsNearDeadline; }
        public void setItemsNearDeadline(int itemsNearDeadline) { this.itemsNearDeadline = itemsNearDeadline; }
        
        public int getExpiredItems() { return expiredItems; }
        public void setExpiredItems(int expiredItems) { this.expiredItems = expiredItems; }
        
        public int getDonatedItems() { return donatedItems; }
        public void setDonatedItems(int donatedItems) { this.donatedItems = donatedItems; }
        
        public int getTotalProcessed() { return totalProcessed; }
        public void setTotalProcessed(int totalProcessed) { this.totalProcessed = totalProcessed; }
    }
}
