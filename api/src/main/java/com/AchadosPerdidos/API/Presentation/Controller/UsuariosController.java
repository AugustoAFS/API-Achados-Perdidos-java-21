package com.AchadosPerdidos.API.Presentation.Controller;

import com.AchadosPerdidos.API.Application.DTOs.Usuario.UsuariosCreateDTO;
import com.AchadosPerdidos.API.Application.DTOs.Usuario.AlunoCreateDTO;
import com.AchadosPerdidos.API.Application.DTOs.Usuario.ServidorCreateDTO;
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

/**
 * Controller para gerenciamento de usuários
 * Responsabilidade: Camada de apresentação - recebe requisições HTTP e delega para os services
 */
@RestController
@RequestMapping("/api/usuarios")
@Tag(name = "Usuários", description = "API para gerenciamento de usuários")
public class UsuariosController {

    @Autowired
    private IUsuariosService usuariosService;

    @GetMapping
    @Operation(summary = "Listar todos os usuários")
    public ResponseEntity<UsuariosListDTO> getAllUsuarios() {
        return ResponseEntity.ok(usuariosService.getAllUsuarios());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuário por ID")
    public ResponseEntity<UsuariosListDTO> getUsuarioById(@PathVariable int id) {
        return ResponseEntity.ok(usuariosService.getUsuarioById(id));
    }

    @PostMapping
    @Operation(summary = "Criar novo usuário")
    public ResponseEntity<UsuariosCreateDTO> createUsuario(@RequestBody UsuariosCreateDTO usuariosCreateDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuariosService.createUsuario(usuariosCreateDTO));
    }

    @PostMapping("/aluno")
    @Operation(summary = "Criar novo aluno")
    public ResponseEntity<UsuariosCreateDTO> createAluno(@RequestBody AlunoCreateDTO alunoDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuariosService.createAluno(alunoDTO));
    }

    @PostMapping("/servidor")
    @Operation(summary = "Criar novo servidor")
    public ResponseEntity<UsuariosCreateDTO> createServidor(@RequestBody ServidorCreateDTO servidorDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuariosService.createServidor(servidorDTO));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar usuário")
    public ResponseEntity<UsuariosUpdateDTO> updateUsuario(
            @Parameter(description = "ID do usuário") @PathVariable int id,
            @RequestBody UsuariosUpdateDTO usuariosUpdateDTO) {
        return ResponseEntity.ok(usuariosService.updateUsuario(id, usuariosUpdateDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar usuário")
    public ResponseEntity<Void> deleteUsuario(@PathVariable int id) {
        usuariosService.deleteUsuario(id);
        return ResponseEntity.noContent().build();
    }

}
