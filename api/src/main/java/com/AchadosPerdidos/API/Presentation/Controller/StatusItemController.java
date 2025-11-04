package com.AchadosPerdidos.API.Presentation.Controller;

import com.AchadosPerdidos.API.Application.DTOs.StatusItem.StatusItemDTO;
import com.AchadosPerdidos.API.Application.DTOs.StatusItem.StatusItemCreateDTO;
import com.AchadosPerdidos.API.Application.DTOs.StatusItem.StatusItemUpdateDTO;
import com.AchadosPerdidos.API.Application.Services.Interfaces.IStatusItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/status-item")
@CrossOrigin(origins = "*")
@Tag(name = "Status Item", description = "API para gerenciamento de status de itens")
public class StatusItemController {

    @Autowired
    private IStatusItemService statusItemService;
    
    @GetMapping
    @Operation(summary = "Listar todos os status")
    public ResponseEntity<List<StatusItemDTO>> getAllStatus() {
        List<StatusItemDTO> statusItems = statusItemService.getAllStatus();
        return ResponseEntity.ok(statusItems);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar status por ID")
    public ResponseEntity<StatusItemDTO> getStatusById(@PathVariable Integer id) {
        StatusItemDTO statusItem = statusItemService.getStatusById(id);
        if (statusItem == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(statusItem);
    }

    @PostMapping
    @Operation(summary = "Criar novo status")
    public ResponseEntity<StatusItemDTO> createStatus(@RequestBody StatusItemCreateDTO statusCreateDTO) {
        StatusItemDTO createdStatus = statusItemService.createStatus(statusCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStatus);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar status")
    public ResponseEntity<StatusItemDTO> updateStatus(@PathVariable Integer id, @RequestBody StatusItemUpdateDTO statusUpdateDTO) {
        StatusItemDTO updatedStatus = statusItemService.updateStatus(id, statusUpdateDTO);
        if (updatedStatus == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedStatus);
    }

    @PostMapping("/{id}/delete")
    @Operation(summary = "Inativar status (soft delete)")
    public ResponseEntity<StatusItemDTO> deleteStatus(@PathVariable Integer id) {
        StatusItemDTO deletedStatus = statusItemService.deleteStatus(id);
        return ResponseEntity.ok(deletedStatus);
    }
}
