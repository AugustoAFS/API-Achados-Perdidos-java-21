package com.AchadosPerdidos.API.Presentation.Controller;

import com.AchadosPerdidos.API.Application.DTOs.ItemPerdido.ItemPerdidoDTO;
import com.AchadosPerdidos.API.Application.DTOs.ItemPerdido.ItemPerdidoListDTO;
import com.AchadosPerdidos.API.Application.DTOs.ItemPerdido.ItemPerdidoCreateDTO;
import com.AchadosPerdidos.API.Application.DTOs.ItemPerdido.ItemPerdidoUpdateDTO;
import com.AchadosPerdidos.API.Application.Services.Interfaces.IItemPerdidoService;
import com.AchadosPerdidos.API.Application.Services.Interfaces.IJWTService;
import com.AchadosPerdidos.API.Exeptions.BusinessException;
import com.AchadosPerdidos.API.Exeptions.ResourceNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/itens-perdidos")
@Tag(name = "Itens Perdidos", description = "API para gerenciamento de itens perdidos")
public class ItemPerdidoController {

    @Autowired
    private IItemPerdidoService itemPerdidoService;

    @Autowired
    private IJWTService jwtService;

    @GetMapping
    @Operation(summary = "Listar todos os itens perdidos")
    public ResponseEntity<ItemPerdidoListDTO> getAllItensPerdidos() {
        try {
            ItemPerdidoListDTO itensPerdidos = itemPerdidoService.getAllItensPerdidos();
            return ResponseEntity.ok(itensPerdidos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar item perdido por ID")
    public ResponseEntity<ItemPerdidoDTO> getItemPerdidoById(@PathVariable Integer id) {
        try {
            ItemPerdidoDTO itemPerdido = itemPerdidoService.getItemPerdidoById(id);
            return ResponseEntity.ok(itemPerdido);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    @Operation(summary = "Criar novo item perdido", description = "Cria um item perdido com seus dados, fotos e registro na tabela itens_perdidos. O usuário relator é obtido automaticamente do token JWT.")
    public ResponseEntity<ItemPerdidoDTO> createItemPerdido(
            @RequestBody ItemPerdidoCreateDTO createDTO,
            HttpServletRequest request) {
        try {
            // Extrair token JWT do header Authorization
            String authHeader = request.getHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            String token = authHeader.substring(7);

            // Obter userId do token
            String userIdStr = jwtService.getUserIdFromToken(token);
            if (userIdStr == null || userIdStr.trim().isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            Integer usuarioRelatorId = Integer.valueOf(userIdStr);

            // Definir usuário relator no DTO
            createDTO.setUsuarioRelatorId(usuarioRelatorId);

            // Criar item perdido (a service cria o item, as fotos e o registro em itens_perdidos)
            ItemPerdidoDTO createdItemPerdido = itemPerdidoService.createItemPerdido(createDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdItemPerdido);
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
    @Operation(summary = "Atualizar item perdido")
    public ResponseEntity<ItemPerdidoDTO> updateItemPerdido(
            @PathVariable Integer id,
            @RequestBody ItemPerdidoUpdateDTO updateDTO) {
        try {
            ItemPerdidoDTO updatedItemPerdido = itemPerdidoService.updateItemPerdido(id, updateDTO);
            return ResponseEntity.ok(updatedItemPerdido);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (BusinessException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar item perdido (soft delete)")
    public ResponseEntity<Void> deleteItemPerdido(@PathVariable Integer id) {
        try {
            boolean deleted = itemPerdidoService.deleteItemPerdido(id);
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
    @Operation(summary = "Listar itens perdidos ativos")
    public ResponseEntity<ItemPerdidoListDTO> getActiveItensPerdidos() {
        try {
            ItemPerdidoListDTO activeItensPerdidos = itemPerdidoService.getActiveItensPerdidos();
            return ResponseEntity.ok(activeItensPerdidos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/item/{itemId}")
    @Operation(summary = "Buscar item perdido por ID do item")
    public ResponseEntity<ItemPerdidoDTO> getItemPerdidoByItemId(@PathVariable Integer itemId) {
        try {
            ItemPerdidoDTO itemPerdido = itemPerdidoService.getItemPerdidoByItemId(itemId);
            return ResponseEntity.ok(itemPerdido);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
