package com.AchadosPerdidos.API.Presentation.Controller;

import com.AchadosPerdidos.API.Application.DTOs.Estado.EstadoDTO;
import com.AchadosPerdidos.API.Application.DTOs.Estado.EstadoCreateDTO;
import com.AchadosPerdidos.API.Application.DTOs.Estado.EstadoUpdateDTO;
import com.AchadosPerdidos.API.Application.Services.Interfaces.IEstadoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller para gerenciamento de estados
 * Responsabilidade: Camada de apresentação - recebe requisições HTTP e delega para os services
 */
@RestController
@RequestMapping("/api/estados")
@Tag(name = "Estados", description = "API para gerenciamento de estados")
public class EstadoController {

    @Autowired
    private IEstadoService estadoService;
    
    @GetMapping
    @Operation(summary = "Listar todos os estados")
    public ResponseEntity<List<EstadoDTO>> getAllEstados() {
        return ResponseEntity.ok(estadoService.getAllEstados());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar estado por ID")
    public ResponseEntity<EstadoDTO> getEstadoById(@PathVariable Integer id) {
        return ResponseEntity.ok(estadoService.getEstadoById(id));
    }

    @GetMapping("/uf/{uf}")
    @Operation(summary = "Buscar estado por UF")
    public ResponseEntity<EstadoDTO> getEstadoByUf(
            @Parameter(description = "Sigla UF do estado") @PathVariable String uf) {
        return ResponseEntity.ok(estadoService.getEstadoByUf(uf));
    }

    @PostMapping
    @Operation(summary = "Criar novo estado")
    public ResponseEntity<EstadoDTO> createEstado(@RequestBody EstadoCreateDTO estadoCreateDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(estadoService.createEstado(estadoCreateDTO));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar estado")
    public ResponseEntity<EstadoDTO> updateEstado(
            @Parameter(description = "ID do estado") @PathVariable Integer id,
            @RequestBody EstadoUpdateDTO estadoUpdateDTO) {
        return ResponseEntity.ok(estadoService.updateEstado(id, estadoUpdateDTO));
    }

    @PostMapping("/{id}/delete")
    @Operation(summary = "Inativar estado (soft delete)")
    public ResponseEntity<EstadoDTO> deleteEstado(@PathVariable Integer id) {
        return ResponseEntity.ok(estadoService.deleteEstado(id));
    }
}

