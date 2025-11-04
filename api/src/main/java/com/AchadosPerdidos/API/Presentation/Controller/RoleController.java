package com.AchadosPerdidos.API.Presentation.Controller;

import com.AchadosPerdidos.API.Application.DTOs.Role.RoleDTO;
import com.AchadosPerdidos.API.Application.Services.Interfaces.IRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@CrossOrigin(origins = "*")
@Tag(name = "Roles", description = "API para gerenciamento de roles/permissões")
public class RoleController {

    @Autowired
    private IRoleService roleService;
    
    @GetMapping
    @Operation(summary = "Listar todas as roles")
    public ResponseEntity<List<RoleDTO>> getAllRoles() {
        List<RoleDTO> roles = roleService.getAllRoles();
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar role por ID")
    public ResponseEntity<RoleDTO> getRoleById(@PathVariable Integer id) {
        RoleDTO role = roleService.getRoleById(id);
        if (role == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(role);
    }

    @GetMapping("/active")
    @Operation(summary = "Listar roles ativas")
    public ResponseEntity<List<RoleDTO>> getActiveRoles() {
        List<RoleDTO> activeRoles = roleService.getActiveRoles();
        return ResponseEntity.ok(activeRoles);
    }
}
