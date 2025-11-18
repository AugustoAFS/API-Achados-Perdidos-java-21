package com.AchadosPerdidos.API.Application.Mapper;

import com.AchadosPerdidos.API.Application.DTOs.Instituicao.InstituicaoDTO;
import com.AchadosPerdidos.API.Application.DTOs.Instituicao.InstituicaoListDTO;
import com.AchadosPerdidos.API.Domain.Entity.Instituicoes;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class InstituicaoModelMapper {

    public InstituicaoDTO toDTO(Instituicoes instituicao) {
        if (instituicao == null) {
            return null;
        }
        
        return new InstituicaoDTO(
            instituicao.getId(),
            instituicao.getNome(),
            instituicao.getCodigo(),
            instituicao.getTipo(),
            instituicao.getCnpj(),
            instituicao.getDtaCriacao() != null ? Date.from(instituicao.getDtaCriacao().atZone(ZoneId.systemDefault()).toInstant()) : null,
            instituicao.getFlgInativo(),
            instituicao.getDtaRemocao() != null ? Date.from(instituicao.getDtaRemocao().atZone(ZoneId.systemDefault()).toInstant()) : null
        );
    }

    public Instituicoes toEntity(InstituicaoDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Instituicoes instituicao = new Instituicoes();
        instituicao.setId(dto.getId());
        instituicao.setNome(dto.getNome());
        instituicao.setCodigo(dto.getCodigo());
        instituicao.setTipo(dto.getTipo());
        instituicao.setCnpj(dto.getCnpj());
        if (dto.getDtaCriacao() != null) {
            instituicao.setDtaCriacao(dto.getDtaCriacao().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        }
        instituicao.setFlgInativo(dto.getFlgInativo());
        if (dto.getDtaRemocao() != null) {
            instituicao.setDtaRemocao(dto.getDtaRemocao().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        }
        
        return instituicao;
    }

    public InstituicaoListDTO toListDTO(List<Instituicoes> instituicoes) {
        if (instituicoes == null) {
            return null;
        }
        
        List<InstituicaoDTO> dtoList = instituicoes.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        
        return new InstituicaoListDTO(dtoList, dtoList.size());
    }
}
