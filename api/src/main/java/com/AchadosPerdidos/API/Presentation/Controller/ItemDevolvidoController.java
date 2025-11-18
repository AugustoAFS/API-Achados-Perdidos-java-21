package com.AchadosPerdidos.API.Presentation.Controller;

import com.AchadosPerdidos.API.Application.DTOs.ItemDevolvido.ItemDevolvidoCreateDTO;
import com.AchadosPerdidos.API.Application.DTOs.ItemDevolvido.ItemDevolvidoDTO;
import com.AchadosPerdidos.API.Application.DTOs.ItemDevolvido.ItemDevolvidoListDTO;
import com.AchadosPerdidos.API.Application.DTOs.ItemDevolvido.ItemDevolvidoUpdateDTO;
import com.AchadosPerdidos.API.Application.Services.Interfaces.IItemDevolvidoService;
import com.AchadosPerdidos.API.Exeptions.BusinessException;
import com.AchadosPerdidos.API.Exeptions.ResourceNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/itens-devolvidos")
@Tag(name = "Itens Devolvidos", description = "API para gerenciamento de itens devolvidos")
public class ItemDevolvidoController {

    @Autowired
    private IItemDevolvidoService itemDevolvidoService;

    @GetMapping
    @Operation(summary = "Listar todos os itens devolvidos")
    public ResponseEntity<ItemDevolvidoListDTO> getAllItensDevolvidos() {
        try {
            ItemDevolvidoListDTO itensDevolvidos = itemDevolvidoService.getAllItensDevolvidos();
            return ResponseEntity.ok(itensDevolvidos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar item devolvido por ID")
    public ResponseEntity<ItemDevolvidoDTO> getItemDevolvidoById(@PathVariable Integer id) {
        try {
            ItemDevolvidoDTO itemDevolvido = itemDevolvidoService.getItemDevolvidoById(id);
            return ResponseEntity.ok(itemDevolvido);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    @Operation(summary = "Criar novo item devolvido")
    public ResponseEntity<ItemDevolvidoDTO> createItemDevolvido(@RequestBody ItemDevolvidoCreateDTO createDTO) {
        try {
            ItemDevolvidoDTO createdItemDevolvido = itemDevolvidoService.createItemDevolvido(createDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdItemDevolvido);
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

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar item devolvido")
    public ResponseEntity<ItemDevolvidoDTO> updateItemDevolvido(
            @PathVariable Integer id,
            @RequestBody ItemDevolvidoUpdateDTO updateDTO) {
        try {
            ItemDevolvidoDTO updatedItemDevolvido = itemDevolvidoService.updateItemDevolvido(id, updateDTO);
            return ResponseEntity.ok(updatedItemDevolvido);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (BusinessException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar item devolvido (soft delete)")
    public ResponseEntity<Void> deleteItemDevolvido(@PathVariable Integer id) {
        try {
            boolean deleted = itemDevolvidoService.deleteItemDevolvido(id);
            if (deleted) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (BusinessException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/active")
    @Operation(summary = "Listar itens devolvidos ativos")
    public ResponseEntity<ItemDevolvidoListDTO> getActiveItensDevolvidos() {
        try {
            ItemDevolvidoListDTO activeItensDevolvidos = itemDevolvidoService.getActiveItensDevolvidos();
            return ResponseEntity.ok(activeItensDevolvidos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/item/{itemId}")
    @Operation(summary = "Buscar itens devolvidos por ID do item")
    public ResponseEntity<ItemDevolvidoListDTO> findByItemId(@PathVariable Integer itemId) {
        try {
            ItemDevolvidoListDTO itensDevolvidos = itemDevolvidoService.findByItemId(itemId);
            return ResponseEntity.ok(itensDevolvidos);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/usuario/{usuarioId}")
    @Operation(summary = "Buscar itens devolvidos por ID do usuário devolvedor")
    public ResponseEntity<ItemDevolvidoListDTO> findByUsuarioDevolvedorId(@PathVariable Integer usuarioId) {
        try {
            ItemDevolvidoListDTO itensDevolvidos = itemDevolvidoService.findByUsuarioDevolvedorId(usuarioId);
            return ResponseEntity.ok(itensDevolvidos);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

