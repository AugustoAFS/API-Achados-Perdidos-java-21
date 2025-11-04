package com.AchadosPerdidos.API.Presentation.Controller;

import com.AchadosPerdidos.API.Application.DTOs.Instituicao.InstituicaoDTO;
import com.AchadosPerdidos.API.Application.DTOs.Instituicao.InstituicaoListDTO;
import com.AchadosPerdidos.API.Application.DTOs.Instituicao.InstituicaoCreateDTO;
import com.AchadosPerdidos.API.Application.DTOs.Instituicao.InstituicaoUpdateDTO;
import com.AchadosPerdidos.API.Application.Services.Interfaces.IInstituicaoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/instituicao")
@CrossOrigin(origins = "*")
@Tag(name = "Instituições", description = "API para gerenciamento de instituições")
public class InstituicaoController {

    @Autowired
    private IInstituicaoService instituicaoService;

    @GetMapping
    public ResponseEntity<InstituicaoListDTO> getAllInstituicoes() {
        InstituicaoListDTO instituicoes = instituicaoService.getAllInstituicoes();
        return ResponseEntity.ok(instituicoes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InstituicaoDTO> getInstituicaoById(@PathVariable int id) {
        InstituicaoDTO instituicao = instituicaoService.getInstituicaoById(id);
        if (instituicao != null) {
            return ResponseEntity.ok(instituicao);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<InstituicaoDTO> createInstituicao(@RequestBody InstituicaoCreateDTO instituicaoCreateDTO) {
        InstituicaoDTO createdInstituicao = instituicaoService.createInstituicaoFromDTO(instituicaoCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdInstituicao);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InstituicaoDTO> updateInstituicao(@PathVariable int id, @RequestBody InstituicaoUpdateDTO instituicaoUpdateDTO) {
        InstituicaoDTO updatedInstituicao = instituicaoService.updateInstituicaoFromDTO(id, instituicaoUpdateDTO);
        if (updatedInstituicao != null) {
            return ResponseEntity.ok(updatedInstituicao);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInstituicao(@PathVariable int id) {
        boolean deleted = instituicaoService.deleteInstituicao(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/active")
    public ResponseEntity<InstituicaoListDTO> getActiveInstituicoes() {
        InstituicaoListDTO activeInstituicoes = instituicaoService.getActiveInstituicoes();
        return ResponseEntity.ok(activeInstituicoes);
    }

    @GetMapping("/type/{tipoInstituicao}")
    public ResponseEntity<InstituicaoListDTO> getInstituicoesByType(@PathVariable String tipoInstituicao) {
        InstituicaoListDTO instituicoes = instituicaoService.getInstituicoesByType(tipoInstituicao);
        return ResponseEntity.ok(instituicoes);
    }
}
