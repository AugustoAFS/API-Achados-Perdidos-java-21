package com.AchadosPerdidos.API.Application.Mapper;

import com.AchadosPerdidos.API.Application.DTOs.ItensReivindicados.ItensReivindicadosDTO;
import com.AchadosPerdidos.API.Application.DTOs.ItensReivindicados.ItensReivindicadosListDTO;
import com.AchadosPerdidos.API.Domain.Entity.ItensReivindicados;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ItensReivindicadosModelMapper {

    public ItensReivindicadosDTO toDTO(ItensReivindicados itensReivindicados) {
        if (itensReivindicados == null) {
            return null;
        }
        
        ItensReivindicadosDTO dto = new ItensReivindicadosDTO();
        dto.setId(itensReivindicados.getId());
        dto.setDetalhesReivindicacao(itensReivindicados.getDetalhes_Reivindicacao());
        dto.setItemId(itensReivindicados.getItem_Id());
        dto.setUsuarioReivindicadorId(itensReivindicados.getUsuario_Reivindicador_Id());
        dto.setUsuarioAchouId(itensReivindicados.getUsuario_Achou_Id());
        dto.setDtaCriacao(itensReivindicados.getDta_Criacao());
        dto.setFlgInativo(itensReivindicados.getFlg_Inativo());
        dto.setDtaRemocao(itensReivindicados.getDta_Remocao());
        
        return dto;
    }

    public ItensReivindicados toEntity(ItensReivindicadosDTO dto) {
        if (dto == null) {
            return null;
        }
        
        ItensReivindicados itensReivindicados = new ItensReivindicados();
        itensReivindicados.setId(dto.getId());
        itensReivindicados.setDetalhes_Reivindicacao(dto.getDetalhesReivindicacao());
        itensReivindicados.setItem_Id(dto.getItemId());
        itensReivindicados.setUsuario_Reivindicador_Id(dto.getUsuarioReivindicadorId());
        itensReivindicados.setUsuario_Achou_Id(dto.getUsuarioAchouId());
        itensReivindicados.setDta_Criacao(dto.getDtaCriacao());
        itensReivindicados.setFlg_Inativo(dto.getFlgInativo());
        itensReivindicados.setDta_Remocao(dto.getDtaRemocao());
        
        return itensReivindicados;
    }

    public ItensReivindicadosListDTO toListDTO(List<ItensReivindicados> itensReivindicados) {
        if (itensReivindicados == null) {
            return null;
        }
        
        List<ItensReivindicadosDTO> dtoList = itensReivindicados.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        
        ItensReivindicadosListDTO listDTO = new ItensReivindicadosListDTO();
        listDTO.setItensReivindicados(dtoList);
        listDTO.setTotalCount(dtoList.size());
        
        return listDTO;
    }
}

