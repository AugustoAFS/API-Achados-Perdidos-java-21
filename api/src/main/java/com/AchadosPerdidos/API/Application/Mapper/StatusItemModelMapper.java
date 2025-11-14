package com.AchadosPerdidos.API.Application.Mapper;

import com.AchadosPerdidos.API.Application.DTOs.StatusItem.StatusItemDTO;
import com.AchadosPerdidos.API.Domain.Entity.StatusItem;
import org.springframework.stereotype.Component;

@Component
public class StatusItemModelMapper {

    public StatusItemDTO toDTO(StatusItem status) {
        if (status == null) return null;
        return new StatusItemDTO(
            status.getId(),
            status.getNome(),
            status.getDescricao(),
            status.getStatusItem(),
            status.getDtaCriacao(),
            status.getFlgInativo(),
            status.getDtaRemocao()
        );
    }

    public StatusItem toEntity(StatusItemDTO dto) {
        if (dto == null) return null;
        StatusItem entity = new StatusItem();
        entity.setId(dto.getId());
        entity.setNome(dto.getNome());
        entity.setDescricao(dto.getDescricao());
        entity.setStatusItem(dto.getStatusItem());
        entity.setDtaCriacao(dto.getDtaCriacao());
        entity.setFlgInativo(dto.getFlgInativo());
        entity.setDtaRemocao(dto.getDtaRemocao());
        return entity;
    }
}


