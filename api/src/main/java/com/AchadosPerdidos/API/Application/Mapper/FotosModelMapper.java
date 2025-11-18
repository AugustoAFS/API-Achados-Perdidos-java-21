package com.AchadosPerdidos.API.Application.Mapper;

import com.AchadosPerdidos.API.Application.DTOs.Fotos.FotosDTO;
import com.AchadosPerdidos.API.Application.DTOs.Fotos.FotosListDTO;
import com.AchadosPerdidos.API.Domain.Entity.Foto;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FotosModelMapper {

    public FotosDTO toDTO(Foto foto) {
        if (foto == null) {
            return null;
        }
        
        return new FotosDTO(
            foto.getId(),
            foto.getUrl(),
            foto.getProvedorArmazenamento(),
            foto.getChaveArmazenamento(),
            foto.getNomeArquivoOriginal(),
            foto.getTamanhoArquivoBytes(),
            foto.getDtaCriacao() != null ? Date.from(foto.getDtaCriacao().atZone(ZoneId.systemDefault()).toInstant()) : null,
            foto.getFlgInativo(),
            foto.getDtaRemocao() != null ? Date.from(foto.getDtaRemocao().atZone(ZoneId.systemDefault()).toInstant()) : null
        );
    }

    public Foto toEntity(FotosDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Foto foto = new Foto();
        foto.setId(dto.getId());
        foto.setUrl(dto.getUrl());
        foto.setProvedorArmazenamento(dto.getProvedorArmazenamento());
        foto.setChaveArmazenamento(dto.getChaveArmazenamento());
        foto.setNomeArquivoOriginal(dto.getNomeArquivoOriginal());
        foto.setTamanhoArquivoBytes(dto.getTamanhoArquivoBytes());
        if (dto.getDtaCriacao() != null) {
            foto.setDtaCriacao(dto.getDtaCriacao().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        }
        foto.setFlgInativo(dto.getFlgInativo());
        if (dto.getDtaRemocao() != null) {
            foto.setDtaRemocao(dto.getDtaRemocao().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        }
        
        return foto;
    }

    public FotosListDTO toListDTO(List<Foto> fotos) {
        if (fotos == null) {
            return null;
        }
        
        List<FotosDTO> dtoList = fotos.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        
        return new FotosListDTO(dtoList, dtoList.size());
    }
}
