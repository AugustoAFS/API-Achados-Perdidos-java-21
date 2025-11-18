package com.AchadosPerdidos.API.Application.Mapper;

import com.AchadosPerdidos.API.Application.DTOs.Campus.CampusDTO;
import com.AchadosPerdidos.API.Application.DTOs.Campus.CampusListDTO;
import com.AchadosPerdidos.API.Domain.Entity.Campus;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CampusModelMapper {

    public CampusDTO toDTO(Campus campus) {
        if (campus == null) {
            return null;
        }
        
        return new CampusDTO(
            campus.getId(),
            campus.getNome(),
            campus.getInstituicaoId(),
            campus.getEnderecoId(),
            campus.getDtaCriacao() != null ? Date.from(campus.getDtaCriacao().atZone(ZoneId.systemDefault()).toInstant()) : null,
            campus.getFlgInativo(),
            campus.getDtaRemocao() != null ? Date.from(campus.getDtaRemocao().atZone(ZoneId.systemDefault()).toInstant()) : null
        );
    }

    public Campus toEntity(CampusDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Campus campus = new Campus();
        campus.setId(dto.getId());
        campus.setNome(dto.getNome());
        campus.setInstituicaoId(dto.getInstituicaoId());
        campus.setEnderecoId(dto.getEnderecoId());
        if (dto.getDtaCriacao() != null) {
            campus.setDtaCriacao(dto.getDtaCriacao().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        }
        campus.setFlgInativo(dto.getFlgInativo());
        if (dto.getDtaRemocao() != null) {
            campus.setDtaRemocao(dto.getDtaRemocao().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        }
        
        return campus;
    }

    public CampusListDTO toListDTO(List<Campus> campus) {
        if (campus == null) {
            return null;
        }
        
        List<CampusDTO> dtoList = campus.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        
        return new CampusListDTO(dtoList, dtoList.size());
    }
}