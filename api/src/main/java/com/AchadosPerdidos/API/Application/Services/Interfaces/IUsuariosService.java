package com.AchadosPerdidos.API.Application.Services.Interfaces;

import com.AchadosPerdidos.API.Application.DTOs.Auth.AuthResponseDTO;
import com.AchadosPerdidos.API.Application.DTOs.Auth.LoginRequestDTO;
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

    //esses carinhas vão ser usados na controller AuthController que vai ser criada
    boolean redefinirSenha(String cpf, String matricula, String novaSenha);
    TokenResponseDTO login(LoginRequestDTO loginRequestDTO);
}
