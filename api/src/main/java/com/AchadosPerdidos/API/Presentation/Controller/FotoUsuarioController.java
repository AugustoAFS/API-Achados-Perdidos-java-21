package com.AchadosPerdidos.API.Presentation.Controller;

import com.AchadosPerdidos.API.Application.DTOs.FotoUsuario.FotoUsuarioListDTO;
import com.AchadosPerdidos.API.Application.Services.Interfaces.IFotoUsuarioService;
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

}

