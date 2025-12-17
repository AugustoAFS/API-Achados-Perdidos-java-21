package com.AchadosPerdidos.API.Presentation.Controller;

import com.AchadosPerdidos.API.Application.DTOs.Role.RoleDTO;
import com.AchadosPerdidos.API.Application.Services.Interfaces.IRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller para gerenciamento de roles/permissões
 * Responsabilidade: Camada de apresentação - recebe requisições HTTP e delega para os services
 */
@RestController
@RequestMapping("/api/roles")
@Tag(name = "Roles", description = "API para gerenciamento de roles/permissões")
public class RoleController {

    @Autowired
    private IRoleService roleService;
    
    @GetMapping
    @Operation(summary = "Listar todas as roles")
    public ResponseEntity<List<RoleDTO>> getAllRoles() {
        return ResponseEntity.ok(roleService.getAllRoles());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar role por ID")
    public ResponseEntity<RoleDTO> getRoleById(@PathVariable Integer id) {
        return ResponseEntity.ok(roleService.getRoleById(id));
    }

    @GetMapping("/active")
    @Operation(summary = "Listar roles ativas")
    public ResponseEntity<List<RoleDTO>> getActiveRoles() {
        return ResponseEntity.ok(roleService.getActiveRoles());
    }

    @GetMapping("/nome/{nome}")
    @Operation(summary = "Buscar role por nome", description = "Retorna uma role específica pelo seu nome (ex: ALUNO, SERVIDOR, USER)")
    public ResponseEntity<RoleDTO> getRoleByNome(
            @Parameter(description = "Nome da role") @PathVariable String nome) {
        return ResponseEntity.ok(roleService.getRoleByNome(nome));
    }
}
