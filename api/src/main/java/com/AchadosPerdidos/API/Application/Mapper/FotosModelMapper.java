package com.AchadosPerdidos.API.Application.Mapper;

import com.AchadosPerdidos.API.Application.DTOs.Fotos.FotosDTO;
import com.AchadosPerdidos.API.Application.DTOs.Fotos.FotosListDTO;
import com.AchadosPerdidos.API.Domain.Entity.Fotos;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FotosModelMapper {

    public FotosDTO toDTO(Fotos fotos) {
        if (fotos == null) {
            return null;
        }
        
        return new FotosDTO(
            fotos.getId(),
            fotos.getUrl(),
            fotos.getProvedorArmazenamento() != null ? fotos.getProvedorArmazenamento().toString() : null,
            fotos.getChaveArmazenamento(),
            fotos.getNomeArquivoOriginal(),
            fotos.getTamanhoArquivoBytes(),
            fotos.getDtaCriacao(),
            fotos.getFlgInativo(),
            fotos.getDtaRemocao()
        );
    }

    public Fotos toEntity(FotosDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Fotos fotos = new Fotos();
        fotos.setId(dto.getId());
        fotos.setUrl(dto.getUrl());
        if (dto.getProvedorArmazenamento() != null) {
            fotos.setProvedorArmazenamento(com.AchadosPerdidos.API.Domain.Enum.Provedor_Armazenamento.valueOf(dto.getProvedorArmazenamento()));
        }
        fotos.setChaveArmazenamento(dto.getChaveArmazenamento());
        fotos.setNomeArquivoOriginal(dto.getNomeArquivoOriginal());
        fotos.setTamanhoArquivoBytes(dto.getTamanhoArquivoBytes());
        fotos.setDtaCriacao(dto.getDtaCriacao());
        fotos.setFlgInativo(dto.getFlgInativo());
        fotos.setDtaRemocao(dto.getDtaRemocao());
        
        return fotos;
    }

    public FotosListDTO toListDTO(List<Fotos> fotos) {
        if (fotos == null) {
            return null;
        }
        
        List<FotosDTO> dtoList = fotos.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        
        return new FotosListDTO(dtoList, dtoList.size());
    }
}
