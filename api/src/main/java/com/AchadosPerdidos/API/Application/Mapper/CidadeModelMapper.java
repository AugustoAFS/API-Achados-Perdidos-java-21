package com.AchadosPerdidos.API.Application.Mapper;

import com.AchadosPerdidos.API.Application.DTOs.Cidade.CidadeDTO;
import com.AchadosPerdidos.API.Domain.Entity.Cidade;
import org.springframework.stereotype.Component;

@Component
public class CidadeModelMapper {

    public CidadeDTO toDTO(Cidade cidade) {
        if (cidade == null) return null;
        CidadeDTO dto = new CidadeDTO();
        dto.setId(cidade.getId());
        dto.setNome(cidade.getNome());
        dto.setEstadoId(cidade.getEstadoId());
        dto.setDtaCriacao(cidade.getDtaCriacao());
        dto.setFlgInativo(cidade.getFlgInativo());
        dto.setDtaRemocao(cidade.getDtaRemocao());
        return dto;
    }

    public Cidade toEntity(CidadeDTO dto) {
        if (dto == null) return null;
        Cidade entity = new Cidade();
        entity.setId(dto.getId());
        entity.setNome(dto.getNome());
        entity.setEstadoId(dto.getEstadoId());
        entity.setDtaCriacao(dto.getDtaCriacao());
        entity.setFlgInativo(dto.getFlgInativo());
        entity.setDtaRemocao(dto.getDtaRemocao());
        return entity;
    }
}


