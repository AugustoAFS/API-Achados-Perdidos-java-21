package com.AchadosPerdidos.API.Presentation.Controller;

import com.AchadosPerdidos.API.Application.DTOs.DeviceToken.DeviceTokenCreateDTO;
import com.AchadosPerdidos.API.Application.DTOs.DeviceToken.DeviceTokenDTO;
import com.AchadosPerdidos.API.Application.DTOs.DeviceToken.DeviceTokenListDTO;
import com.AchadosPerdidos.API.Application.DTOs.DeviceToken.DeviceTokenUpdateDTO;
import com.AchadosPerdidos.API.Application.Services.Interfaces.IDeviceTokenService;
import com.AchadosPerdidos.API.Application.Services.Interfaces.IJWTService;
import com.AchadosPerdidos.API.Exeptions.BusinessException;
import com.AchadosPerdidos.API.Exeptions.ResourceNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/device-tokens")
@Tag(name = "Device Tokens", description = "API para gerenciamento de tokens de dispositivos para push notifications")
public class DeviceTokenController {

    @Autowired
    private IDeviceTokenService deviceTokenService;

    @Autowired
    private IJWTService jwtService;

    @GetMapping
    @Operation(summary = "Listar todos os tokens de dispositivos")
    public ResponseEntity<DeviceTokenListDTO> getAllDeviceTokens() {
        try {
            DeviceTokenListDTO deviceTokens = deviceTokenService.getAllDeviceTokens();
            return ResponseEntity.ok(deviceTokens);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar token de dispositivo por ID")
    public ResponseEntity<DeviceTokenDTO> getDeviceTokenById(@PathVariable Integer id) {
        try {
            DeviceTokenDTO deviceToken = deviceTokenService.getDeviceTokenById(id);
            return ResponseEntity.ok(deviceToken);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/usuario/{usuarioId}")
    @Operation(summary = "Buscar tokens de dispositivos por ID do usuário")
    public ResponseEntity<DeviceTokenListDTO> getDeviceTokensByUsuarioId(@PathVariable Integer usuarioId) {
        try {
            DeviceTokenListDTO deviceTokens = deviceTokenService.getDeviceTokensByUsuarioId(usuarioId);
            return ResponseEntity.ok(deviceTokens);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/usuario/{usuarioId}/active")
    @Operation(summary = "Buscar tokens ativos de dispositivos por ID do usuário")
    public ResponseEntity<DeviceTokenListDTO> getActiveDeviceTokensByUsuarioId(@PathVariable Integer usuarioId) {
        try {
            DeviceTokenListDTO deviceTokens = deviceTokenService.getActiveDeviceTokensByUsuarioId(usuarioId);
            return ResponseEntity.ok(deviceTokens);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    @Operation(summary = "Criar novo token de dispositivo")
    public ResponseEntity<DeviceTokenDTO> createDeviceToken(@RequestBody DeviceTokenCreateDTO createDTO) {
        try {
            DeviceTokenDTO createdDeviceToken = deviceTokenService.createDeviceToken(createDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdDeviceToken);
        } catch (BusinessException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/register")
    @Operation(summary = "Registrar ou atualizar token de dispositivo (endpoint simplificado para app mobile)")
    public ResponseEntity<DeviceTokenDTO> registerOrUpdateDeviceToken(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @RequestParam String token,
            @RequestParam String plataforma) {
        try {
            // Valida JWT do header Authorization
            if (authorization == null || !authorization.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
            }

            String jwtToken = authorization.substring(7);
            if (!jwtService.validateToken(jwtToken) || jwtService.isTokenExpired(jwtToken)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
            }

            // Extrai userId do JWT
            String userIdStr = jwtService.getUserIdFromToken(jwtToken);
            if (userIdStr == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
            }

            Integer usuarioId = Integer.parseInt(userIdStr);
            DeviceTokenDTO deviceToken = deviceTokenService.registerOrUpdateDeviceToken(usuarioId, token, plataforma);
            return ResponseEntity.ok(deviceToken);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar token de dispositivo")
    public ResponseEntity<DeviceTokenDTO> updateDeviceToken(
            @PathVariable Integer id,
            @RequestBody DeviceTokenUpdateDTO updateDTO) {
        try {
            DeviceTokenDTO updatedDeviceToken = deviceTokenService.updateDeviceToken(id, updateDTO);
            return ResponseEntity.ok(updatedDeviceToken);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (BusinessException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar token de dispositivo")
    public ResponseEntity<Void> deleteDeviceToken(@PathVariable Integer id) {
        try {
            boolean deleted = deviceTokenService.deleteDeviceToken(id);
            if (deleted) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/active")
    @Operation(summary = "Listar todos os tokens de dispositivos ativos")
    public ResponseEntity<DeviceTokenListDTO> getActiveDeviceTokens() {
        try {
            DeviceTokenListDTO activeDeviceTokens = deviceTokenService.getActiveDeviceTokens();
            return ResponseEntity.ok(activeDeviceTokens);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

