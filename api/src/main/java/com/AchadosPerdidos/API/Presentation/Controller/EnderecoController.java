package com.AchadosPerdidos.API.Presentation.Controller;

import com.AchadosPerdidos.API.Application.DTOs.Endereco.EnderecoDTO;
import com.AchadosPerdidos.API.Application.DTOs.Endereco.EnderecoCreateDTO;
import com.AchadosPerdidos.API.Application.DTOs.Endereco.EnderecoUpdateDTO;
import com.AchadosPerdidos.API.Application.Services.Interfaces.IEnderecoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enderecos")
@CrossOrigin(origins = "*")
@Tag(name = "Endereços", description = "API para gerenciamento de endereços")
public class EnderecoController {

    @Autowired
    private IEnderecoService enderecoService;
    
    @GetMapping
    @Operation(summary = "Listar todos os endereços")
    public ResponseEntity<List<EnderecoDTO>> getAllEnderecos() {
        List<EnderecoDTO> enderecos = enderecoService.getAllEnderecos();
        return ResponseEntity.ok(enderecos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar endereço por ID")
    public ResponseEntity<EnderecoDTO> getEnderecoById(@PathVariable Integer id) {
        EnderecoDTO endereco = enderecoService.getEnderecoById(id);
        if (endereco == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(endereco);
    }

    @PostMapping
    @Operation(summary = "Criar novo endereço")
    public ResponseEntity<EnderecoDTO> createEndereco(@RequestBody EnderecoCreateDTO enderecoCreateDTO) {
        EnderecoDTO createdEndereco = enderecoService.createEndereco(enderecoCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEndereco);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar endereço")
    public ResponseEntity<EnderecoDTO> updateEndereco(@PathVariable Integer id, @RequestBody EnderecoUpdateDTO enderecoUpdateDTO) {
        EnderecoDTO updatedEndereco = enderecoService.updateEndereco(id, enderecoUpdateDTO);
        if (updatedEndereco == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedEndereco);
    }

    @PostMapping("/{id}/delete")
    @Operation(summary = "Inativar endereço (soft delete)")
    public ResponseEntity<EnderecoDTO> deleteEndereco(@PathVariable Integer id) {
        EnderecoDTO deletedEndereco = enderecoService.deleteEndereco(id);
        return ResponseEntity.ok(deletedEndereco);
    }

    @GetMapping("/cidade/{cidadeId}")
    @Operation(summary = "Listar endereços por cidade")
    public ResponseEntity<List<EnderecoDTO>> getEnderecosByCidade(@PathVariable Integer cidadeId) {
        List<EnderecoDTO> enderecos = enderecoService.getEnderecosByCidade(cidadeId);
        return ResponseEntity.ok(enderecos);
    }
}

