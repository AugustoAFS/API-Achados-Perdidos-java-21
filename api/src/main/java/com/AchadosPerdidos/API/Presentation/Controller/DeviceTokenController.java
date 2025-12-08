package com.AchadosPerdidos.API.Presentation.Controller;

import com.AchadosPerdidos.API.Application.DTOs.DeviceToken.DeviceTokenCreateDTO;
import com.AchadosPerdidos.API.Application.DTOs.DeviceToken.DeviceTokenDTO;
import com.AchadosPerdidos.API.Application.DTOs.DeviceToken.DeviceTokenListDTO;
import com.AchadosPerdidos.API.Application.DTOs.DeviceToken.DeviceTokenUpdateDTO;
import com.AchadosPerdidos.API.Application.Services.Interfaces.IDeviceTokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller para gerenciamento de tokens de dispositivos
 * Responsabilidade: Camada de apresentação - recebe requisições HTTP e delega para os services
 */
@RestController
@RequestMapping("/api/device-tokens")
@Tag(name = "Device Tokens", description = "API para gerenciamento de tokens de dispositivos para push notifications")
public class DeviceTokenController {

    @Autowired
    private IDeviceTokenService deviceTokenService;

    @GetMapping
    @Operation(summary = "Listar todos os tokens de dispositivos")
    public ResponseEntity<DeviceTokenListDTO> getAllDeviceTokens() {
        return ResponseEntity.ok(deviceTokenService.getAllDeviceTokens());
    }

    @GetMapping("/usuario/{usuarioId}")
    @Operation(summary = "Buscar tokens de dispositivos por ID do usuário")
    public ResponseEntity<DeviceTokenListDTO> getDeviceTokensByUsuario(
            @Parameter(description = "ID do usuário") @PathVariable Integer usuarioId) {
        return ResponseEntity.ok(deviceTokenService.getDeviceTokensByUsuario(usuarioId));
    }

    @PostMapping
    @Operation(summary = "Criar novo token de dispositivo")
    public ResponseEntity<DeviceTokenDTO> createDeviceToken(@RequestBody DeviceTokenCreateDTO createDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(deviceTokenService.createDeviceToken(createDTO));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar token de dispositivo")
    public ResponseEntity<DeviceTokenDTO> updateDeviceToken(
            @Parameter(description = "ID do token") @PathVariable Integer id,
            @RequestBody DeviceTokenUpdateDTO updateDTO) {
        return ResponseEntity.ok(deviceTokenService.updateDeviceToken(id, updateDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar token de dispositivo")
    public ResponseEntity<Void> deleteDeviceToken(@PathVariable Integer id) {
        deviceTokenService.deleteDeviceToken(id);
        return ResponseEntity.noContent().build();
    }
}

