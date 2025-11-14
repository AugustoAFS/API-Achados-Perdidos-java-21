package com.AchadosPerdidos.API.Application.Mapper;

import com.AchadosPerdidos.API.Application.DTOs.Role.RoleDTO;
import com.AchadosPerdidos.API.Domain.Entity.Role;
import org.springframework.stereotype.Component;

@Component
public class RoleModelMapper {

    public RoleDTO toDTO(Role role) {
        if (role == null) return null;
        return new RoleDTO(
            role.getId(),
            role.getNome(),
            role.getDescricao(),
            role.getDtaCriacao(),
            role.getFlgInativo(),
            role.getDtaRemocao()
        );
    }

    public Role toEntity(RoleDTO dto) {
        if (dto == null) return null;
        Role entity = new Role();
        entity.setId(dto.getId());
        entity.setNome(dto.getNome());
        entity.setDescricao(dto.getDescricao());
        entity.setDtaCriacao(dto.getDtaCriacao());
        entity.setFlgInativo(dto.getFlgInativo());
        entity.setDtaRemocao(dto.getDtaRemocao());
        return entity;
    }
}


