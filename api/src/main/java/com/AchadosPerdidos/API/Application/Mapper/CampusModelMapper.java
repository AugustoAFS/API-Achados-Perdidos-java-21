package com.AchadosPerdidos.API.Application.Mapper;

import com.AchadosPerdidos.API.Application.DTOs.Campus.CampusDTO;
import com.AchadosPerdidos.API.Application.DTOs.Campus.CampusListDTO;
import com.AchadosPerdidos.API.Domain.Entity.Campus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CampusModelMapper {

    public CampusDTO toDTO(Campus campus) {
        if (campus == null) {
            return null;
        }
        
        CampusDTO dto = new CampusDTO();
        dto.setId(campus.getId());
        dto.setNome(campus.getNome());
        dto.setInstituicaoId(campus.getInstituicaoId());
        dto.setEnderecoId(campus.getEnderecoId());
        dto.setDtaCriacao(campus.getDtaCriacao());
        dto.setFlgInativo(campus.getFlgInativo());
        dto.setDtaRemocao(campus.getDtaRemocao());
        
        return dto;
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
        campus.setDtaCriacao(dto.getDtaCriacao());
        campus.setFlgInativo(dto.getFlgInativo());
        campus.setDtaRemocao(dto.getDtaRemocao());
        
        return campus;
    }

    public CampusListDTO toListDTO(List<Campus> campus) {
        if (campus == null) {
            return null;
        }
        
        List<CampusDTO> dtoList = campus.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        
        CampusListDTO listDTO = new CampusListDTO();
        listDTO.setCampi(dtoList);
        listDTO.setTotalCount(dtoList.size());
        
        return listDTO;
    }
}