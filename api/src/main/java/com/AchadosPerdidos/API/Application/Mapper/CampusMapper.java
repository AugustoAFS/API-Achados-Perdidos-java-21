package com.AchadosPerdidos.API.Application.Mapper;

import com.AchadosPerdidos.API.Application.DTOs.Campus.CampusDTO;
import com.AchadosPerdidos.API.Application.DTOs.Campus.CampusListDTO;
import com.AchadosPerdidos.API.Domain.Entity.Campus;
import com.AchadosPerdidos.API.Domain.Entity.Endereco;
import com.AchadosPerdidos.API.Domain.Entity.Instituicoes;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CampusMapper {

    public CampusDTO toDTO(Campus campus) {
        if (campus == null) {
            return null;
        }
        
        Integer instituicaoId = campus.getInstituicaoId() != null ? campus.getInstituicaoId().getId() : null;
        Integer enderecoId = campus.getEnderecoId() != null ? campus.getEnderecoId().getId() : null;

        return new CampusDTO(
            campus.getId(),
            campus.getNome(),
            instituicaoId,
            enderecoId,
            campus.getDtaCriacao(),
            campus.getFlgInativo(),
            campus.getDtaRemocao()
        );
    }

    public Campus toEntity(CampusDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Campus campus = new Campus();
        campus.setId(dto.getId());
        campus.setNome(dto.getNome());
        campus.setInstituicaoId(toInstituicao(dto.getInstituicaoId()));
        campus.setEnderecoId(toEndereco(dto.getEnderecoId()));
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
        
        return new CampusListDTO(dtoList, dtoList.size());
    }

    private Instituicoes toInstituicao(Integer id) {
        if (id == null) {
            return null;
        }
        Instituicoes instituicao = new Instituicoes();
        instituicao.setId(id);
        return instituicao;
    }

    private Endereco toEndereco(Integer id) {
        if (id == null) {
            return null;
        }
        Endereco endereco = new Endereco();
        endereco.setId(id);
        return endereco;
    }
}