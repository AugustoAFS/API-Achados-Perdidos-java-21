package com.AchadosPerdidos.API.Application.Mapper;

import com.AchadosPerdidos.API.Application.DTOs.Cidade.CidadeDTO;
import com.AchadosPerdidos.API.Domain.Entity.Cidade;
import com.AchadosPerdidos.API.Domain.Entity.Estado;
import org.springframework.stereotype.Component;

@Component
public class CidadeMapper {

    public CidadeDTO toDTO(Cidade cidade) {
        if (cidade == null) return null;
        Integer estadoId = cidade.getEstadoId() != null ? cidade.getEstadoId().getId() : null;

        return new CidadeDTO(
            cidade.getId(),
            cidade.getNome(),
            estadoId,
            cidade.getDtaCriacao(),
            cidade.getFlgInativo(),
            cidade.getDtaRemocao()
        );
    }

    public Cidade toEntity(CidadeDTO dto) {
        if (dto == null) return null;
        Cidade entity = new Cidade();
        entity.setId(dto.getId());
        entity.setNome(dto.getNome());
        entity.setEstadoId(toEstado(dto.getEstadoId()));
        entity.setDtaCriacao(dto.getDtaCriacao());
        entity.setFlgInativo(dto.getFlgInativo());
        entity.setDtaRemocao(dto.getDtaRemocao());
        return entity;
    }

    private Estado toEstado(Integer id) {
        if (id == null) {
            return null;
        }
        Estado estado = new Estado();
        estado.setId(id);
        return estado;
    }
}


