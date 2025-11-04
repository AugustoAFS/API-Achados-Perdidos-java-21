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
        
        ItensDTO dto = new ItensDTO();
        dto.setId(itens.getId());
        dto.setNome(itens.getNome());
        dto.setDescricao(itens.getDescricao());
        dto.setEncontradoEm(itens.getEncontradoEm());
        dto.setUsuarioRelatorId(itens.getUsuarioRelatorId());
        dto.setLocalId(itens.getLocalId());
        dto.setStatusItemId(itens.getStatusItemId());
        dto.setDtaCriacao(itens.getDtaCriacao());
        dto.setFlgInativo(itens.getFlgInativo());
        dto.setDtaRemocao(itens.getDtaRemocao());
        
        return dto;
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
        
        ItensListDTO listDTO = new ItensListDTO();
        listDTO.setItens(dtoList);
        listDTO.setTotalCount(dtoList.size());
        
        return listDTO;
    }
}
