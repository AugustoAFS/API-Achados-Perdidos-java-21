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
        
        return new ItensReivindicadosDTO(
            itensReivindicados.getId(),
            itensReivindicados.getDetalhes_Reivindicacao(),
            itensReivindicados.getItem_Id(),
            itensReivindicados.getUsuario_Reivindicador_Id(),
            itensReivindicados.getUsuario_Achou_Id(),
            itensReivindicados.getDta_Criacao(),
            itensReivindicados.getFlg_Inativo(),
            itensReivindicados.getDta_Remocao()
        );
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
        
        return new ItensReivindicadosListDTO(dtoList, dtoList.size());
    }
}

