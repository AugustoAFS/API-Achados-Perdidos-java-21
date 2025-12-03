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
            notificationService.sendDonationDeadlineWarning();
            return ResponseEntity.ok("Notificações de prazo enviadas com sucesso");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao enviar notificações: " + e.getMessage());
        }
    }


    /**
     * Estatísticas de prazos e doações
     * @return Estatísticas do sistema
     */

}
