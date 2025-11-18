package com.AchadosPerdidos.API.Application.Mapper;

import com.AchadosPerdidos.API.Application.DTOs.Local.LocalDTO;
import com.AchadosPerdidos.API.Domain.Entity.Local;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.Date;

@Component
public class LocalModelMapper {

    public LocalDTO toDTO(Local local) {
        if (local == null) return null;
        return new LocalDTO(
            local.getId(),
            local.getNome(),
            local.getDescricao(),
            local.getCampus_id(),
            local.getDtaCriacao() != null ? Date.from(local.getDtaCriacao().atZone(ZoneId.systemDefault()).toInstant()) : null,
            local.getFlgInativo(),
            local.getDtaRemocao() != null ? Date.from(local.getDtaRemocao().atZone(ZoneId.systemDefault()).toInstant()) : null
        );
    }

    public Local toEntity(LocalDTO dto) {
        if (dto == null) return null;
        Local entity = new Local();
        entity.setId(dto.getId());
        entity.setNome(dto.getNome());
        entity.setDescricao(dto.getDescricao());
        entity.setCampus_id(dto.getCampusId());
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


