package com.AchadosPerdidos.API.Presentation.Controller;

import com.AchadosPerdidos.API.Application.DTOs.ItemAchado.ItemAchadoDTO;
import com.AchadosPerdidos.API.Application.DTOs.ItemAchado.ItemAchadoListDTO;
import com.AchadosPerdidos.API.Application.DTOs.ItemAchado.ItemAchadoCreateDTO;
import com.AchadosPerdidos.API.Application.DTOs.ItemAchado.ItemAchadoUpdateDTO;
import com.AchadosPerdidos.API.Application.Services.Interfaces.IItemAchadoService;
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
@RequestMapping("/api/itens-achados")
@Tag(name = "Itens Achados", description = "API para gerenciamento de itens achados")
public class ItemAchadoController {

    @Autowired
    private IItemAchadoService itemAchadoService;

    @Autowired
    private IJWTService jwtService;

    @GetMapping
    @Operation(summary = "Listar todos os itens achados")
    public ResponseEntity<ItemAchadoListDTO> getAllItensAchados() {
        try {
            ItemAchadoListDTO itensAchados = itemAchadoService.getAllItensAchados();
            return ResponseEntity.ok(itensAchados);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar item achado por ID")
    public ResponseEntity<ItemAchadoDTO> getItemAchadoById(@PathVariable Integer id) {
        try {
            ItemAchadoDTO itemAchado = itemAchadoService.getItemAchadoById(id);
            return ResponseEntity.ok(itemAchado);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    @Operation(summary = "Criar novo item achado", description = "Cria um item achado com seus dados, fotos e registro na tabela itens_achados. O usuário relator é obtido automaticamente do token JWT.")
    public ResponseEntity<ItemAchadoDTO> createItemAchado(
            @RequestBody ItemAchadoCreateDTO createDTO,
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

            // Criar item achado (a service cria o item, as fotos e o registro em itens_achados)
            ItemAchadoDTO createdItemAchado = itemAchadoService.createItemAchado(createDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdItemAchado);
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
    @Operation(summary = "Atualizar item achado")
    public ResponseEntity<ItemAchadoDTO> updateItemAchado(
            @PathVariable Integer id,
            @RequestBody ItemAchadoUpdateDTO updateDTO) {
        try {
            ItemAchadoDTO updatedItemAchado = itemAchadoService.updateItemAchado(id, updateDTO);
            return ResponseEntity.ok(updatedItemAchado);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (BusinessException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar item achado (soft delete)")
    public ResponseEntity<Void> deleteItemAchado(@PathVariable Integer id) {
        try {
            boolean deleted = itemAchadoService.deleteItemAchado(id);
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
    @Operation(summary = "Listar itens achados ativos")
    public ResponseEntity<ItemAchadoListDTO> getActiveItensAchados() {
        try {
            ItemAchadoListDTO activeItensAchados = itemAchadoService.getActiveItensAchados();
            return ResponseEntity.ok(activeItensAchados);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/item/{itemId}")
    @Operation(summary = "Buscar item achado por ID do item")
    public ResponseEntity<ItemAchadoDTO> getItemAchadoByItemId(@PathVariable Integer itemId) {
        try {
            ItemAchadoDTO itemAchado = itemAchadoService.getItemAchadoByItemId(itemId);
            return ResponseEntity.ok(itemAchado);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
