package com.AchadosPerdidos.API.Presentation.Controller;

import com.AchadosPerdidos.API.Application.DTOs.FotoItem.FotoItemListDTO;
import com.AchadosPerdidos.API.Application.Services.Interfaces.IFotoItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/fotos-item")
@Tag(name = "Fotos Item", description = "API para gerenciamento de relacionamento entre fotos e itens")
public class FotoItemController {

    @Autowired
    private IFotoItemService fotoItemService;

    @GetMapping
    @Operation(summary = "Listar todos os relacionamentos foto-item")
    public ResponseEntity<FotoItemListDTO> getAllFotosItem() {
        try {
            FotoItemListDTO fotosItem = fotoItemService.getAllFotosItem();
            return ResponseEntity.ok(fotosItem);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping("/active")
    @Operation(summary = "Listar relacionamentos foto-item ativos")
    public ResponseEntity<FotoItemListDTO> getActiveFotosItem() {
        try {
            FotoItemListDTO activeFotosItem = fotoItemService.getActiveFotosItem();
            return ResponseEntity.ok(activeFotosItem);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}

