package com.AchadosPerdidos.API.Application.Mapper;

import com.AchadosPerdidos.API.Application.DTOs.Instituicao.InstituicaoDTO;
import com.AchadosPerdidos.API.Application.DTOs.Instituicao.InstituicaoListDTO;
import com.AchadosPerdidos.API.Domain.Entity.Instituicao;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class InstituicaoModelMapper {

    public InstituicaoDTO toDTO(Instituicao instituicao) {
        if (instituicao == null) {
            return null;
        }
        
        InstituicaoDTO dto = new InstituicaoDTO();
        dto.setId(instituicao.getId());
        dto.setNome(instituicao.getNome());
        dto.setCodigo(instituicao.getCodigo());
        dto.setTipo(instituicao.getTipo());
        dto.setCnpj(instituicao.getCnpj());
        dto.setDtaCriacao(instituicao.getDtaCriacao());
        dto.setFlgInativo(instituicao.getFlgInativo());
        dto.setDtaRemocao(instituicao.getDtaRemocao());
        
        return dto;
    }

    public Instituicao toEntity(InstituicaoDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Instituicao instituicao = new Instituicao();
        instituicao.setId(dto.getId());
        instituicao.setNome(dto.getNome());
        instituicao.setCodigo(dto.getCodigo());
        instituicao.setTipo(dto.getTipo());
        instituicao.setCnpj(dto.getCnpj());
        instituicao.setDtaCriacao(dto.getDtaCriacao());
        instituicao.setFlgInativo(dto.getFlgInativo());
        instituicao.setDtaRemocao(dto.getDtaRemocao());
        
        return instituicao;
    }

    public InstituicaoListDTO toListDTO(List<Instituicao> instituicoes) {
        if (instituicoes == null) {
            return null;
        }
        
        List<InstituicaoDTO> dtoList = instituicoes.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        
        InstituicaoListDTO listDTO = new InstituicaoListDTO();
        listDTO.setInstituicoes(dtoList);
        listDTO.setTotalCount(dtoList.size());
        
        return listDTO;
    }
}
