package com.AchadosPerdidos.API.Application.Mapper;

import com.AchadosPerdidos.API.Application.DTOs.Cidade.CidadeDTO;
import com.AchadosPerdidos.API.Domain.Entity.Cidade;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.Date;

@Component
public class CidadeModelMapper {

    public CidadeDTO toDTO(Cidade cidade) {
        if (cidade == null) return null;
        return new CidadeDTO(
            cidade.getId(),
            cidade.getNome(),
            cidade.getEstadoId(),
            cidade.getDtaCriacao() != null ? Date.from(cidade.getDtaCriacao().atZone(ZoneId.systemDefault()).toInstant()) : null,
            cidade.getFlgInativo(),
            cidade.getDtaRemocao() != null ? Date.from(cidade.getDtaRemocao().atZone(ZoneId.systemDefault()).toInstant()) : null
        );
    }

    public Cidade toEntity(CidadeDTO dto) {
        if (dto == null) return null;
        Cidade entity = new Cidade();
        entity.setId(dto.getId());
        entity.setNome(dto.getNome());
        entity.setEstadoId(dto.getEstadoId());
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


