package com.AchadosPerdidos.API.Application.Services.Interfaces;

import com.AchadosPerdidos.API.Application.DTOs.Usuario.UsuariosDTO;
import com.AchadosPerdidos.API.Application.DTOs.Usuario.UsuariosListDTO;
import com.AchadosPerdidos.API.Application.DTOs.Usuario.UsuariosCreateDTO;
import com.AchadosPerdidos.API.Application.DTOs.Usuario.UsuariosUpdateDTO;

public interface IUsuariosService {
    UsuariosListDTO getAllUsuarios();
    UsuariosDTO getUsuarioById(int id);
    UsuariosDTO getUsuarioByEmail(String email);
    UsuariosDTO createUsuario(UsuariosDTO usuariosDTO);
    UsuariosUpdateDTO updateUsuario(int id, UsuariosDTO usuariosDTO);
    boolean deleteUsuario(int id);
    UsuariosListDTO getActiveUsuarios();
    UsuariosListDTO getUsuariosByRole(int tipoRoleId);
    UsuariosListDTO getUsuariosByInstitution(int instituicaoId);
    UsuariosListDTO getUsuariosByCampus(int campusId);
    UsuariosDTO authenticateUsuario(String email, String senha);
    UsuariosDTO createUsuarioFromDTO(UsuariosCreateDTO createDTO);
    UsuariosDTO updateUsuarioFromDTO(int id, UsuariosUpdateDTO updateDTO);
}