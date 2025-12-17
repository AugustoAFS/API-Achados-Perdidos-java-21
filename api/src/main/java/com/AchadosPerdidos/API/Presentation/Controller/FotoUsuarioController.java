package com.AchadosPerdidos.API.Presentation.Controller;

import com.AchadosPerdidos.API.Application.DTOs.FotoUsuario.FotoUsuarioListDTO;
import com.AchadosPerdidos.API.Application.Services.Interfaces.IFotoUsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller para gerenciamento de relacionamento foto-usuário
 * Responsabilidade: Camada de apresentação - recebe requisições HTTP e delega para os services
 */
@RestController
@RequestMapping("/api/fotos-usuario")
@Tag(name = "Fotos Usuário", description = "API para gerenciamento de relacionamento entre fotos e usuários")
public class FotoUsuarioController {

    @Autowired
    private IFotoUsuarioService fotoUsuarioService;

    @GetMapping
    @Operation(summary = "Listar todos os relacionamentos foto-usuário")
    public ResponseEntity<FotoUsuarioListDTO> getAllFotosUsuario() {
        return ResponseEntity.ok(fotoUsuarioService.getAllFotosUsuario());
    }

    @GetMapping("/active")
    @Operation(summary = "Listar relacionamentos foto-usuário ativos")
    public ResponseEntity<FotoUsuarioListDTO> getActiveFotosUsuario() {
        return ResponseEntity.ok(fotoUsuarioService.getActiveFotosUsuario());
    }
}

