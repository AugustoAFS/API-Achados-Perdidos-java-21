package com.AchadosPerdidos.API.Presentation.Controller;

import com.AchadosPerdidos.API.Application.DTOs.Instituicao.InstituicaoDTO;
import com.AchadosPerdidos.API.Application.DTOs.Instituicao.InstituicaoListDTO;
import com.AchadosPerdidos.API.Application.Services.Interfaces.IInstituicaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller para gerenciamento de instituições
 * Responsabilidade: Camada de apresentação - recebe requisições HTTP e delega para os services
 */
@RestController
@RequestMapping("/api/instituicao")
@Tag(name = "Instituições", description = "API para gerenciamento de instituições")
public class InstituicaoController {

    @Autowired
    private IInstituicaoService instituicaoService;

    @GetMapping
    @Operation(summary = "Listar todas as instituições")
    public ResponseEntity<InstituicaoListDTO> getAllInstituicoes() {
        return ResponseEntity.ok(instituicaoService.getAllInstituicoes());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar instituição por ID")
    public ResponseEntity<InstituicaoDTO> getInstituicaoById(@PathVariable int id) {
        return ResponseEntity.ok(instituicaoService.getInstituicaoById(id));
    }

    @PostMapping
    @Operation(summary = "Criar nova instituição")
    public ResponseEntity<InstituicaoDTO> createInstituicao(@RequestBody InstituicaoDTO instituicaoDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(instituicaoService.createInstituicao(instituicaoDTO));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar instituição")
    public ResponseEntity<InstituicaoDTO> updateInstituicao(
            @Parameter(description = "ID da instituição") @PathVariable int id,
            @RequestBody InstituicaoDTO instituicaoDTO) {
        return ResponseEntity.ok(instituicaoService.updateInstituicao(id, instituicaoDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar instituição")
    public ResponseEntity<Void> deleteInstituicao(@PathVariable int id) {
        instituicaoService.deleteInstituicao(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/active")
    @Operation(summary = "Listar instituições ativas")
    public ResponseEntity<InstituicaoListDTO> getActiveInstituicoes() {
        return ResponseEntity.ok(instituicaoService.getActiveInstituicoes());
    }

}
