package com.AchadosPerdidos.API.Presentation.Controller;

import com.AchadosPerdidos.API.Application.DTOs.Estado.EstadoDTO;
import com.AchadosPerdidos.API.Application.DTOs.Estado.EstadoCreateDTO;
import com.AchadosPerdidos.API.Application.DTOs.Estado.EstadoUpdateDTO;
import com.AchadosPerdidos.API.Application.Services.Interfaces.IEstadoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estados")
@CrossOrigin(origins = "*")
@Tag(name = "Estados", description = "API para gerenciamento de estados")
public class EstadoController {

    @Autowired
    private IEstadoService estadoService;
    
    @GetMapping
    @Operation(summary = "Listar todos os estados")
    public ResponseEntity<List<EstadoDTO>> getAllEstados() {
        List<EstadoDTO> estados = estadoService.getAllEstados();
        return ResponseEntity.ok(estados);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar estado por ID")
    public ResponseEntity<EstadoDTO> getEstadoById(@PathVariable Integer id) {
        EstadoDTO estado = estadoService.getEstadoById(id);
        if (estado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(estado);
    }

    @GetMapping("/uf/{uf}")
    @Operation(summary = "Buscar estado por UF")
    public ResponseEntity<EstadoDTO> getEstadoByUf(@PathVariable String uf) {
        EstadoDTO estado = estadoService.getEstadoByUf(uf);
        if (estado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(estado);
    }

    @PostMapping
    @Operation(summary = "Criar novo estado")
    public ResponseEntity<EstadoDTO> createEstado(@RequestBody EstadoCreateDTO estadoCreateDTO) {
        EstadoDTO createdEstado = estadoService.createEstado(estadoCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEstado);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar estado")
    public ResponseEntity<EstadoDTO> updateEstado(@PathVariable Integer id, @RequestBody EstadoUpdateDTO estadoUpdateDTO) {
        EstadoDTO updatedEstado = estadoService.updateEstado(id, estadoUpdateDTO);
        if (updatedEstado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedEstado);
    }

    @PostMapping("/{id}/delete")
    @Operation(summary = "Inativar estado (soft delete)")
    public ResponseEntity<EstadoDTO> deleteEstado(@PathVariable Integer id) {
        EstadoDTO deletedEstado = estadoService.deleteEstado(id);
        return ResponseEntity.ok(deletedEstado);
    }
}

