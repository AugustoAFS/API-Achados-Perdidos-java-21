package com.AchadosPerdidos.API.Application.Services.Interfaces;

import com.AchadosPerdidos.API.Application.DTOs.UsuarioRole.UsuarioRoleCreateDTO;
import com.AchadosPerdidos.API.Application.DTOs.UsuarioRole.UsuarioRoleDTO;
import com.AchadosPerdidos.API.Application.DTOs.UsuarioRole.UsuarioRoleListDTO;
import com.AchadosPerdidos.API.Application.DTOs.UsuarioRole.UsuarioRoleUpdateDTO;

public interface IUsuarioRoleService {
    UsuarioRoleListDTO getAllUsuarioRoles();
    UsuarioRoleDTO getUsuarioRoleByUsuarioIdAndRoleId(Integer usuarioId, Integer roleId);
    UsuarioRoleDTO createUsuarioRole(UsuarioRoleCreateDTO createDTO);
    UsuarioRoleDTO updateUsuarioRole(Integer usuarioId, Integer roleId, UsuarioRoleUpdateDTO updateDTO);
    boolean deleteUsuarioRole(Integer usuarioId, Integer roleId);
    UsuarioRoleListDTO getActiveUsuarioRoles();
    UsuarioRoleListDTO findByUsuarioId(Integer usuarioId);
    UsuarioRoleListDTO findByRoleId(Integer roleId);
}

