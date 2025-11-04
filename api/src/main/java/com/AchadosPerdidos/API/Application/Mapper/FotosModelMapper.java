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
        
        FotosDTO dto = new FotosDTO();
        dto.setId(fotos.getId());
        dto.setUrl(fotos.getUrl());
        dto.setProvedorArmazenamento(fotos.getProvedorArmazenamento() != null ? fotos.getProvedorArmazenamento().toString() : null);
        dto.setChaveArmazenamento(fotos.getChaveArmazenamento());
        dto.setNomeArquivoOriginal(fotos.getNomeArquivoOriginal());
        dto.setTamanhoArquivoBytes(fotos.getTamanhoArquivoBytes());
        dto.setDtaCriacao(fotos.getDtaCriacao());
        dto.setFlgInativo(fotos.getFlgInativo());
        dto.setDtaRemocao(fotos.getDtaRemocao());
        
        return dto;
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
        
        FotosListDTO listDTO = new FotosListDTO();
        listDTO.setFotos(dtoList);
        listDTO.setTotalCount(dtoList.size());
        
        return listDTO;
    }
}
