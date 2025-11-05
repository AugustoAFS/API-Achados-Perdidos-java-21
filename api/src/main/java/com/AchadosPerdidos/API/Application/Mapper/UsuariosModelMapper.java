package com.AchadosPerdidos.API.Application.Mapper;

import com.AchadosPerdidos.API.Application.DTOs.Usuario.UsuariosDTO;
import com.AchadosPerdidos.API.Application.DTOs.Usuario.UsuariosCreateDTO;
import com.AchadosPerdidos.API.Application.DTOs.Usuario.UsuariosListDTO;
import com.AchadosPerdidos.API.Application.DTOs.Usuario.UsuariosUpdateDTO;
import com.AchadosPerdidos.API.Domain.Entity.Usuarios;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UsuariosModelMapper {

    @Autowired
    private PasswordEncoder passwordEncoder;

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

    public Usuarios toEntity(UsuariosCreateDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Usuarios usuarios = new Usuarios();
        usuarios.setNomeCompleto(dto.getNomeCompleto());
        usuarios.setCpf(dto.getCpf());
        usuarios.setEmail(dto.getEmail());
        usuarios.setHashSenha(passwordEncoder.encode(dto.getSenha()));
        usuarios.setMatricula(dto.getMatricula());
        usuarios.setNumeroTelefone(dto.getNumeroTelefone());
        usuarios.setEnderecoId(dto.getEnderecoId());
        
        return usuarios;
    }

    public void updateEntityFromUpdateDTO(Usuarios usuarios, UsuariosUpdateDTO dto) {
        if (usuarios == null || dto == null) {
            return;
        }
        
        if (dto.getNomeCompleto() != null) {
            usuarios.setNomeCompleto(dto.getNomeCompleto());
        }
        if (dto.getCpf() != null) {
            usuarios.setCpf(dto.getCpf());
        }
        if (dto.getEmail() != null) {
            usuarios.setEmail(dto.getEmail());
        }
        if (dto.getSenha() != null && !dto.getSenha().isEmpty()) {
            usuarios.setHashSenha(passwordEncoder.encode(dto.getSenha()));
        }
        if (dto.getMatricula() != null) {
            usuarios.setMatricula(dto.getMatricula());
        }
        if (dto.getNumeroTelefone() != null) {
            usuarios.setNumeroTelefone(dto.getNumeroTelefone());
        }
        if (dto.getEmpresaId() != null) {
            usuarios.setEmpresaId(dto.getEmpresaId());
        }
        if (dto.getEnderecoId() != null) {
            usuarios.setEnderecoId(dto.getEnderecoId());
        }
        if (dto.getFlgInativo() != null) {
            usuarios.setFlgInativo(dto.getFlgInativo());
        }
    }

    public UsuariosCreateDTO toCreateDTO(Usuarios usuarios) {
        if (usuarios == null) {
            return null;
        }
        
        UsuariosCreateDTO dto = new UsuariosCreateDTO();
        dto.setNomeCompleto(usuarios.getNomeCompleto());
        dto.setCpf(usuarios.getCpf());
        dto.setEmail(usuarios.getEmail());
        dto.setMatricula(usuarios.getMatricula());
        dto.setNumeroTelefone(usuarios.getNumeroTelefone());
        dto.setEnderecoId(usuarios.getEnderecoId());
        
        return dto;
    }

    public UsuariosUpdateDTO toUpdateDTO(Usuarios usuarios) {
        if (usuarios == null) {
            return null;
        }
        
        UsuariosUpdateDTO dto = new UsuariosUpdateDTO();
        dto.setNomeCompleto(usuarios.getNomeCompleto());
        dto.setCpf(usuarios.getCpf());
        dto.setEmail(usuarios.getEmail());
        dto.setMatricula(usuarios.getMatricula());
        dto.setNumeroTelefone(usuarios.getNumeroTelefone());
        dto.setEmpresaId(usuarios.getEmpresaId());
        dto.setEnderecoId(usuarios.getEnderecoId());
        dto.setFlgInativo(usuarios.getFlgInativo());
        
        return dto;
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

    public UsuariosListDTO toListDTO(Usuarios usuario) {
        if (usuario == null) {
            return null;
        }
        
        UsuariosDTO dto = toDTO(usuario);
        UsuariosListDTO listDTO = new UsuariosListDTO();
        List<UsuariosDTO> dtoList = new ArrayList<>();
        dtoList.add(dto);
        listDTO.setUsuarios(dtoList);
        listDTO.setTotalCount(1);
        
        return listDTO;
    }
}