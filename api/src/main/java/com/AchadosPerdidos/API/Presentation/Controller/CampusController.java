package com.AchadosPerdidos.API.Presentation.Controller;

import com.AchadosPerdidos.API.Application.DTOs.Campus.CampusDTO;
import com.AchadosPerdidos.API.Application.DTOs.Campus.CampusListDTO;
import com.AchadosPerdidos.API.Application.DTOs.Campus.CampusCreateDTO;
import com.AchadosPerdidos.API.Application.DTOs.Campus.CampusUpdateDTO;
import com.AchadosPerdidos.API.Application.Services.Interfaces.ICampusService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/campus")
@CrossOrigin(origins = "*")
@Tag(name = "Campus", description = "API para gerenciamento de campus")
public class CampusController {

    @Autowired
    private ICampusService campusService;

    @GetMapping
    public ResponseEntity<CampusListDTO> getAllCampus() {
        CampusListDTO campus = campusService.getAllCampus();
        return ResponseEntity.ok(campus);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CampusDTO> getCampusById(@PathVariable int id) {
        CampusDTO campus = campusService.getCampusById(id);
        if (campus != null) {
            return ResponseEntity.ok(campus);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<CampusDTO> createCampus(@RequestBody CampusCreateDTO campusCreateDTO) {
        CampusDTO createdCampus = campusService.createCampusFromDTO(campusCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCampus);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CampusDTO> updateCampus(@PathVariable int id, @RequestBody CampusUpdateDTO campusUpdateDTO) {
        CampusDTO updatedCampus = campusService.updateCampusFromDTO(id, campusUpdateDTO);
        if (updatedCampus != null) {
            return ResponseEntity.ok(updatedCampus);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCampus(@PathVariable int id) {
        boolean deleted = campusService.deleteCampus(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/active")
    public ResponseEntity<CampusListDTO> getActiveCampus() {
        CampusListDTO activeCampus = campusService.getActiveCampus();
        return ResponseEntity.ok(activeCampus);
    }

    @GetMapping("/institution/{institutionId}")
    public ResponseEntity<CampusListDTO> getCampusByInstitution(@PathVariable int institutionId) {
        CampusListDTO campus = campusService.getCampusByInstitution(institutionId);
        return ResponseEntity.ok(campus);
    }
}
