package com.AchadosPerdidos.API.Application.Mapper;

import com.AchadosPerdidos.API.Application.DTOs.Endereco.EnderecoDTO;
import com.AchadosPerdidos.API.Domain.Entity.Endereco;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.Date;

@Component
public class EnderecoModelMapper {

    public EnderecoDTO toDTO(Endereco endereco) {
        if (endereco == null) return null;
        return new EnderecoDTO(
            endereco.getId(),
            endereco.getLogradouro(),
            endereco.getNumero(),
            endereco.getComplemento(),
            endereco.getBairro(),
            endereco.getCep(),
            endereco.getCidadeId(),
            endereco.getDtaCriacao() != null ? Date.from(endereco.getDtaCriacao().atZone(ZoneId.systemDefault()).toInstant()) : null,
            endereco.getFlgInativo(),
            endereco.getDtaRemocao() != null ? Date.from(endereco.getDtaRemocao().atZone(ZoneId.systemDefault()).toInstant()) : null
        );
    }

    public Endereco toEntity(EnderecoDTO dto) {
        if (dto == null) return null;
        Endereco entity = new Endereco();
        entity.setId(dto.getId());
        entity.setLogradouro(dto.getLogradouro());
        entity.setNumero(dto.getNumero());
        entity.setComplemento(dto.getComplemento());
        entity.setBairro(dto.getBairro());
        entity.setCep(dto.getCep());
        entity.setCidadeId(dto.getCidadeId());
        if (dto.getDtaCriacao() != null) {
            entity.setDtaCriacao(dto.getDtaCriacao().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        }
        entity.setFlgInativo(dto.getFlgInativo());
        // Endereco não tem setDtaRemocao na entidade, apenas getter
        return entity;
    }
}


