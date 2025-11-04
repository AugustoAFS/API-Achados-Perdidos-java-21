package com.AchadosPerdidos.API.Presentation.Controller;

import com.AchadosPerdidos.API.Application.DTOs.Cidade.CidadeDTO;
import com.AchadosPerdidos.API.Application.DTOs.Cidade.CidadeCreateDTO;
import com.AchadosPerdidos.API.Application.DTOs.Cidade.CidadeUpdateDTO;
import com.AchadosPerdidos.API.Application.Services.Interfaces.ICidadeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cidades")
@CrossOrigin(origins = "*")
@Tag(name = "Cidades", description = "API para gerenciamento de cidades")
public class CidadeController {

    @Autowired
    private ICidadeService cidadeService;
    
    @GetMapping
    @Operation(summary = "Listar todas as cidades")
    public ResponseEntity<List<CidadeDTO>> getAllCidades() {
        List<CidadeDTO> cidades = cidadeService.getAllCidades();
        return ResponseEntity.ok(cidades);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar cidade por ID")
    public ResponseEntity<CidadeDTO> getCidadeById(@PathVariable Integer id) {
        CidadeDTO cidade = cidadeService.getCidadeById(id);
        if (cidade == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cidade);
    }

    @PostMapping
    @Operation(summary = "Criar nova cidade")
    public ResponseEntity<CidadeDTO> createCidade(@RequestBody CidadeCreateDTO cidadeCreateDTO) {
        CidadeDTO createdCidade = cidadeService.createCidade(cidadeCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCidade);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar cidade")
    public ResponseEntity<CidadeDTO> updateCidade(@PathVariable Integer id, @RequestBody CidadeUpdateDTO cidadeUpdateDTO) {
        CidadeDTO updatedCidade = cidadeService.updateCidade(id, cidadeUpdateDTO);
        if (updatedCidade == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedCidade);
    }

    @PostMapping("/{id}/delete")
    @Operation(summary = "Inativar cidade (soft delete)")
    public ResponseEntity<CidadeDTO> deleteCidade(@PathVariable Integer id) {
        CidadeDTO deletedCidade = cidadeService.deleteCidade(id);
        return ResponseEntity.ok(deletedCidade);
    }

    @GetMapping("/estado/{estadoId}")
    @Operation(summary = "Listar cidades por estado")
    public ResponseEntity<List<CidadeDTO>> getCidadesByEstado(@PathVariable Integer estadoId) {
        List<CidadeDTO> cidades = cidadeService.getCidadesByEstado(estadoId);
        return ResponseEntity.ok(cidades);
    }
}

