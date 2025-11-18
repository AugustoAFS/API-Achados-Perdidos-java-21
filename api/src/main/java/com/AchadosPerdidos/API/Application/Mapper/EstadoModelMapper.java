package com.AchadosPerdidos.API.Application.Mapper;

import com.AchadosPerdidos.API.Application.DTOs.Estado.EstadoDTO;
import com.AchadosPerdidos.API.Domain.Entity.Estado;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.Date;

@Component
public class EstadoModelMapper {

    public EstadoDTO toDTO(Estado estado) {
        if (estado == null) return null;
        return new EstadoDTO(
            estado.getId(),
            estado.getNome(),
            estado.getUf(),
            estado.getDtaCriacao() != null ? Date.from(estado.getDtaCriacao().atZone(ZoneId.systemDefault()).toInstant()) : null,
            estado.getFlgInativo(),
            estado.getDtaRemocao() != null ? Date.from(estado.getDtaRemocao().atZone(ZoneId.systemDefault()).toInstant()) : null
        );
    }

    public Estado toEntity(EstadoDTO dto) {
        if (dto == null) return null;
        Estado entity = new Estado();
        entity.setId(dto.getId());
        entity.setNome(dto.getNome());
        entity.setUf(dto.getUf());
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


