package com.AchadosPerdidos.API.Application.Mapper;

import com.AchadosPerdidos.API.Application.DTOs.Endereco.EnderecoDTO;
import com.AchadosPerdidos.API.Domain.Entity.Endereco;
import org.springframework.stereotype.Component;

@Component
public class EnderecoModelMapper {

    public EnderecoDTO toDTO(Endereco endereco) {
        if (endereco == null) return null;
        EnderecoDTO dto = new EnderecoDTO();
        dto.setId(endereco.getId());
        dto.setLogradouro(endereco.getLogradouro());
        dto.setNumero(endereco.getNumero());
        dto.setComplemento(endereco.getComplemento());
        dto.setBairro(endereco.getBairro());
        dto.setCep(endereco.getCep());
        dto.setCidadeId(endereco.getCidadeId());
        dto.setDtaCriacao(endereco.getDtaCriacao());
        dto.setFlgInativo(endereco.getFlgInativo());
        dto.setDtaRemocao(endereco.getDtaRemocao());
        return dto;
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
        entity.setDtaCriacao(dto.getDtaCriacao());
        entity.setFlgInativo(dto.getFlgInativo());
        entity.setDtaRemocao(dto.getDtaRemocao());
        return entity;
    }
}


