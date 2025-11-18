package com.AchadosPerdidos.API.Application.Mapper;

import com.AchadosPerdidos.API.Application.DTOs.Role.RoleDTO;
import com.AchadosPerdidos.API.Domain.Entity.Role;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.Date;

@Component
public class RoleModelMapper {

    public RoleDTO toDTO(Role role) {
        if (role == null) return null;
        return new RoleDTO(
            role.getId(),
            role.getNome(),
            role.getDescricao(),
            role.getDtaCriacao() != null ? Date.from(role.getDtaCriacao().atZone(ZoneId.systemDefault()).toInstant()) : null,
            role.getFlgInativo(),
            role.getDtaRemocao() != null ? Date.from(role.getDtaRemocao().atZone(ZoneId.systemDefault()).toInstant()) : null
        );
    }

    public Role toEntity(RoleDTO dto) {
        if (dto == null) return null;
        Role entity = new Role();
        entity.setId(dto.getId());
        entity.setNome(dto.getNome());
        entity.setDescricao(dto.getDescricao());
        if (dto.getDtaCriacao() != null) {
            entity.setDtaCriacao(dto.getDtaCriacao().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        }
        entity.setFlgInativo(dto.getFlgInativo());
        if (dto.getDtaRemocao() != null) {
            entity.setDtaRemocao(dto.getDtaRemocao().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        }
        return entity;
    }
}


