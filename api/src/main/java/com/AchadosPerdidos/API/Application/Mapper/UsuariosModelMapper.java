package com.AchadosPerdidos.API.Application.Mapper;

import com.AchadosPerdidos.API.Application.DTOs.Usuario.UsuariosDTO;
import com.AchadosPerdidos.API.Application.DTOs.Usuario.UsuariosListDTO;
import com.AchadosPerdidos.API.Domain.Entity.Usuarios;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UsuariosModelMapper {

    public UsuariosDTO toDTO(Usuarios usuarios) {
        if (usuarios == null) {
            return null;
        }
        
        UsuariosDTO dto = new UsuariosDTO();
        dto.setId(usuarios.getId());
        dto.setNomeCompleto(usuarios.getNomeCompleto());
        dto.setCpf(usuarios.getCpf());
        dto.setEmail(usuarios.getEmail());
        dto.setHashSenha(usuarios.getHashSenha());
        dto.setMatricula(usuarios.getMatricula());
        dto.setNumeroTelefone(usuarios.getNumeroTelefone());
        dto.setEmpresaId(usuarios.getEmpresaId());
        dto.setEnderecoId(usuarios.getEnderecoId());
        dto.setDtaCriacao(usuarios.getDtaCriacao());
        dto.setFlgInativo(usuarios.getFlgInativo());
        dto.setDtaRemocao(usuarios.getDtaRemocao());
        
        return dto;
    }

    public Usuarios toEntity(UsuariosDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Usuarios usuarios = new Usuarios();
        usuarios.setId(dto.getId());
        usuarios.setNomeCompleto(dto.getNomeCompleto());
        usuarios.setCpf(dto.getCpf());
        usuarios.setEmail(dto.getEmail());
        usuarios.setHashSenha(dto.getHashSenha());
        usuarios.setMatricula(dto.getMatricula());
        usuarios.setNumeroTelefone(dto.getNumeroTelefone());
        usuarios.setEmpresaId(dto.getEmpresaId());
        usuarios.setEnderecoId(dto.getEnderecoId());
        usuarios.setDtaCriacao(dto.getDtaCriacao());
        usuarios.setFlgInativo(dto.getFlgInativo());
        usuarios.setDtaRemocao(dto.getDtaRemocao());
        
        return usuarios;
    }

    public UsuariosListDTO toListDTO(List<Usuarios> usuarios) {
        if (usuarios == null) {
            return null;
        }
        
        List<UsuariosDTO> dtoList = usuarios.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        
        UsuariosListDTO listDTO = new UsuariosListDTO();
        listDTO.setUsuarios(dtoList);
        listDTO.setTotalCount(dtoList.size());
        
        return listDTO;
    }
}