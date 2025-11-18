package com.AchadosPerdidos.API.Presentation.Controller;

import com.AchadosPerdidos.API.Application.DTOs.ItemDoado.ItemDoadoDTO;
import com.AchadosPerdidos.API.Application.DTOs.ItemDoado.ItemDoadoListDTO;
import com.AchadosPerdidos.API.Application.DTOs.ItemDoado.ItemDoadoCreateDTO;
import com.AchadosPerdidos.API.Application.DTOs.ItemDoado.ItemDoadoUpdateDTO;
import com.AchadosPerdidos.API.Application.Services.Interfaces.IItemDoadoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/itens-doados")
@Tag(name = "Itens Doados", description = "API para gerenciamento de itens doados")
public class ItemDoadoController {

    @Autowired
    private IItemDoadoService itemDoadoService;

    @GetMapping
    @Operation(summary = "Listar todos os itens doados")
    public ResponseEntity<ItemDoadoListDTO> getAllItensDoados() {
        ItemDoadoListDTO itensDoados = itemDoadoService.getAllItensDoados();
        return ResponseEntity.ok(itensDoados);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar item doado por ID")
    public ResponseEntity<ItemDoadoDTO> getItemDoadoById(@PathVariable Integer id) {
        ItemDoadoDTO itemDoado = itemDoadoService.getItemDoadoById(id);
        return ResponseEntity.ok(itemDoado);
    }

    @PostMapping
    @Operation(summary = "Criar novo item doado")
    public ResponseEntity<ItemDoadoDTO> createItemDoado(@RequestBody ItemDoadoCreateDTO createDTO) {
        ItemDoadoDTO createdItemDoado = itemDoadoService.createItemDoado(createDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdItemDoado);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar item doado")
    public ResponseEntity<ItemDoadoDTO> updateItemDoado(@PathVariable Integer id, @RequestBody ItemDoadoUpdateDTO updateDTO) {
        ItemDoadoDTO updatedItemDoado = itemDoadoService.updateItemDoado(id, updateDTO);
        return ResponseEntity.ok(updatedItemDoado);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar item doado (soft delete)")
    public ResponseEntity<Void> deleteItemDoado(@PathVariable Integer id) {
        boolean deleted = itemDoadoService.deleteItemDoado(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/active")
    @Operation(summary = "Listar itens doados ativos")
    public ResponseEntity<ItemDoadoListDTO> getActiveItensDoados() {
        ItemDoadoListDTO activeItensDoados = itemDoadoService.getActiveItensDoados();
        return ResponseEntity.ok(activeItensDoados);
    }

    @GetMapping("/item/{itemId}")
    @Operation(summary = "Buscar item doado por ID do item")
    public ResponseEntity<ItemDoadoDTO> getItemDoadoByItemId(@PathVariable Integer itemId) {
        ItemDoadoDTO itemDoado = itemDoadoService.findByItemId(itemId);
        return ResponseEntity.ok(itemDoado);
    }
}

