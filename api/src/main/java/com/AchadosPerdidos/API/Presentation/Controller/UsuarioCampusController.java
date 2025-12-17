package com.AchadosPerdidos.API.Presentation.Controller;

import com.AchadosPerdidos.API.Application.DTOs.UsuarioCampus.UsuarioCampusCreateDTO;
import com.AchadosPerdidos.API.Application.DTOs.UsuarioCampus.UsuarioCampusDTO;
import com.AchadosPerdidos.API.Application.DTOs.UsuarioCampus.UsuarioCampusListDTO;
import com.AchadosPerdidos.API.Application.DTOs.UsuarioCampus.UsuarioCampusUpdateDTO;
import com.AchadosPerdidos.API.Application.Services.Interfaces.IUsuarioCampusService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller para gerenciamento de relacionamento usuário-campus
 * Responsabilidade: Camada de apresentação - recebe requisições HTTP e delega para os services
 */
@RestController
@RequestMapping("/api/usuario-campus")
@Tag(name = "Usuário Campus", description = "API para gerenciamento de relacionamento entre usuários e campus")
public class UsuarioCampusController {

    @Autowired
    private IUsuarioCampusService usuarioCampusService;

    @GetMapping
    @Operation(summary = "Listar todos os relacionamentos usuário-campus")
    public ResponseEntity<List<UsuarioCampusListDTO>> getAllUsuarioCampus() {
        return ResponseEntity.ok(usuarioCampusService.getAllUsuarioCampus());
    }

    @PostMapping
    @Operation(summary = "Criar novo relacionamento usuário-campus")
    public ResponseEntity<UsuarioCampusDTO> createUsuarioCampus(@RequestBody UsuarioCampusCreateDTO createDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCampusService.createUsuarioCampus(createDTO));
    }

    @PutMapping("/usuario/{usuarioId}/campus/{campusId}")
    @Operation(summary = "Atualizar relacionamento usuário-campus")
    public ResponseEntity<UsuarioCampusDTO> updateUsuarioCampus(
            @Parameter(description = "ID do usuário") @PathVariable Integer usuarioId,
            @Parameter(description = "ID do campus") @PathVariable Integer campusId,
            @RequestBody UsuarioCampusUpdateDTO updateDTO) {
        return ResponseEntity.ok(usuarioCampusService.updateUsuarioCampus(usuarioId, campusId, updateDTO));
    }

    @DeleteMapping("/usuario/{usuarioId}/campus/{campusId}")
    @Operation(summary = "Deletar relacionamento usuário-campus")
    public ResponseEntity<Void> deleteUsuarioCampus(
            @Parameter(description = "ID do usuário") @PathVariable Integer usuarioId,
            @Parameter(description = "ID do campus") @PathVariable Integer campusId) {
        usuarioCampusService.deleteUsuarioCampus(usuarioId, campusId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/active")
    @Operation(summary = "Listar relacionamentos usuário-campus ativos")
    public ResponseEntity<UsuarioCampusListDTO> getActiveUsuarioCampus() {
        return ResponseEntity.ok(usuarioCampusService.getActiveUsuarioCampus());
    }
}
