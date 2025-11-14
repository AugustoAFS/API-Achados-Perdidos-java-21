package com.AchadosPerdidos.API.Application.Mapper;

import com.AchadosPerdidos.API.Application.DTOs.Reivindicacoes.ReivindicacoesDTO;
import com.AchadosPerdidos.API.Application.DTOs.Reivindicacoes.ReivindicacoesListDTO;
import com.AchadosPerdidos.API.Domain.Entity.Reivindicacoes;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReivindicacoesModelMapper {

    public ReivindicacoesDTO toDTO(Reivindicacoes reivindicacoes) {
        if (reivindicacoes == null) {
            return null;
        }
        
        return new ReivindicacoesDTO(
            reivindicacoes.getId(),
            reivindicacoes.getDetalhesReivindicacao(),
            reivindicacoes.getItemId(),
            reivindicacoes.getUsuarioReivindicadorId(),
            reivindicacoes.getUsuarioAchouId(),
            reivindicacoes.getDtaCriacao(),
            reivindicacoes.getFlgInativo(),
            reivindicacoes.getDtaRemocao()
        );
    }

    public Reivindicacoes toEntity(ReivindicacoesDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Reivindicacoes reivindicacoes = new Reivindicacoes();
    reivindicacoes.setId(dto.getId());
    reivindicacoes.setDetalhesReivindicacao(dto.getDetalhesReivindicacao());
    reivindicacoes.setItemId(dto.getItemId());
    reivindicacoes.setUsuarioReivindicadorId(dto.getUsuarioReivindicadorId());
    reivindicacoes.setUsuarioAchouId(dto.getUsuarioAchouId());
    reivindicacoes.setDtaCriacao(dto.getDtaCriacao());
    reivindicacoes.setFlgInativo(dto.getFlgInativo());
    reivindicacoes.setDtaRemocao(dto.getDtaRemocao());
        
        return reivindicacoes;
    }

    public ReivindicacoesListDTO toListDTO(List<Reivindicacoes> reivindicacoes) {
        if (reivindicacoes == null) {
            return null;
        }
        
        List<ReivindicacoesDTO> dtoList = reivindicacoes.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        
        return new ReivindicacoesListDTO(dtoList, dtoList.size());
    }
}
