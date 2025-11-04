package com.AchadosPerdidos.API.Presentation.Controller;

import com.AchadosPerdidos.API.Application.DTOs.Itens.ItensDTO;
import com.AchadosPerdidos.API.Application.DTOs.Itens.ItensListDTO;
import com.AchadosPerdidos.API.Application.DTOs.Itens.ItensCreateDTO;
import com.AchadosPerdidos.API.Application.DTOs.Itens.ItensUpdateDTO;
import com.AchadosPerdidos.API.Application.Services.Interfaces.IItensService;
import com.AchadosPerdidos.API.Application.Services.Interfaces.INotificationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/itens")
@CrossOrigin(origins = "*")
@Tag(name = "Itens", description = "API para gerenciamento de itens achados/perdidos")
public class ItensController {

    @Autowired
    private IItensService itensService;

    @Autowired
    private INotificationService notificationService;

    @GetMapping
    public ResponseEntity<ItensListDTO> getAllItens() {
        ItensListDTO itens = itensService.getAllItens();
        return ResponseEntity.ok(itens);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItensDTO> getItemById(@PathVariable int id) {
        ItensDTO item = itensService.getItemById(id);
        if (item != null) {
            return ResponseEntity.ok(item);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<ItensDTO> createItem(@RequestBody ItensCreateDTO itensCreateDTO) {
        ItensDTO createdItem = itensService.createItemFromDTO(itensCreateDTO);
        
        // Envia notificação automática quando item é encontrado
        if (createdItem != null) {
            notificationService.notifyItemFound(createdItem.getId(), createdItem.getUsuarioRelatorId());
        }
        
        return ResponseEntity.status(HttpStatus.CREATED).body(createdItem);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItensDTO> updateItem(@PathVariable int id, @RequestBody ItensUpdateDTO itensUpdateDTO) {
        ItensDTO updatedItem = itensService.updateItemFromDTO(id, itensUpdateDTO);
        if (updatedItem != null) {
            return ResponseEntity.ok(updatedItem);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable int id) {
        boolean deleted = itensService.deleteItem(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/active")
    public ResponseEntity<ItensListDTO> getActiveItens() {
        ItensListDTO activeItens = itensService.getActiveItens();
        return ResponseEntity.ok(activeItens);
    }

    @GetMapping("/status/{statusId}")
    public ResponseEntity<ItensListDTO> getItensByStatus(@PathVariable int statusId) {
        ItensListDTO itens = itensService.getItensByStatus(statusId);
        return ResponseEntity.ok(itens);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ItensListDTO> getItensByUser(@PathVariable int userId) {
        ItensListDTO itens = itensService.getItensByUser(userId);
        return ResponseEntity.ok(itens);
    }

    @GetMapping("/campus/{campusId}")
    public ResponseEntity<ItensListDTO> getItensByCampus(@PathVariable int campusId) {
        ItensListDTO itens = itensService.getItensByCampus(campusId);
        return ResponseEntity.ok(itens);
    }

    @GetMapping("/local/{localId}")
    public ResponseEntity<ItensListDTO> getItensByLocal(@PathVariable int localId) {
        ItensListDTO itens = itensService.getItensByLocal(localId);
        return ResponseEntity.ok(itens);
    }

    @GetMapping("/empresa/{empresaId}")
    public ResponseEntity<ItensListDTO> getItensByEmpresa(@PathVariable int empresaId) {
        ItensListDTO itens = itensService.getItensByEmpresa(empresaId);
        return ResponseEntity.ok(itens);
    }

    @GetMapping("/search")
    public ResponseEntity<ItensListDTO> searchItens(@RequestParam String term) {
        ItensListDTO itens = itensService.searchItens(term);
        return ResponseEntity.ok(itens);
    }
}
