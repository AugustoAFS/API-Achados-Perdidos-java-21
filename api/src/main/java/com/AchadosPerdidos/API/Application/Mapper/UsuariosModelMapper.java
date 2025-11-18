package com.AchadosPerdidos.API.Application.Mapper;

import com.AchadosPerdidos.API.Application.DTOs.Usuario.UsuariosDTO;
import com.AchadosPerdidos.API.Application.DTOs.Usuario.UsuariosCreateDTO;
import com.AchadosPerdidos.API.Application.DTOs.Usuario.UsuariosListDTO;
import com.AchadosPerdidos.API.Application.DTOs.Usuario.UsuariosUpdateDTO;
import com.AchadosPerdidos.API.Domain.Entity.Usuario;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UsuariosModelMapper {

    public UsuariosDTO toDTO(Usuario usuario) {
        if (usuario == null) {
            return null;
        }
        
        return new UsuariosDTO(
            usuario.getId(),
            usuario.getNomeCompleto(),
            usuario.getCpf(),
            usuario.getEmail(),
            usuario.getMatricula(),
            usuario.getEndereco_id(),
            usuario.getDta_Criacao() != null ? Date.from(usuario.getDta_Criacao().atZone(ZoneId.systemDefault()).toInstant()) : null,
            usuario.getFlg_Inativo(),
            usuario.getDta_Remocao() != null ? Date.from(usuario.getDta_Remocao().atZone(ZoneId.systemDefault()).toInstant()) : null
        );
    }

    public Usuario toEntity(UsuariosDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Usuario usuario = new Usuario();
        usuario.setId(dto.getId());
        usuario.setNomeCompleto(dto.getNomeCompleto());
        usuario.setCpf(dto.getCpf());
        usuario.setEmail(dto.getEmail());
        usuario.setMatricula(dto.getMatricula());
        usuario.setEndereco_id(dto.getEnderecoId());
        if (dto.getDtaCriacao() != null) {
            usuario.setDta_Criacao(dto.getDtaCriacao().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        }
        usuario.setFlg_Inativo(dto.getFlgInativo());
        if (dto.getDtaRemocao() != null) {
            usuario.setDta_Remocao(dto.getDtaRemocao().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        }
        
        return usuario;
    }

    public Usuario fromCreateDTO(UsuariosCreateDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Usuario usuario = new Usuario();
        usuario.setNomeCompleto(dto.getNomeCompleto());
        usuario.setCpf(dto.getCpf());
        usuario.setEmail(dto.getEmail());
        // Nota: O hash da senha é feito no serviço, não no mapper
        usuario.setMatricula(dto.getMatricula());
        usuario.setNumero_telefone(dto.getNumeroTelefone());
        usuario.setEndereco_id(dto.getEnderecoId());
        
        return usuario;
    }

    public void updateFromDTO(Usuario usuario, UsuariosUpdateDTO dto) {
        if (usuario == null || dto == null) {
            return;
        }
        
        if (dto.getNomeCompleto() != null) {
            usuario.setNomeCompleto(dto.getNomeCompleto());
        }
        if (dto.getCpf() != null) {
            usuario.setCpf(dto.getCpf());
        }
        if (dto.getEmail() != null) {
            usuario.setEmail(dto.getEmail());
        }
        if (dto.getMatricula() != null) {
            usuario.setMatricula(dto.getMatricula());
        }
        if (dto.getEnderecoId() != null) {
            usuario.setEndereco_id(dto.getEnderecoId());
        }
        if (dto.getFlgInativo() != null) {
            usuario.setFlg_Inativo(dto.getFlgInativo());
        }
    }

    public UsuariosCreateDTO toCreateDTO(Usuario usuario) {
        if (usuario == null) {
            return null;
        }
        
        return new UsuariosCreateDTO(
            usuario.getNomeCompleto(),
            usuario.getCpf(),
            usuario.getEmail(),
            null, // senha não é retornada
            usuario.getMatricula(),
            usuario.getNumero_telefone(),
            usuario.getEndereco_id(),
            null // campusId não está na entidade Usuario, está na tabela usuario_campus
        );
    }

    public UsuariosUpdateDTO toUpdateDTO(Usuario usuario) {
        if (usuario == null) {
            return null;
        }
        
        return new UsuariosUpdateDTO(
            usuario.getNomeCompleto(),
            usuario.getCpf(),
            usuario.getEmail(),
            usuario.getMatricula(),
            usuario.getEndereco_id(),
            usuario.getFlg_Inativo()
        );
    }

    public UsuariosListDTO toListDTO(List<Usuario> usuarios) {
        if (usuarios == null) {
            return null;
        }
        
        List<UsuariosDTO> dtoList = usuarios.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        
        return new UsuariosListDTO(dtoList, dtoList.size());
    }

    public UsuariosListDTO toListDTO(Usuario usuario) {
        if (usuario == null) {
            return null;
        }
        
        UsuariosDTO dto = toDTO(usuario);
        List<UsuariosDTO> dtoList = new ArrayList<>();
        dtoList.add(dto);
        
        return new UsuariosListDTO(dtoList, 1);
    }
}