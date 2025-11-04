package com.AchadosPerdidos.API.Application.Mapper;

import com.AchadosPerdidos.API.Application.DTOs.Local.LocalDTO;
import com.AchadosPerdidos.API.Domain.Entity.Local;
import org.springframework.stereotype.Component;

@Component
public class LocalModelMapper {

    public LocalDTO toDTO(Local local) {
        if (local == null) return null;
        LocalDTO dto = new LocalDTO();
        dto.setId(local.getId());
        dto.setNome(local.getNome());
        dto.setDescricao(local.getDescricao());
        dto.setCampusId(local.getCampusId());
        dto.setDtaCriacao(local.getDtaCriacao());
        dto.setFlgInativo(local.getFlgInativo());
        dto.setDtaRemocao(local.getDtaRemocao());
        return dto;
    }

    public Local toEntity(LocalDTO dto) {
        if (dto == null) return null;
        Local entity = new Local();
        entity.setId(dto.getId());
        entity.setNome(dto.getNome());
        entity.setDescricao(dto.getDescricao());
        entity.setCampusId(dto.getCampusId());
        entity.setDtaCriacao(dto.getDtaCriacao());
        entity.setFlgInativo(dto.getFlgInativo());
        entity.setDtaRemocao(dto.getDtaRemocao());
        return entity;
    }
}


