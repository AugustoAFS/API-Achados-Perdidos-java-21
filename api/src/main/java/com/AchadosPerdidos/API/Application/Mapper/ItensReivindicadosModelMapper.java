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
        dto.setDetalhes_Reivindicacao(itensReivindicados.getDetalhes_Reivindicacao());
        dto.setItem_Id(itensReivindicados.getItem_Id());
        dto.setUsuario_Reivindicador_Id(itensReivindicados.getUsuario_Reivindicador_Id());
        dto.setUsuario_Achou_Id(itensReivindicados.getUsuario_Achou_Id());
        dto.setDta_Criacao(itensReivindicados.getDta_Criacao());
        dto.setFlg_Inativo(itensReivindicados.getFlg_Inativo());
        dto.setDta_Remocao(itensReivindicados.getDta_Remocao());
        
        return dto;
    }

    public ItensReivindicados toEntity(ItensReivindicadosDTO dto) {
        if (dto == null) {
            return null;
        }
        
        ItensReivindicados itensReivindicados = new ItensReivindicados();
        itensReivindicados.setId(dto.getId());
        itensReivindicados.setDetalhes_Reivindicacao(dto.getDetalhes_Reivindicacao());
        itensReivindicados.setItem_Id(dto.getItem_Id());
        itensReivindicados.setUsuario_Reivindicador_Id(dto.getUsuario_Reivindicador_Id());
        itensReivindicados.setUsuario_Achou_Id(dto.getUsuario_Achou_Id());
        itensReivindicados.setDta_Criacao(dto.getDta_Criacao());
        itensReivindicados.setFlg_Inativo(dto.getFlg_Inativo());
        itensReivindicados.setDta_Remocao(dto.getDta_Remocao());
        
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

