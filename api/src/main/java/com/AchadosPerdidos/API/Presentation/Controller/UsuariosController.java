package com.AchadosPerdidos.API.Presentation.Controller;

import com.AchadosPerdidos.API.Application.DTOs.Usuario.UsuariosCreateDTO;
import com.AchadosPerdidos.API.Application.DTOs.Usuario.UsuariosListDTO;
import com.AchadosPerdidos.API.Application.DTOs.Usuario.UsuariosUpdateDTO;
import com.AchadosPerdidos.API.Application.Services.Interfaces.IUsuariosService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
@Tag(name = "Usuários", description = "Rota para gerenciamento de usuários")
public class UsuariosController {

    @Autowired
    private IUsuariosService usuariosService;

    @GetMapping
    @Operation(summary = "Listar todos os usuários", description = "Retorna uma lista de todos os usuários cadastrados")
    public ResponseEntity<UsuariosListDTO> getAllUsuarios() {
        UsuariosListDTO usuarios = usuariosService.getAllUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuário por ID", description = "Retorna um usuário específico pelo seu ID")
    public ResponseEntity<UsuariosListDTO> getUsuarioById(@PathVariable int id) {
        UsuariosListDTO usuario = usuariosService.getUsuarioById(id);
        if (usuario != null) {
            return ResponseEntity.ok(usuario);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Criar novo usuário", description = "Cria um novo usuário no sistema. A instituição será preenchida automaticamente baseada no campus selecionado.")
    public ResponseEntity<UsuariosCreateDTO> createUsuario(@RequestBody UsuariosCreateDTO usuariosCreateDTO) {
        UsuariosCreateDTO createdUsuario = usuariosService.createUsuario(usuariosCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUsuario);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar usuário", description = "Atualiza os dados de um usuário existente")
    public ResponseEntity<UsuariosUpdateDTO> updateUsuario(
            @Parameter(description = "ID do usuário a ser atualizado") @PathVariable int id, 
            @RequestBody UsuariosUpdateDTO usuariosUpdateDTO) {
        UsuariosUpdateDTO updatedUsuario = usuariosService.updateUsuario(id, usuariosUpdateDTO);
        if (updatedUsuario != null) {
            return ResponseEntity.ok(updatedUsuario);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar usuário", description = "Remove um usuário do sistema")
    public ResponseEntity<Void> deleteUsuario(@PathVariable int id) {
        boolean deleted = usuariosService.deleteUsuario(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
