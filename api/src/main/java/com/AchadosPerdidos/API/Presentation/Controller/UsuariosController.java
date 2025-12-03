package com.AchadosPerdidos.API.Presentation.Controller;

import com.AchadosPerdidos.API.Application.DTOs.Auth.LoginRequestDTO;
import com.AchadosPerdidos.API.Application.DTOs.Auth.TokenResponseDTO;
import com.AchadosPerdidos.API.Application.DTOs.Auth.RedefinirSenhaDTO;
import com.AchadosPerdidos.API.Application.DTOs.Usuario.UsuariosCreateDTO;
import com.AchadosPerdidos.API.Application.DTOs.Usuario.AlunoCreateDTO;
import com.AchadosPerdidos.API.Application.DTOs.Usuario.ServidorCreateDTO;
import com.AchadosPerdidos.API.Application.DTOs.Usuario.UsuariosListDTO;
import com.AchadosPerdidos.API.Application.DTOs.Usuario.UsuariosUpdateDTO;
import com.AchadosPerdidos.API.Application.Services.Interfaces.IUsuariosService;
import com.AchadosPerdidos.API.Exeptions.BusinessException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
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

    @PostMapping("/login")
    @Operation(summary = "Login de usuário", description = "Autentica um usuário com email e senha, retornando um token JWT")
    public ResponseEntity<TokenResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        try {
            TokenResponseDTO response = usuariosService.login(loginRequestDTO);
            return ResponseEntity.ok(response);
        } catch (BusinessException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping
    @Operation(summary = "Criar novo usuário", description = "Cria um novo usuário no sistema. A instituição será preenchida automaticamente baseada no campus selecionado.")
    public ResponseEntity<UsuariosCreateDTO> createUsuario(@RequestBody UsuariosCreateDTO usuariosCreateDTO) {
        UsuariosCreateDTO createdUsuario = usuariosService.createUsuario(usuariosCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUsuario);
    }

    @PostMapping("/aluno")
    @Operation(summary = "Criar novo aluno", description = "Cria um novo usuário do tipo ALUNO. A matrícula é obrigatória. Não inclui CPF. A instituição será preenchida automaticamente baseada no campus selecionado.")
    public ResponseEntity<UsuariosCreateDTO> createAluno(@RequestBody AlunoCreateDTO alunoDTO) {
        UsuariosCreateDTO createdAluno = usuariosService.createAluno(alunoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAluno);
    }

    @PostMapping("/servidor")
    @Operation(summary = "Criar novo servidor", description = "Cria um novo usuário do tipo SERVIDOR. O CPF é obrigatório e deve ter 11 dígitos. Não inclui matrícula. A instituição será preenchida automaticamente baseada no campus selecionado.")
    public ResponseEntity<UsuariosCreateDTO> createServidor(@RequestBody ServidorCreateDTO servidorDTO) {
        UsuariosCreateDTO createdServidor = usuariosService.createServidor(servidorDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdServidor);
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

    @PostMapping("/redefinir-senha")
    @Operation(summary = "Redefinir senha do usuário", description = "Redefine a senha de um usuário usando CPF ou matrícula, gerando um novo hash BCrypt. É necessário fornecer pelo menos um dos campos: CPF ou matrícula.")
    public ResponseEntity<String> redefinirSenha(@RequestBody RedefinirSenhaDTO redefinirSenhaDTO) {
        try {
            boolean sucesso = usuariosService.redefinirSenha(
                redefinirSenhaDTO.getCpf_Usuario() != null ? redefinirSenhaDTO.getCpf_Usuario() : "",
                redefinirSenhaDTO.getMatricula() != null ? redefinirSenhaDTO.getMatricula() : "",
                redefinirSenhaDTO.getNova_Senha() != null ? redefinirSenhaDTO.getNova_Senha() : ""
            );
            if (sucesso) {
                return ResponseEntity.ok("Senha redefinida com sucesso");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao redefinir senha");
            }
        } catch (BusinessException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
