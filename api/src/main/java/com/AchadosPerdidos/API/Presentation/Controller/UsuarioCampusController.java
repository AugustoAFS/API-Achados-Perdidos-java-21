package com.AchadosPerdidos.API.Presentation.Controller;

import com.AchadosPerdidos.API.Application.DTOs.UsuarioCampus.UsuarioCampusCreateDTO;
import com.AchadosPerdidos.API.Application.DTOs.UsuarioCampus.UsuarioCampusDTO;
import com.AchadosPerdidos.API.Application.DTOs.UsuarioCampus.UsuarioCampusListDTO;
import com.AchadosPerdidos.API.Application.DTOs.UsuarioCampus.UsuarioCampusUpdateDTO;
import com.AchadosPerdidos.API.Application.Services.Interfaces.IUsuarioCampusService;
import com.AchadosPerdidos.API.Exeptions.BusinessException;
import com.AchadosPerdidos.API.Exeptions.ResourceNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuario-campus")
@Tag(name = "Usuário Campus", description = "API para gerenciamento de relacionamento entre usuários e campus")
public class UsuarioCampusController {

    @Autowired
    private IUsuarioCampusService usuarioCampusService;

    @GetMapping
    @Operation(summary = "Listar todos os relacionamentos usuário-campus")
    public ResponseEntity<UsuarioCampusListDTO> getAllUsuarioCampus() {
        try {
            UsuarioCampusListDTO usuarioCampus = usuarioCampusService.getAllUsuarioCampus();
            return ResponseEntity.ok(usuarioCampus);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/usuario/{usuarioId}/campus/{campusId}")
    @Operation(summary = "Buscar relacionamento usuário-campus por IDs")
    public ResponseEntity<UsuarioCampusDTO> getUsuarioCampusByUsuarioIdAndCampusId(
            @PathVariable Integer usuarioId,
            @PathVariable Integer campusId) {
        try {
            UsuarioCampusDTO usuarioCampus = usuarioCampusService.getUsuarioCampusByUsuarioIdAndCampusId(usuarioId, campusId);
            return ResponseEntity.ok(usuarioCampus);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    @Operation(summary = "Criar novo relacionamento usuário-campus")
    public ResponseEntity<UsuarioCampusDTO> createUsuarioCampus(@RequestBody UsuarioCampusCreateDTO createDTO) {
        try {
            UsuarioCampusDTO createdUsuarioCampus = usuarioCampusService.createUsuarioCampus(createDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUsuarioCampus);
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

    @PutMapping("/usuario/{usuarioId}/campus/{campusId}")
    @Operation(summary = "Atualizar relacionamento usuário-campus")
    public ResponseEntity<UsuarioCampusDTO> updateUsuarioCampus(
            @PathVariable Integer usuarioId,
            @PathVariable Integer campusId,
            @RequestBody UsuarioCampusUpdateDTO updateDTO) {
        try {
            UsuarioCampusDTO updatedUsuarioCampus = usuarioCampusService.updateUsuarioCampus(usuarioId, campusId, updateDTO);
            return ResponseEntity.ok(updatedUsuarioCampus);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (BusinessException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/usuario/{usuarioId}/campus/{campusId}")
    @Operation(summary = "Deletar relacionamento usuário-campus")
    public ResponseEntity<Void> deleteUsuarioCampus(
            @PathVariable Integer usuarioId,
            @PathVariable Integer campusId) {
        try {
            boolean deleted = usuarioCampusService.deleteUsuarioCampus(usuarioId, campusId);
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
    @Operation(summary = "Listar relacionamentos usuário-campus ativos")
    public ResponseEntity<UsuarioCampusListDTO> getActiveUsuarioCampus() {
        try {
            UsuarioCampusListDTO activeUsuarioCampus = usuarioCampusService.getActiveUsuarioCampus();
            return ResponseEntity.ok(activeUsuarioCampus);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/usuario/{usuarioId}")
    @Operation(summary = "Buscar campus por ID do usuário")
    public ResponseEntity<UsuarioCampusListDTO> findByUsuarioId(@PathVariable Integer usuarioId) {
        try {
            UsuarioCampusListDTO usuarioCampus = usuarioCampusService.findByUsuarioId(usuarioId);
            return ResponseEntity.ok(usuarioCampus);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/campus/{campusId}")
    @Operation(summary = "Buscar usuários por ID do campus")
    public ResponseEntity<UsuarioCampusListDTO> findByCampusId(@PathVariable Integer campusId) {
        try {
            UsuarioCampusListDTO usuarioCampus = usuarioCampusService.findByCampusId(campusId);
            return ResponseEntity.ok(usuarioCampus);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

