package com.AchadosPerdidos.API.Application.Mapper;

import com.AchadosPerdidos.API.Application.DTOs.Local.LocalDTO;
import com.AchadosPerdidos.API.Domain.Entity.Local;
import org.springframework.stereotype.Component;

@Component
public class LocalModelMapper {

    public LocalDTO toDTO(Local local) {
        if (local == null) return null;
        return new LocalDTO(
            local.getId(),
            local.getNome(),
            local.getDescricao(),
            local.getCampusId(),
            local.getDtaCriacao(),
            local.getFlgInativo(),
            local.getDtaRemocao()
        );
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


