package com.AchadosPerdidos.API.Presentation.Controller;

import com.AchadosPerdidos.API.Application.DTOs.FotoUsuario.FotoUsuarioCreateDTO;
import com.AchadosPerdidos.API.Application.DTOs.FotoUsuario.FotoUsuarioDTO;
import com.AchadosPerdidos.API.Application.DTOs.FotoUsuario.FotoUsuarioListDTO;
import com.AchadosPerdidos.API.Application.DTOs.FotoUsuario.FotoUsuarioUpdateDTO;
import com.AchadosPerdidos.API.Application.Services.Interfaces.IFotoUsuarioService;
import com.AchadosPerdidos.API.Exeptions.BusinessException;
import com.AchadosPerdidos.API.Exeptions.ResourceNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/fotos-usuario")
@Tag(name = "Fotos Usuário", description = "API para gerenciamento de relacionamento entre fotos e usuários")
public class FotoUsuarioController {

    @Autowired
    private IFotoUsuarioService fotoUsuarioService;

    @GetMapping
    @Operation(summary = "Listar todos os relacionamentos foto-usuário")
    public ResponseEntity<FotoUsuarioListDTO> getAllFotosUsuario() {
        try {
            FotoUsuarioListDTO fotosUsuario = fotoUsuarioService.getAllFotosUsuario();
            return ResponseEntity.ok(fotosUsuario);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/usuario/{usuarioId}/foto/{fotoId}")
    @Operation(summary = "Buscar relacionamento foto-usuário por IDs")
    public ResponseEntity<FotoUsuarioDTO> getFotoUsuarioByUsuarioIdAndFotoId(
            @PathVariable Integer usuarioId,
            @PathVariable Integer fotoId) {
        try {
            FotoUsuarioDTO fotoUsuario = fotoUsuarioService.getFotoUsuarioByUsuarioIdAndFotoId(usuarioId, fotoId);
            return ResponseEntity.ok(fotoUsuario);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    @Operation(summary = "Criar novo relacionamento foto-usuário")
    public ResponseEntity<FotoUsuarioDTO> createFotoUsuario(@RequestBody FotoUsuarioCreateDTO createDTO) {
        try {
            FotoUsuarioDTO createdFotoUsuario = fotoUsuarioService.createFotoUsuario(createDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdFotoUsuario);
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

    @PutMapping("/usuario/{usuarioId}/foto/{fotoId}")
    @Operation(summary = "Atualizar relacionamento foto-usuário")
    public ResponseEntity<FotoUsuarioDTO> updateFotoUsuario(
            @PathVariable Integer usuarioId,
            @PathVariable Integer fotoId,
            @RequestBody FotoUsuarioUpdateDTO updateDTO) {
        try {
            FotoUsuarioDTO updatedFotoUsuario = fotoUsuarioService.updateFotoUsuario(usuarioId, fotoId, updateDTO);
            return ResponseEntity.ok(updatedFotoUsuario);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (BusinessException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/usuario/{usuarioId}/foto/{fotoId}")
    @Operation(summary = "Deletar relacionamento foto-usuário")
    public ResponseEntity<Void> deleteFotoUsuario(
            @PathVariable Integer usuarioId,
            @PathVariable Integer fotoId) {
        try {
            boolean deleted = fotoUsuarioService.deleteFotoUsuario(usuarioId, fotoId);
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
    @Operation(summary = "Listar relacionamentos foto-usuário ativos")
    public ResponseEntity<FotoUsuarioListDTO> getActiveFotosUsuario() {
        try {
            FotoUsuarioListDTO activeFotosUsuario = fotoUsuarioService.getActiveFotosUsuario();
            return ResponseEntity.ok(activeFotosUsuario);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/usuario/{usuarioId}")
    @Operation(summary = "Buscar fotos por ID do usuário")
    public ResponseEntity<FotoUsuarioListDTO> findByUsuarioId(@PathVariable Integer usuarioId) {
        try {
            FotoUsuarioListDTO fotosUsuario = fotoUsuarioService.findByUsuarioId(usuarioId);
            return ResponseEntity.ok(fotosUsuario);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/foto/{fotoId}")
    @Operation(summary = "Buscar usuários por ID da foto")
    public ResponseEntity<FotoUsuarioListDTO> findByFotoId(@PathVariable Integer fotoId) {
        try {
            FotoUsuarioListDTO fotosUsuario = fotoUsuarioService.findByFotoId(fotoId);
            return ResponseEntity.ok(fotosUsuario);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

