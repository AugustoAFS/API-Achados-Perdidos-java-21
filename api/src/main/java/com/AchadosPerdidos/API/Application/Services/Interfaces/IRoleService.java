package com.AchadosPerdidos.API.Application.Services.Interfaces;

import com.AchadosPerdidos.API.Application.DTOs.Role.RoleDTO;

import java.util.List;

public interface IRoleService {
    List<RoleDTO> getAllRoles();
    RoleDTO getRoleById(Integer id);
    RoleDTO getRoleByNome(String nome);
    List<RoleDTO> getActiveRoles();
}

