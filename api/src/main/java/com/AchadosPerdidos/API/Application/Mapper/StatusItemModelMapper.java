package com.AchadosPerdidos.API.Application.Mapper;

import com.AchadosPerdidos.API.Application.DTOs.StatusItem.StatusItemDTO;
import com.AchadosPerdidos.API.Domain.Entity.StatusItem;
import org.springframework.stereotype.Component;

@Component
public class StatusItemModelMapper {

    public StatusItemDTO toDTO(StatusItem status) {
        if (status == null) return null;
        StatusItemDTO dto = new StatusItemDTO();
        dto.setId(status.getId());
        dto.setNome(status.getNome());
        dto.setDescricao(status.getDescricao());
        dto.setStatusItem(status.getStatusItem());
        dto.setDtaCriacao(status.getDtaCriacao());
        dto.setFlgInativo(status.getFlgInativo());
        dto.setDtaRemocao(status.getDtaRemocao());
        return dto;
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


