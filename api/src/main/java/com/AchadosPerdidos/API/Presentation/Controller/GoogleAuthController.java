package com.AchadosPerdidos.API.Presentation.Controller;

import com.AchadosPerdidos.API.Application.DTOs.Auth.AuthResponseDTO;
import com.AchadosPerdidos.API.Application.DTOs.Auth.GoogleUserDTO;
import com.AchadosPerdidos.API.Application.DTOs.Usuario.UsuariosDTO;
import com.AchadosPerdidos.API.Application.Services.Interfaces.IGoogleAuthService;
import com.AchadosPerdidos.API.Application.Services.Interfaces.IJwtTokenService;
import com.AchadosPerdidos.API.Application.Services.Interfaces.IUsuariosService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller para autenticação Google OAuth2
 */
@RestController
@RequestMapping("/api/google-auth")
@CrossOrigin(origins = "*")
@Tag(name = "Autenticação Google", description = "API para autenticação via Google OAuth2")
public class GoogleAuthController {
    
    private static final Logger logger = LoggerFactory.getLogger(GoogleAuthController.class);
    
    private final IGoogleAuthService googleAuthService;
    private final IJwtTokenService jwtTokenService;
    private final IUsuariosService usuariosService;
    
    public GoogleAuthController(
            IGoogleAuthService googleAuthService,
            IJwtTokenService jwtTokenService,
            IUsuariosService usuariosService) {
        this.googleAuthService = googleAuthService;
        this.jwtTokenService = jwtTokenService;
        this.usuariosService = usuariosService;
        logger.info("GoogleAuthController inicializado");
    }
    
    /**
     * Inicia o processo de login com Google
     * @return Redirecionamento para URL de autorização do Google
     */
    @GetMapping("/login")
    public ResponseEntity<String> loginGoogle() {
        try {
            logger.info("Iniciando processo de login do Google");
            String authUrl = googleAuthService.buildGoogleAuthorizationUrl();
            logger.info("Redirecionando para URL de autorização: {}", authUrl);
            return ResponseEntity.status(HttpStatus.FOUND)
                .header("Location", authUrl)
                .build();
        } catch (Exception e) {
            logger.error("Erro ao iniciar processo de login do Google", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao iniciar processo de autenticação");
        }
    }
    
    /**
     * Processa o callback do Google OAuth2
     * @param code Código de autorização retornado pelo Google
     * @return Token JWT e informações do usuário
     */
    @GetMapping("/callback")
    public ResponseEntity<?> handleGoogleAuthCallback(@RequestParam(required = false) String code) {
        try {
            if (code == null || code.trim().isEmpty()) {
                logger.warn("Código de autorização não fornecido");
                return ResponseEntity.badRequest()
                    .body("Código de autorização não fornecido");
            }
            
            logger.info("Recebido código de autorização: {}", code);
            GoogleUserDTO googleUser = googleAuthService.getUserInfoFromAuthorizationCode(code);
            
            if (googleUser == null) {
                logger.warn("Não foi possível obter informações do usuário");
                return ResponseEntity.badRequest()
                    .body("Não foi possível obter informações do usuário");
            }
            
            // Buscar usuário no banco de dados pelo email
            logger.info("Buscando usuário no MySQL com email: {}", googleUser.getEmail());
            UsuariosDTO usuario = usuariosService.getUsuarioByEmail(googleUser.getEmail());
            if (usuario == null) {
                logger.warn("Usuário não encontrado no banco de dados MySQL: {}", googleUser.getEmail());
                logger.info("Verifique se o usuário existe na tabela 'Usuarios' do MySQL");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Usuário não encontrado no banco de dados. Email: " + googleUser.getEmail());
            }
            
            // Verificar se o usuário está ativo
            if (usuario.getFlgInativo()) {
                logger.warn("Usuário inativo tentou fazer login: {}", usuario.getEmail());
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Usuário inativo");
            }
            
            logger.info("Informações do usuário obtidas: {}", usuario.getEmail());
            
            // Gerar token JWT com informações do usuário
            String token = jwtTokenService.generateToken(
                usuario.getEmail(),
                usuario.getNomeCompleto(),
                "User",
                String.valueOf(usuario.getId())
            );
            
            logger.info("Token JWT gerado com sucesso para o usuário: {}", usuario.getEmail());
            
            // Criar resposta com token e informações do usuário
            AuthResponseDTO.UserInfoDTO userInfo = new AuthResponseDTO.UserInfoDTO();
            userInfo.setId(usuario.getId());
            userInfo.setNome(usuario.getNomeCompleto());
            userInfo.setEmail(usuario.getEmail());
            userInfo.setRole("User");
            userInfo.setCampus("");
            
            AuthResponseDTO response = new AuthResponseDTO();
            response.setToken(token);
            response.setTokenType("Bearer");
            response.setExpiresIn(3600); // 1 hora
            response.setUser(userInfo);
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("Erro ao processar callback do Google", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro interno ao processar autenticação");
        }
    }
}
