package com.AchadosPerdidos.API.Presentation.Controller;

import com.AchadosPerdidos.API.Application.DTOs.Cidade.CidadeDTO;
import com.AchadosPerdidos.API.Application.DTOs.Cidade.CidadeCreateDTO;
import com.AchadosPerdidos.API.Application.DTOs.Cidade.CidadeUpdateDTO;
import com.AchadosPerdidos.API.Application.Services.Interfaces.ICidadeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller para gerenciamento de cidades
 * Responsabilidade: Camada de apresentação - recebe requisições HTTP e delega para os services
 */
@RestController
@RequestMapping("/api/cidades")
@Tag(name = "Cidades", description = "API para gerenciamento de cidades")
public class CidadeController {

    @Autowired
    private ICidadeService cidadeService;
    
    @GetMapping
    @Operation(summary = "Listar todas as cidades")
    public ResponseEntity<List<CidadeDTO>> getAllCidades() {
        return ResponseEntity.ok(cidadeService.getAllCidades());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar cidade por ID")
    public ResponseEntity<CidadeDTO> getCidadeById(@PathVariable Integer id) {
        return ResponseEntity.ok(cidadeService.getCidadeById(id));
    }

    @PostMapping
    @Operation(summary = "Criar nova cidade")
    public ResponseEntity<CidadeDTO> createCidade(@RequestBody CidadeCreateDTO cidadeCreateDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cidadeService.createCidade(cidadeCreateDTO));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar cidade")
    public ResponseEntity<CidadeDTO> updateCidade(
            @Parameter(description = "ID da cidade") @PathVariable Integer id,
            @RequestBody CidadeUpdateDTO cidadeUpdateDTO) {
        return ResponseEntity.ok(cidadeService.updateCidade(id, cidadeUpdateDTO));
    }

    @PostMapping("/{id}/delete")
    @Operation(summary = "Inativar cidade (soft delete)")
    public ResponseEntity<CidadeDTO> deleteCidade(@PathVariable Integer id) {
        return ResponseEntity.ok(cidadeService.deleteCidade(id));
    }

    @GetMapping("/estado/{estadoId}")
    @Operation(summary = "Listar cidades por estado")
    public ResponseEntity<List<CidadeDTO>> getCidadesByEstado(
            @Parameter(description = "ID do estado") @PathVariable Integer estadoId) {
        return ResponseEntity.ok(cidadeService.getCidadesByEstado(estadoId));
    }
}

