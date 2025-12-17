package com.AchadosPerdidos.API.Application.Services.Interfaces;

import com.AchadosPerdidos.API.Application.DTOs.Auth.AuthResponseDTO;
import com.AchadosPerdidos.API.Application.DTOs.Auth.LoginRequestDTO;
import com.AchadosPerdidos.API.Application.DTOs.Auth.RedefinirSenhaDTO;
import com.AchadosPerdidos.API.Application.DTOs.Auth.TokenResponseDTO;
import com.AchadosPerdidos.API.Application.DTOs.Usuario.UsuariosDTO;
import com.AchadosPerdidos.API.Application.DTOs.Usuario.UsuariosListDTO;
import com.AchadosPerdidos.API.Application.DTOs.Usuario.UsuariosCreateDTO;
import com.AchadosPerdidos.API.Application.DTOs.Usuario.UsuariosUpdateDTO;
import com.AchadosPerdidos.API.Application.DTOs.Usuario.AlunoCreateDTO;
import com.AchadosPerdidos.API.Application.DTOs.Usuario.ServidorCreateDTO;

public interface IUsuariosService {
    UsuariosListDTO getAllUsuarios();
    UsuariosListDTO getUsuarioById(int id);
    UsuariosDTO getUsuarioByEmail(String email);
    UsuariosCreateDTO createUsuario(UsuariosCreateDTO usuariosDTO);
    UsuariosCreateDTO createAluno(AlunoCreateDTO alunoDTO);
    UsuariosCreateDTO createServidor(ServidorCreateDTO servidorDTO);
    UsuariosUpdateDTO updateUsuario(int id, UsuariosUpdateDTO usuariosDTO);
    boolean deleteUsuario(int id);

    // ========== AUTENTICAÇÃO ==========

    /**
     * Realiza login com email e senha
     */
    TokenResponseDTO login(LoginRequestDTO loginRequestDTO);

    /**
     * Redefine senha usando strings (versão antiga - mantida para compatibilidade)
     * @deprecated Use {@link #redefinirSenha(RedefinirSenhaDTO)} instead
     */
    @Deprecated
    boolean redefinirSenha(String cpf, String matricula, String novaSenha);

    /**
     * Redefine senha usando DTO completo com validações
     */
    void redefinirSenha(RedefinirSenhaDTO redefinirSenhaDTO);

    /**
     * Processa login com Google OAuth2
     */
    TokenResponseDTO loginWithGoogle(String code, IGoogleAuthService googleAuthService, IJWTService jwtService);
}
