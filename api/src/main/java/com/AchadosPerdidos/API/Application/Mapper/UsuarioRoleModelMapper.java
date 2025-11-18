package com.AchadosPerdidos.API.Application.Mapper;

import com.AchadosPerdidos.API.Application.DTOs.UsuarioRole.UsuarioRoleCreateDTO;
import com.AchadosPerdidos.API.Application.DTOs.UsuarioRole.UsuarioRoleDTO;
import com.AchadosPerdidos.API.Application.DTOs.UsuarioRole.UsuarioRoleListDTO;
import com.AchadosPerdidos.API.Application.DTOs.UsuarioRole.UsuarioRoleUpdateDTO;
import com.AchadosPerdidos.API.Domain.Entity.UsuarioRole;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UsuarioRoleModelMapper {

    public UsuarioRoleDTO toDTO(UsuarioRole usuarioRole) {
        if (usuarioRole == null) {
            return null;
        }
        
        return new UsuarioRoleDTO(
            usuarioRole.getId(),
            usuarioRole.getUsuario_id(),
            usuarioRole.getRole_id(),
            usuarioRole.getDta_Criacao() != null ? Date.from(usuarioRole.getDta_Criacao().atZone(ZoneId.systemDefault()).toInstant()) : null,
            usuarioRole.getFlg_Inativo(),
            usuarioRole.getDta_Remocao() != null ? Date.from(usuarioRole.getDta_Remocao().atZone(ZoneId.systemDefault()).toInstant()) : null
        );
    }

    public UsuarioRole toEntity(UsuarioRoleDTO dto) {
        if (dto == null) {
            return null;
        }
        
        UsuarioRole usuarioRole = new UsuarioRole();
        usuarioRole.setId(dto.getId());
        usuarioRole.setUsuario_id(dto.getUsuarioId());
        usuarioRole.setRole_id(dto.getRoleId());
        if (dto.getDtaCriacao() != null) {
            usuarioRole.setDta_Criacao(dto.getDtaCriacao().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        }
        usuarioRole.setFlg_Inativo(dto.getFlgInativo());
        if (dto.getDtaRemocao() != null) {
            usuarioRole.setDta_Remocao(dto.getDtaRemocao().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        }
        
        return usuarioRole;
    }

    public UsuarioRole fromCreateDTO(UsuarioRoleCreateDTO dto) {
        if (dto == null) {
            return null;
        }
        
        UsuarioRole usuarioRole = new UsuarioRole();
        usuarioRole.setUsuario_id(dto.getUsuarioId());
        usuarioRole.setRole_id(dto.getRoleId());
        
        return usuarioRole;
    }

    public void updateFromDTO(UsuarioRole usuarioRole, UsuarioRoleUpdateDTO dto) {
        if (usuarioRole == null || dto == null) {
            return;
        }
        
        if (dto.getUsuarioId() != null) {
            usuarioRole.setUsuario_id(dto.getUsuarioId());
        }
        if (dto.getRoleId() != null) {
            usuarioRole.setRole_id(dto.getRoleId());
        }
        if (dto.getFlgInativo() != null) {
            usuarioRole.setFlg_Inativo(dto.getFlgInativo());
        }
    }

    public UsuarioRoleListDTO toListDTO(List<UsuarioRole> usuarioRoles) {
        if (usuarioRoles == null) {
            return null;
        }
        
        List<UsuarioRoleDTO> dtoList = usuarioRoles.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        
        return new UsuarioRoleListDTO(dtoList, dtoList.size());
    }
}

