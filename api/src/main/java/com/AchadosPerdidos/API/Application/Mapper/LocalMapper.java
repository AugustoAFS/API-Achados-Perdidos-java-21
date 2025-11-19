package com.AchadosPerdidos.API.Application.Mapper;

import com.AchadosPerdidos.API.Application.DTOs.Local.LocalDTO;
import com.AchadosPerdidos.API.Domain.Entity.Campus;
import com.AchadosPerdidos.API.Domain.Entity.Local;
import org.springframework.stereotype.Component;

@Component
public class LocalMapper {

    public LocalDTO toDTO(Local local) {
        if (local == null) return null;
        return new LocalDTO(
            local.getId(),
            local.getNome(),
            local.getDescricao(),
                local.getCampus_id() != null ? local.getCampus_id().getId() : null,
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
        entity.setCampus_id(toCampus(dto.getCampusId()));
        entity.setDtaCriacao(dto.getDtaCriacao());
        entity.setFlgInativo(dto.getFlgInativo());
        entity.setDtaRemocao(dto.getDtaRemocao());
        return entity;
    }
    private Campus toCampus(Integer campusId) {
        if (campusId == null) {
            return null;
        }
        Campus campus = new Campus();
        campus.setId(campusId);
        return campus;
    }
}


