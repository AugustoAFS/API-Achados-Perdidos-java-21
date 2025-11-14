package com.AchadosPerdidos.API.Application.Mapper;

import com.AchadosPerdidos.API.Application.DTOs.Itens.ItensDTO;
import com.AchadosPerdidos.API.Application.DTOs.Itens.ItensListDTO;
import com.AchadosPerdidos.API.Domain.Entity.Itens;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ItensModelMapper {

    public ItensDTO toDTO(Itens itens) {
        if (itens == null) {
            return null;
        }
        
        return new ItensDTO(
            itens.getId(),
            itens.getNome(),
            itens.getDescricao(),
            itens.getEncontradoEm(),
            itens.getUsuarioRelatorId(),
            itens.getLocalId(),
            itens.getStatusItemId(),
            itens.getDtaCriacao(),
            itens.getFlgInativo(),
            itens.getDtaRemocao()
        );
    }

    public Itens toEntity(ItensDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Itens itens = new Itens();
        itens.setId(dto.getId());
        itens.setNome(dto.getNome());
        itens.setDescricao(dto.getDescricao());
        itens.setEncontradoEm(dto.getEncontradoEm());
        itens.setUsuarioRelatorId(dto.getUsuarioRelatorId());
        itens.setLocalId(dto.getLocalId());
        itens.setStatusItemId(dto.getStatusItemId());
        itens.setDtaCriacao(dto.getDtaCriacao());
        itens.setFlgInativo(dto.getFlgInativo());
        itens.setDtaRemocao(dto.getDtaRemocao());
        
        return itens;
    }

    public ItensListDTO toListDTO(List<Itens> itens) {
        if (itens == null) {
            return null;
        }
        
        List<ItensDTO> dtoList = itens.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        
        return new ItensListDTO(dtoList, dtoList.size());
    }
}
