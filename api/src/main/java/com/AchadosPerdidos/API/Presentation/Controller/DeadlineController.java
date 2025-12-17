package com.AchadosPerdidos.API.Presentation.Controller;

import com.AchadosPerdidos.API.Application.Services.Interfaces.INotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller para gerenciamento de prazos e doações
 * Responsabilidade: Camada de apresentação - recebe requisições HTTP e delega para os services
 */
@RestController
@RequestMapping("/api/deadline")
@Tag(name = "Deadlines", description = "API para gerenciamento de prazos e doações")
public class DeadlineController {

    @Autowired
    private INotificationService notificationService;

    @PostMapping("/notify-deadlines")
    @Operation(summary = "Notificar prazos", description = "Força a execução do processo de notificação de prazos")
    public ResponseEntity<String> notifyDeadlines() {
        notificationService.sendDonationDeadlineWarning();
        return ResponseEntity.ok("Notificações de prazo enviadas com sucesso");
    }

}
