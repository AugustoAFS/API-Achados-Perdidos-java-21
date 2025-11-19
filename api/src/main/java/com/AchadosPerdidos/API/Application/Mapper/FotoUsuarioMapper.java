package com.AchadosPerdidos.API.Application.Mapper;

import com.AchadosPerdidos.API.Application.DTOs.FotoUsuario.FotoUsuarioCreateDTO;
import com.AchadosPerdidos.API.Application.DTOs.FotoUsuario.FotoUsuarioDTO;
import com.AchadosPerdidos.API.Application.DTOs.FotoUsuario.FotoUsuarioListDTO;
import com.AchadosPerdidos.API.Application.DTOs.FotoUsuario.FotoUsuarioUpdateDTO;
import com.AchadosPerdidos.API.Domain.Entity.Foto;
import com.AchadosPerdidos.API.Domain.Entity.FotoUsuario;
import com.AchadosPerdidos.API.Domain.Entity.Usuario;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FotoUsuarioMapper {

    public FotoUsuarioDTO toDTO(FotoUsuario fotoUsuario) {
        if (fotoUsuario == null) {
            return null;
        }
        
        Integer usuarioId = fotoUsuario.getUsuarioId() != null ? fotoUsuario.getUsuarioId().getId() : null;
        Integer fotoId = fotoUsuario.getFotoId() != null ? fotoUsuario.getFotoId().getId() : null;

        return new FotoUsuarioDTO(
            fotoUsuario.getId(),
            usuarioId,
            fotoId,
            fotoUsuario.getDtaCriacao(),
            fotoUsuario.getFlgInativo(),
            fotoUsuario.getDtaRemocao()
        );
    }

    public FotoUsuario toEntity(FotoUsuarioDTO dto) {
        if (dto == null) {
            return null;
        }
        
        FotoUsuario fotoUsuario = new FotoUsuario();
        fotoUsuario.setId(dto.getId());
        fotoUsuario.setUsuarioId(toUsuario(dto.getUsuarioId()));
        fotoUsuario.setFotoId(toFoto(dto.getFotoId()));
        fotoUsuario.setDtaCriacao(dto.getDtaCriacao());
        fotoUsuario.setFlgInativo(dto.getFlgInativo());
        fotoUsuario.setDtaRemocao(dto.getDtaRemocao());
        
        return fotoUsuario;
    }

    public FotoUsuario fromCreateDTO(FotoUsuarioCreateDTO dto) {
        if (dto == null) {
            return null;
        }
        
        FotoUsuario fotoUsuario = new FotoUsuario();
        fotoUsuario.setUsuarioId(toUsuario(dto.getUsuarioId()));
        fotoUsuario.setFotoId(toFoto(dto.getFotoId()));
        
        return fotoUsuario;
    }

    public void updateFromDTO(FotoUsuario fotoUsuario, FotoUsuarioUpdateDTO dto) {
        if (fotoUsuario == null || dto == null) {
            return;
        }
        
        if (dto.getUsuarioId() != null) {
            fotoUsuario.setUsuarioId(toUsuario(dto.getUsuarioId()));
        }
        if (dto.getFotoId() != null) {
            fotoUsuario.setFotoId(toFoto(dto.getFotoId()));
        }
        if (dto.getFlgInativo() != null) {
            fotoUsuario.setFlgInativo(dto.getFlgInativo());
        }
    }

    public FotoUsuarioListDTO toListDTO(List<FotoUsuario> fotoUsuarios) {
        if (fotoUsuarios == null) {
            return null;
        }
        
        List<FotoUsuarioDTO> dtoList = fotoUsuarios.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        
        return new FotoUsuarioListDTO(dtoList, dtoList.size());
    }

    private Usuario toUsuario(Integer id) {
        if (id == null) {
            return null;
        }
        Usuario usuario = new Usuario();
        usuario.setId(id);
        return usuario;
    }

    private Foto toFoto(Integer id) {
        if (id == null) {
            return null;
        }
        Foto foto = new Foto();
        foto.setId(id);
        return foto;
    }
}

