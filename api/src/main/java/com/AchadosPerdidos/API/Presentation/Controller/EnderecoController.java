package com.AchadosPerdidos.API.Presentation.Controller;

import com.AchadosPerdidos.API.Application.DTOs.Endereco.EnderecoDTO;
import com.AchadosPerdidos.API.Application.DTOs.Endereco.EnderecoCreateDTO;
import com.AchadosPerdidos.API.Application.DTOs.Endereco.EnderecoUpdateDTO;
import com.AchadosPerdidos.API.Application.Services.Interfaces.IEnderecoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller para gerenciamento de endereços
 * Responsabilidade: Camada de apresentação - recebe requisições HTTP e delega para os services
 */
@RestController
@RequestMapping("/api/enderecos")
@Tag(name = "Endereços", description = "API para gerenciamento de endereços")
public class EnderecoController {

    @Autowired
    private IEnderecoService enderecoService;
    
    @GetMapping
    @Operation(summary = "Listar todos os endereços")
    public ResponseEntity<List<EnderecoDTO>> getAllEnderecos() {
        return ResponseEntity.ok(enderecoService.getAllEnderecos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar endereço por ID")
    public ResponseEntity<EnderecoDTO> getEnderecoById(@PathVariable Integer id) {
        return ResponseEntity.ok(enderecoService.getEnderecoById(id));
    }

    @PostMapping
    @Operation(summary = "Criar novo endereço")
    public ResponseEntity<EnderecoDTO> createEndereco(@RequestBody EnderecoCreateDTO enderecoCreateDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(enderecoService.createEndereco(enderecoCreateDTO));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar endereço")
    public ResponseEntity<EnderecoDTO> updateEndereco(
            @Parameter(description = "ID do endereço") @PathVariable Integer id,
            @RequestBody EnderecoUpdateDTO enderecoUpdateDTO) {
        return ResponseEntity.ok(enderecoService.updateEndereco(id, enderecoUpdateDTO));
    }

    @PostMapping("/{id}/delete")
    @Operation(summary = "Inativar endereço (soft delete)")
    public ResponseEntity<EnderecoDTO> deleteEndereco(@PathVariable Integer id) {
        return ResponseEntity.ok(enderecoService.deleteEndereco(id));
    }

    @GetMapping("/cidade/{cidadeId}")
    @Operation(summary = "Listar endereços por cidade")
    public ResponseEntity<List<EnderecoDTO>> getEnderecosByCidade(
            @Parameter(description = "ID da cidade") @PathVariable Integer cidadeId) {
        return ResponseEntity.ok(enderecoService.getEnderecosByCidade(cidadeId));
    }
}

