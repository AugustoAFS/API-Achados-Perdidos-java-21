package com.AchadosPerdidos.API.Presentation.Controller;

import com.AchadosPerdidos.API.Application.DTOs.Reivindicacoes.ReivindicacoesDTO;
import com.AchadosPerdidos.API.Application.DTOs.Reivindicacoes.ReivindicacoesListDTO;
import com.AchadosPerdidos.API.Application.Services.Interfaces.IReivindicacoesService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reivindicacoes")
@CrossOrigin(origins = "*")
@Tag(name = "Reivindicações", description = "API para gerenciamento de reivindicações")
public class ReivindicacoesController {

    @Autowired
    private IReivindicacoesService reivindicacoesService;

    @GetMapping
    public ResponseEntity<ReivindicacoesListDTO> getAllReivindicacoes() {
        ReivindicacoesListDTO reivindicacoes = reivindicacoesService.getAllReivindicacoes();
        return ResponseEntity.ok(reivindicacoes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReivindicacoesDTO> getReivindicacaoById(@PathVariable int id) {
        ReivindicacoesDTO reivindicacao = reivindicacoesService.getReivindicacaoById(id);
        if (reivindicacao != null) {
            return ResponseEntity.ok(reivindicacao);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<ReivindicacoesDTO> createReivindicacao(@RequestBody ReivindicacoesDTO reivindicacoesDTO) {
        ReivindicacoesDTO createdReivindicacao = reivindicacoesService.createReivindicacao(reivindicacoesDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdReivindicacao);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReivindicacoesDTO> updateReivindicacao(@PathVariable int id, @RequestBody ReivindicacoesDTO reivindicacoesDTO) {
        ReivindicacoesDTO updatedReivindicacao = reivindicacoesService.updateReivindicacao(id, reivindicacoesDTO);
        if (updatedReivindicacao != null) {
            return ResponseEntity.ok(updatedReivindicacao);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReivindicacao(@PathVariable int id) {
        boolean deleted = reivindicacoesService.deleteReivindicacao(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/item/{itemId}")
    public ResponseEntity<ReivindicacoesListDTO> getReivindicacoesByItem(@PathVariable int itemId) {
        ReivindicacoesListDTO reivindicacoes = reivindicacoesService.getReivindicacoesByItem(itemId);
        return ResponseEntity.ok(reivindicacoes);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ReivindicacoesListDTO> getReivindicacoesByUser(@PathVariable int userId) {
        ReivindicacoesListDTO reivindicacoes = reivindicacoesService.getReivindicacoesByUser(userId);
        return ResponseEntity.ok(reivindicacoes);
    }

    @GetMapping("/proprietario/{proprietarioId}")
    public ResponseEntity<ReivindicacoesListDTO> getReivindicacoesByProprietario(@PathVariable int proprietarioId) {
        ReivindicacoesListDTO reivindicacoes = reivindicacoesService.getReivindicacoesByProprietario(proprietarioId);
        return ResponseEntity.ok(reivindicacoes);
    }

    @GetMapping("/item/{itemId}/user/{userId}")
    public ResponseEntity<ReivindicacoesDTO> getReivindicacaoByItemAndUser(@PathVariable int itemId, @PathVariable int userId) {
        ReivindicacoesDTO reivindicacao = reivindicacoesService.getReivindicacaoByItemAndUser(itemId, userId);
        if (reivindicacao != null) {
            return ResponseEntity.ok(reivindicacao);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
