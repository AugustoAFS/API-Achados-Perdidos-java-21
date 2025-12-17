package com.AchadosPerdidos.API.Presentation.Controller;

import com.AchadosPerdidos.API.Application.DTOs.FotoItem.FotoItemListDTO;
import com.AchadosPerdidos.API.Application.Services.Interfaces.IFotoItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller para gerenciamento de relacionamento foto-item
 * Responsabilidade: Camada de apresentação - recebe requisições HTTP e delega para os services
 */
@RestController
@RequestMapping("/api/fotos-item")
@Tag(name = "Fotos Item", description = "API para gerenciamento de relacionamento entre fotos e itens")
public class FotoItemController {

    @Autowired
    private IFotoItemService fotoItemService;

    @GetMapping
    @Operation(summary = "Listar todos os relacionamentos foto-item")
    public ResponseEntity<FotoItemListDTO> getAllFotosItem() {
        return ResponseEntity.ok(fotoItemService.getAllFotosItem());
    }

    @GetMapping("/active")
    @Operation(summary = "Listar relacionamentos foto-item ativos")
    public ResponseEntity<FotoItemListDTO> getActiveFotosItem() {
        return ResponseEntity.ok(fotoItemService.getActiveFotosItem());
    }
}

