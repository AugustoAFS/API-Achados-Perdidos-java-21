package com.AchadosPerdidos.API.Presentation.Controller;

import com.AchadosPerdidos.API.Application.DTOs.Auth.LoginRequestDTO;
import com.AchadosPerdidos.API.Application.DTOs.Auth.RedefinirSenhaDTO;
import com.AchadosPerdidos.API.Application.DTOs.Auth.TokenResponseDTO;
import com.AchadosPerdidos.API.Application.DTOs.Auth.TokenValidationDTO;
import com.AchadosPerdidos.API.Application.Services.Interfaces.IGoogleAuthService;
import com.AchadosPerdidos.API.Application.Services.Interfaces.IJWTService;
import com.AchadosPerdidos.API.Application.Services.Interfaces.IUsuariosService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Auth", description = "API para autenticação e autorização")
public class AuthController {

    @Autowired
    private IUsuariosService usuariosService;

    @Autowired
    private IGoogleAuthService googleAuthService;

    @Autowired
    private IJWTService jwtService;

    @PostMapping("/login")
    @Operation(summary = "Login de usuário", description = "Autentica usuário com email e senha, retornando token JWT")
    public ResponseEntity<TokenResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        return ResponseEntity.ok(usuariosService.login(loginRequestDTO));
    }

    @PostMapping("/logout")
    @Operation(summary = "Logout de usuário", description = "Valida token para logout. Cliente deve descartar o token localmente")
    public ResponseEntity<String> logout(
            @Parameter(description = "Token JWT no formato 'Bearer {token}'")
            @RequestHeader("Authorization") String authorization) {
        return ResponseEntity.ok(jwtService.logout(authorization));
    }

    @GetMapping("/validate")
    @Operation(summary = "Validar token JWT", description = "Valida se um token JWT é válido e retorna informações básicas")
    public ResponseEntity<TokenValidationDTO> validateToken(
            @Parameter(description = "Token JWT no formato 'Bearer {token}'")
            @RequestHeader("Authorization") String authorization) {
        return ResponseEntity.ok(jwtService.validateTokenAndGetInfo(authorization));
    }

    @PostMapping("/redefinir-senha")
    @Operation(summary = "Redefinir senha do usuário", description = "Redefine senha usando CPF ou matrícula")
    public ResponseEntity<String> redefinirSenha(@RequestBody RedefinirSenhaDTO redefinirSenhaDTO) {
        usuariosService.redefinirSenha(redefinirSenhaDTO);
        return ResponseEntity.ok("Senha redefinida com sucesso");
    }

    @GetMapping("/google/login")
    @Operation(summary = "Iniciar login com Google OAuth2", description = "Redireciona para autorização do Google")
    public ResponseEntity<String> loginGoogle() {
        String authUrl = googleAuthService.generateAuthorizationUrl();
        return ResponseEntity.status(HttpStatus.FOUND).header("Location", authUrl).build();
    }

    @GetMapping("/google/callback")
    @Operation(summary = "Callback do Google OAuth2", description = "Processa callback após autorização e retorna token JWT")
    public ResponseEntity<TokenResponseDTO> handleGoogleAuthCallback(
            @Parameter(description = "Código de autorização do Google") @RequestParam String code) {
        return ResponseEntity.ok(usuariosService.loginWithGoogle(code, googleAuthService, jwtService));
    }
}

