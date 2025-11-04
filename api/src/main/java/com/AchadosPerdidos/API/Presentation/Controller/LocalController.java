package com.AchadosPerdidos.API.Presentation.Controller;

import com.AchadosPerdidos.API.Application.DTOs.Local.LocalDTO;
import com.AchadosPerdidos.API.Application.DTOs.Local.LocalCreateDTO;
import com.AchadosPerdidos.API.Application.DTOs.Local.LocalUpdateDTO;
import com.AchadosPerdidos.API.Application.Services.Interfaces.ILocalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/locais")
@CrossOrigin(origins = "*")
@Tag(name = "Locais", description = "API para gerenciamento de locais")
public class LocalController {

    @Autowired
    private ILocalService localService;
    
    @GetMapping
    @Operation(summary = "Listar todos os locais")
    public ResponseEntity<List<LocalDTO>> getAllLocais() {
        List<LocalDTO> locais = localService.getAllLocais();
        return ResponseEntity.ok(locais);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar local por ID")
    public ResponseEntity<LocalDTO> getLocalById(@PathVariable Integer id) {
        LocalDTO local = localService.getLocalById(id);
        if (local == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(local);
    }

    @PostMapping
    @Operation(summary = "Criar novo local")
    public ResponseEntity<LocalDTO> createLocal(@RequestBody LocalCreateDTO localCreateDTO) {
        LocalDTO createdLocal = localService.createLocal(localCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdLocal);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar local")
    public ResponseEntity<LocalDTO> updateLocal(@PathVariable Integer id, @RequestBody LocalUpdateDTO localUpdateDTO) {
        LocalDTO updatedLocal = localService.updateLocal(id, localUpdateDTO);
        if (updatedLocal == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedLocal);
    }

    @PostMapping("/{id}/delete")
    @Operation(summary = "Inativar local (soft delete)")
    public ResponseEntity<LocalDTO> deleteLocal(@PathVariable Integer id) {
        LocalDTO deletedLocal = localService.deleteLocal(id);
        return ResponseEntity.ok(deletedLocal);
    }

    @GetMapping("/campus/{campusId}")
    @Operation(summary = "Listar locais por campus")
    public ResponseEntity<List<LocalDTO>> getLocaisByCampus(@PathVariable Integer campusId) {
        List<LocalDTO> locais = localService.getLocaisByCampus(campusId);
        return ResponseEntity.ok(locais);
    }
}

