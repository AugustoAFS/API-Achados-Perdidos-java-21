package com.AchadosPerdidos.API.Application.Mapper;

import com.AchadosPerdidos.API.Application.DTOs.Usuario.UsuariosDTO;
import com.AchadosPerdidos.API.Application.DTOs.Usuario.UsuariosCreateDTO;
import com.AchadosPerdidos.API.Application.DTOs.Usuario.UsuariosListDTO;
import com.AchadosPerdidos.API.Application.DTOs.Usuario.UsuariosUpdateDTO;
import com.AchadosPerdidos.API.Domain.Entity.Endereco;
import com.AchadosPerdidos.API.Domain.Entity.Usuario;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UsuariosMapper {

    public UsuariosDTO toDTO(Usuario usuario) {
        if (usuario == null) {
            return null;
        }
        
        Integer enderecoId = usuario.getEndereco_id() != null ? usuario.getEndereco_id().getId() : null;

        return new UsuariosDTO(
            usuario.getId(),
            usuario.getNomeCompleto(),
            usuario.getCpf(),
            usuario.getEmail(),
            usuario.getMatricula(),
            enderecoId,
            usuario.getDta_Criacao(),
            usuario.getFlg_Inativo(),
            usuario.getDta_Remocao()
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
        usuario.setEndereco_id(toEndereco(dto.getEnderecoId()));
        usuario.setDta_Criacao(dto.getDtaCriacao());
        usuario.setFlg_Inativo(dto.getFlgInativo());
        usuario.setDta_Remocao(dto.getDtaRemocao());
        
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
        usuario.setEndereco_id(toEndereco(dto.getEnderecoId()));
        
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
            usuario.setEndereco_id(toEndereco(dto.getEnderecoId()));
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
            usuario.getEndereco_id() != null ? usuario.getEndereco_id().getId() : null,
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
            usuario.getEndereco_id() != null ? usuario.getEndereco_id().getId() : null,
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

    private Endereco toEndereco(Integer id) {
        if (id == null) {
            return null;
        }
        Endereco endereco = new Endereco();
        endereco.setId(id);
        return endereco;
    }
}