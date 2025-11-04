package com.AchadosPerdidos.API.Application.Mapper;

import com.AchadosPerdidos.API.Application.DTOs.Estado.EstadoDTO;
import com.AchadosPerdidos.API.Domain.Entity.Estado;
import org.springframework.stereotype.Component;

@Component
public class EstadoModelMapper {

    public EstadoDTO toDTO(Estado estado) {
        if (estado == null) return null;
        EstadoDTO dto = new EstadoDTO();
        dto.setId(estado.getId());
        dto.setNome(estado.getNome());
        dto.setUf(estado.getUf());
        dto.setDtaCriacao(estado.getDtaCriacao());
        dto.setFlgInativo(estado.getFlgInativo());
        dto.setDtaRemocao(estado.getDtaRemocao());
        return dto;
    }

    public Estado toEntity(EstadoDTO dto) {
        if (dto == null) return null;
        Estado entity = new Estado();
        entity.setId(dto.getId());
        entity.setNome(dto.getNome());
        entity.setUf(dto.getUf());
        entity.setDtaCriacao(dto.getDtaCriacao());
        entity.setFlgInativo(dto.getFlgInativo());
        entity.setDtaRemocao(dto.getDtaRemocao());
        return entity;
    }
}


