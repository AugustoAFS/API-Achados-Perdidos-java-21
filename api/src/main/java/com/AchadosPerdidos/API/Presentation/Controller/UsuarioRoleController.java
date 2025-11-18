package com.AchadosPerdidos.API.Presentation.Controller;

import com.AchadosPerdidos.API.Application.DTOs.UsuarioRole.UsuarioRoleCreateDTO;
import com.AchadosPerdidos.API.Application.DTOs.UsuarioRole.UsuarioRoleDTO;
import com.AchadosPerdidos.API.Application.DTOs.UsuarioRole.UsuarioRoleListDTO;
import com.AchadosPerdidos.API.Application.DTOs.UsuarioRole.UsuarioRoleUpdateDTO;
import com.AchadosPerdidos.API.Application.Services.Interfaces.IUsuarioRoleService;
import com.AchadosPerdidos.API.Exeptions.BusinessException;
import com.AchadosPerdidos.API.Exeptions.ResourceNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuario-roles")
@Tag(name = "Usuário Roles", description = "API para gerenciamento de relacionamento entre usuários e roles")
public class UsuarioRoleController {

    @Autowired
    private IUsuarioRoleService usuarioRoleService;

    @GetMapping
    @Operation(summary = "Listar todos os relacionamentos usuário-role")
    public ResponseEntity<UsuarioRoleListDTO> getAllUsuarioRoles() {
        try {
            UsuarioRoleListDTO usuarioRoles = usuarioRoleService.getAllUsuarioRoles();
            return ResponseEntity.ok(usuarioRoles);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/usuario/{usuarioId}/role/{roleId}")
    @Operation(summary = "Buscar relacionamento usuário-role por IDs")
    public ResponseEntity<UsuarioRoleDTO> getUsuarioRoleByUsuarioIdAndRoleId(
            @PathVariable Integer usuarioId,
            @PathVariable Integer roleId) {
        try {
            UsuarioRoleDTO usuarioRole = usuarioRoleService.getUsuarioRoleByUsuarioIdAndRoleId(usuarioId, roleId);
            return ResponseEntity.ok(usuarioRole);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    @Operation(summary = "Criar novo relacionamento usuário-role")
    public ResponseEntity<UsuarioRoleDTO> createUsuarioRole(@RequestBody UsuarioRoleCreateDTO createDTO) {
        try {
            UsuarioRoleDTO createdUsuarioRole = usuarioRoleService.createUsuarioRole(createDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUsuarioRole);
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

    @PutMapping("/usuario/{usuarioId}/role/{roleId}")
    @Operation(summary = "Atualizar relacionamento usuário-role")
    public ResponseEntity<UsuarioRoleDTO> updateUsuarioRole(
            @PathVariable Integer usuarioId,
            @PathVariable Integer roleId,
            @RequestBody UsuarioRoleUpdateDTO updateDTO) {
        try {
            UsuarioRoleDTO updatedUsuarioRole = usuarioRoleService.updateUsuarioRole(usuarioId, roleId, updateDTO);
            return ResponseEntity.ok(updatedUsuarioRole);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (BusinessException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/usuario/{usuarioId}/role/{roleId}")
    @Operation(summary = "Deletar relacionamento usuário-role")
    public ResponseEntity<Void> deleteUsuarioRole(
            @PathVariable Integer usuarioId,
            @PathVariable Integer roleId) {
        try {
            boolean deleted = usuarioRoleService.deleteUsuarioRole(usuarioId, roleId);
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
    @Operation(summary = "Listar relacionamentos usuário-role ativos")
    public ResponseEntity<UsuarioRoleListDTO> getActiveUsuarioRoles() {
        try {
            UsuarioRoleListDTO activeUsuarioRoles = usuarioRoleService.getActiveUsuarioRoles();
            return ResponseEntity.ok(activeUsuarioRoles);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/usuario/{usuarioId}")
    @Operation(summary = "Buscar roles por ID do usuário")
    public ResponseEntity<UsuarioRoleListDTO> findByUsuarioId(@PathVariable Integer usuarioId) {
        try {
            UsuarioRoleListDTO usuarioRoles = usuarioRoleService.findByUsuarioId(usuarioId);
            return ResponseEntity.ok(usuarioRoles);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/role/{roleId}")
    @Operation(summary = "Buscar usuários por ID da role")
    public ResponseEntity<UsuarioRoleListDTO> findByRoleId(@PathVariable Integer roleId) {
        try {
            UsuarioRoleListDTO usuarioRoles = usuarioRoleService.findByRoleId(roleId);
            return ResponseEntity.ok(usuarioRoles);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

