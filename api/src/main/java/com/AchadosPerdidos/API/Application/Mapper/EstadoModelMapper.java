package com.AchadosPerdidos.API.Application.Mapper;

import com.AchadosPerdidos.API.Application.DTOs.Estado.EstadoDTO;
import com.AchadosPerdidos.API.Domain.Entity.Estado;
import org.springframework.stereotype.Component;

@Component
public class EstadoModelMapper {

    public EstadoDTO toDTO(Estado estado) {
        if (estado == null) return null;
        return new EstadoDTO(
            estado.getId(),
            estado.getNome(),
            estado.getUf(),
            estado.getDtaCriacao(),
            estado.getFlgInativo(),
            estado.getDtaRemocao()
        );
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


