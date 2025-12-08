package com.AchadosPerdidos.API.Presentation.Controller;

import com.AchadosPerdidos.API.Application.DTOs.Campus.CampusDTO;
import com.AchadosPerdidos.API.Application.DTOs.Campus.CampusListDTO;
import com.AchadosPerdidos.API.Application.DTOs.Campus.CampusCreateDTO;
import com.AchadosPerdidos.API.Application.DTOs.Campus.CampusUpdateDTO;
import com.AchadosPerdidos.API.Application.Services.Interfaces.ICampusService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller para gerenciamento de campus
 * Responsabilidade: Camada de apresentação - recebe requisições HTTP e delega para os services
 */
@RestController
@RequestMapping("/api/campus")
@Tag(name = "Campus", description = "API para gerenciamento de campus")
public class CampusController {

    @Autowired
    private ICampusService campusService;

    @GetMapping
    @Operation(summary = "Listar todos os campus")
    public ResponseEntity<CampusListDTO> getAllCampus() {
        return ResponseEntity.ok(campusService.getAllCampus());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar campus por ID")
    public ResponseEntity<CampusDTO> getCampusById(@PathVariable int id) {
        return ResponseEntity.ok(campusService.getCampusById(id));
    }

    @PostMapping
    @Operation(summary = "Criar novo campus")
    public ResponseEntity<CampusDTO> createCampus(@RequestBody CampusCreateDTO campusCreateDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(campusService.createCampus(campusCreateDTO));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar campus")
    public ResponseEntity<CampusDTO> updateCampus(
            @Parameter(description = "ID do campus") @PathVariable int id,
            @RequestBody CampusUpdateDTO campusUpdateDTO) {
        return ResponseEntity.ok(campusService.updateCampus(id, campusUpdateDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar campus")
    public ResponseEntity<Void> deleteCampus(@PathVariable int id) {
        campusService.deleteCampus(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/active")
    @Operation(summary = "Listar campus ativos")
    public ResponseEntity<CampusListDTO> getActiveCampus() {
        return ResponseEntity.ok(campusService.getActiveCampus());
    }

    @GetMapping("/institution/{institutionId}")
    @Operation(summary = "Listar campus por instituição")
    public ResponseEntity<CampusListDTO> getCampusByInstitution(
            @Parameter(description = "ID da instituição") @PathVariable int institutionId) {
        return ResponseEntity.ok(campusService.getCampusByInstitution(institutionId));
    }
}
