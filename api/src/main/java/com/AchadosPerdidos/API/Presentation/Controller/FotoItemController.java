package com.AchadosPerdidos.API.Presentation.Controller;

import com.AchadosPerdidos.API.Application.DTOs.FotoItem.FotoItemCreateDTO;
import com.AchadosPerdidos.API.Application.DTOs.FotoItem.FotoItemDTO;
import com.AchadosPerdidos.API.Application.DTOs.FotoItem.FotoItemListDTO;
import com.AchadosPerdidos.API.Application.DTOs.FotoItem.FotoItemUpdateDTO;
import com.AchadosPerdidos.API.Application.Services.Interfaces.IFotoItemService;
import com.AchadosPerdidos.API.Exeptions.BusinessException;
import com.AchadosPerdidos.API.Exeptions.ResourceNotFoundException;
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

    @GetMapping("/item/{itemId}/foto/{fotoId}")
    @Operation(summary = "Buscar relacionamento foto-item por IDs")
    public ResponseEntity<FotoItemDTO> getFotoItemByItemIdAndFotoId(
            @PathVariable Integer itemId,
            @PathVariable Integer fotoId) {
        try {
            FotoItemDTO fotoItem = fotoItemService.getFotoItemByItemIdAndFotoId(itemId, fotoId);
            return ResponseEntity.ok(fotoItem);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    @Operation(summary = "Criar novo relacionamento foto-item")
    public ResponseEntity<FotoItemDTO> createFotoItem(@RequestBody FotoItemCreateDTO createDTO) {
        try {
            FotoItemDTO createdFotoItem = fotoItemService.createFotoItem(createDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdFotoItem);
        } catch (BusinessException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/item/{itemId}/foto/{fotoId}")
    @Operation(summary = "Atualizar relacionamento foto-item")
    public ResponseEntity<FotoItemDTO> updateFotoItem(
            @PathVariable Integer itemId,
            @PathVariable Integer fotoId,
            @RequestBody FotoItemUpdateDTO updateDTO) {
        try {
            FotoItemDTO updatedFotoItem = fotoItemService.updateFotoItem(itemId, fotoId, updateDTO);
            return ResponseEntity.ok(updatedFotoItem);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (BusinessException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/item/{itemId}/foto/{fotoId}")
    @Operation(summary = "Deletar relacionamento foto-item")
    public ResponseEntity<Void> deleteFotoItem(
            @PathVariable Integer itemId,
            @PathVariable Integer fotoId) {
        try {
            boolean deleted = fotoItemService.deleteFotoItem(itemId, fotoId);
            if (deleted) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
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

    @GetMapping("/item/{itemId}")
    @Operation(summary = "Buscar fotos por ID do item")
    public ResponseEntity<FotoItemListDTO> findByItemId(@PathVariable Integer itemId) {
        try {
            FotoItemListDTO fotosItem = fotoItemService.findByItemId(itemId);
            return ResponseEntity.ok(fotosItem);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/foto/{fotoId}")
    @Operation(summary = "Buscar itens por ID da foto")
    public ResponseEntity<FotoItemListDTO> findByFotoId(@PathVariable Integer fotoId) {
        try {
            FotoItemListDTO fotosItem = fotoItemService.findByFotoId(fotoId);
            return ResponseEntity.ok(fotosItem);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

