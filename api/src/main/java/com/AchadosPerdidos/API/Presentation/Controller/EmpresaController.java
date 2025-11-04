package com.AchadosPerdidos.API.Presentation.Controller;

import com.AchadosPerdidos.API.Application.DTOs.Empresa.EmpresaDTO;
import com.AchadosPerdidos.API.Application.DTOs.Empresa.EmpresaListDTO;
import com.AchadosPerdidos.API.Application.Services.Interfaces.IEmpresaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/empresa")
@CrossOrigin(origins = "*")
@Tag(name = "Empresas", description = "API para gerenciamento de empresas")
public class EmpresaController {

    @Autowired
    private IEmpresaService empresaService;

    @GetMapping
    public ResponseEntity<EmpresaListDTO> getAllEmpresas() {
        EmpresaListDTO empresas = empresaService.getAllEmpresas();
        return ResponseEntity.ok(empresas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmpresaDTO> getEmpresaById(@PathVariable int id) {
        EmpresaDTO empresa = empresaService.getEmpresaById(id);
        if (empresa != null) {
            return ResponseEntity.ok(empresa);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<EmpresaDTO> createEmpresa(@RequestBody EmpresaDTO empresaDTO) {
        EmpresaDTO createdEmpresa = empresaService.createEmpresa(empresaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEmpresa);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmpresaDTO> updateEmpresa(@PathVariable int id, @RequestBody EmpresaDTO empresaDTO) {
        EmpresaDTO updatedEmpresa = empresaService.updateEmpresa(id, empresaDTO);
        if (updatedEmpresa != null) {
            return ResponseEntity.ok(updatedEmpresa);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmpresa(@PathVariable int id) {
        boolean deleted = empresaService.deleteEmpresa(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/active")
    public ResponseEntity<EmpresaListDTO> getActiveEmpresas() {
        EmpresaListDTO activeEmpresas = empresaService.getActiveEmpresas();
        return ResponseEntity.ok(activeEmpresas);
    }

    @GetMapping("/country/{paisSede}")
    public ResponseEntity<EmpresaListDTO> getEmpresasByCountry(@PathVariable String paisSede) {
        EmpresaListDTO empresas = empresaService.getEmpresasByCountry(paisSede);
        return ResponseEntity.ok(empresas);
    }
}
