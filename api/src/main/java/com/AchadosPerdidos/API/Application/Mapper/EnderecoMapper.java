package com.AchadosPerdidos.API.Application.Mapper;

import com.AchadosPerdidos.API.Application.DTOs.Endereco.EnderecoDTO;
import com.AchadosPerdidos.API.Domain.Entity.Cidade;
import com.AchadosPerdidos.API.Domain.Entity.Endereco;
import org.springframework.stereotype.Component;

@Component
public class EnderecoMapper {

    public EnderecoDTO toDTO(Endereco endereco) {
        if (endereco == null) return null;
        Integer cidadeId = endereco.getCidadeId() != null ? endereco.getCidadeId().getId() : null;

        return new EnderecoDTO(
            endereco.getId(),
            endereco.getLogradouro(),
            endereco.getNumero(),
            endereco.getComplemento(),
            endereco.getBairro(),
            endereco.getCep(),
            cidadeId,
            endereco.getDtaCriacao(),
            endereco.getFlgInativo(),
            endereco.getDtaRemocao()
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
        entity.setCidadeId(toCidade(dto.getCidadeId()));
        entity.setDtaCriacao(dto.getDtaCriacao());
        entity.setFlgInativo(dto.getFlgInativo());
        // Endereco não tem setDtaRemocao na entidade, apenas getter
        return entity;
    }

    private Cidade toCidade(Integer id) {
        if (id == null) {
            return null;
        }
        Cidade cidade = new Cidade();
        cidade.setId(id);
        return cidade;
    }
}


