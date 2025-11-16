package com.AchadosPerdidos.API.Application.Mapper;

import com.AchadosPerdidos.API.Application.DTOs.Usuario.UsuariosDTO;
import com.AchadosPerdidos.API.Application.DTOs.Usuario.UsuariosCreateDTO;
import com.AchadosPerdidos.API.Application.DTOs.Usuario.UsuariosListDTO;
import com.AchadosPerdidos.API.Application.DTOs.Usuario.UsuariosUpdateDTO;
import com.AchadosPerdidos.API.Domain.Entity.Usuarios;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UsuariosModelMapper {

    public UsuariosDTO toDTO(Usuarios usuarios) {
        if (usuarios == null) {
            return null;
        }
        
        return new UsuariosDTO(
            usuarios.getId(),
            usuarios.getNomeCompleto(),
            usuarios.getCpf(),
            usuarios.getEmail(),
            usuarios.getMatricula(),
            usuarios.getEmpresaId(),
            usuarios.getEnderecoId(),
            usuarios.getDtaCriacao(),
            usuarios.getFlgInativo(),
            usuarios.getDtaRemocao()
        );
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
    usuarios.setMatricula(dto.getMatricula());
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
        // Nota: O hash da senha é feito no serviço, não no mapper
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
        if (dto.getMatricula() != null) {
            usuarios.setMatricula(dto.getMatricula());
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
        
        return new UsuariosCreateDTO(
            usuarios.getNomeCompleto(),
            usuarios.getCpf(),
            usuarios.getEmail(),
            null, // senha não é retornada
            usuarios.getMatricula(),
            usuarios.getNumeroTelefone(),
            usuarios.getEnderecoId(),
            null // campusId não está na entidade Usuarios, está na tabela usuario_campus
        );
    }

    public UsuariosUpdateDTO toUpdateDTO(Usuarios usuarios) {
        if (usuarios == null) {
            return null;
        }
        
        return new UsuariosUpdateDTO(
            usuarios.getNomeCompleto(),
            usuarios.getCpf(),
            usuarios.getEmail(),
            usuarios.getMatricula(),
            usuarios.getEmpresaId(),
            usuarios.getEnderecoId(),
            usuarios.getFlgInativo()
        );
    }

    public UsuariosListDTO toListDTO(List<Usuarios> usuarios) {
        if (usuarios == null) {
            return null;
        }
        
        List<UsuariosDTO> dtoList = usuarios.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        
        return new UsuariosListDTO(dtoList, dtoList.size());
    }

    public UsuariosListDTO toListDTO(Usuarios usuario) {
        if (usuario == null) {
            return null;
        }
        
        UsuariosDTO dto = toDTO(usuario);
        List<UsuariosDTO> dtoList = new ArrayList<>();
        dtoList.add(dto);
        
        return new UsuariosListDTO(dtoList, 1);
    }
}