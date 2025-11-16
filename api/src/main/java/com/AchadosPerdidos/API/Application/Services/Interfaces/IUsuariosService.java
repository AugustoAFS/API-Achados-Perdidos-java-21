package com.AchadosPerdidos.API.Application.Services.Interfaces;

import com.AchadosPerdidos.API.Application.DTOs.Auth.AuthResponseDTO;
import com.AchadosPerdidos.API.Application.DTOs.Auth.LoginRequestDTO;
import com.AchadosPerdidos.API.Application.DTOs.Usuario.UsuariosDTO;
import com.AchadosPerdidos.API.Application.DTOs.Usuario.UsuariosListDTO;
import com.AchadosPerdidos.API.Application.DTOs.Usuario.UsuariosCreateDTO;
import com.AchadosPerdidos.API.Application.DTOs.Usuario.UsuariosUpdateDTO;

public interface IUsuariosService {
    UsuariosListDTO getAllUsuarios();
    UsuariosListDTO getUsuarioById(int id);
    UsuariosDTO getUsuarioByEmail(String email);
    UsuariosCreateDTO createUsuario(UsuariosCreateDTO usuariosDTO);
    UsuariosUpdateDTO updateUsuario(int id, UsuariosUpdateDTO usuariosDTO);
    boolean deleteUsuario(int id);
    boolean redefinirSenha(String cpf, String matricula, String novaSenha);
    AuthResponseDTO login(LoginRequestDTO loginRequestDTO);
}
