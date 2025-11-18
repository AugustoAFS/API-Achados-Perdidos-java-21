package com.AchadosPerdidos.API.Application.Mapper;

import com.AchadosPerdidos.API.Application.DTOs.FotoUsuario.FotoUsuarioCreateDTO;
import com.AchadosPerdidos.API.Application.DTOs.FotoUsuario.FotoUsuarioDTO;
import com.AchadosPerdidos.API.Application.DTOs.FotoUsuario.FotoUsuarioListDTO;
import com.AchadosPerdidos.API.Application.DTOs.FotoUsuario.FotoUsuarioUpdateDTO;
import com.AchadosPerdidos.API.Domain.Entity.FotoUsuario;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FotoUsuarioModelMapper {

    public FotoUsuarioDTO toDTO(FotoUsuario fotoUsuario) {
        if (fotoUsuario == null) {
            return null;
        }
        
        return new FotoUsuarioDTO(
            fotoUsuario.getId(),
            fotoUsuario.getUsuarioId(),
            fotoUsuario.getFotoId(),
            fotoUsuario.getDtaCriacao() != null ? Date.from(fotoUsuario.getDtaCriacao().atZone(ZoneId.systemDefault()).toInstant()) : null,
            fotoUsuario.getFlgInativo(),
            fotoUsuario.getDtaRemocao() != null ? Date.from(fotoUsuario.getDtaRemocao().atZone(ZoneId.systemDefault()).toInstant()) : null
        );
    }

    public FotoUsuario toEntity(FotoUsuarioDTO dto) {
        if (dto == null) {
            return null;
        }
        
        FotoUsuario fotoUsuario = new FotoUsuario();
        fotoUsuario.setId(dto.getId());
        fotoUsuario.setUsuarioId(dto.getUsuarioId());
        fotoUsuario.setFotoId(dto.getFotoId());
        if (dto.getDtaCriacao() != null) {
            fotoUsuario.setDtaCriacao(dto.getDtaCriacao().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        }
        fotoUsuario.setFlgInativo(dto.getFlgInativo());
        if (dto.getDtaRemocao() != null) {
            fotoUsuario.setDtaRemocao(dto.getDtaRemocao().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        }
        
        return fotoUsuario;
    }

    public FotoUsuario fromCreateDTO(FotoUsuarioCreateDTO dto) {
        if (dto == null) {
            return null;
        }
        
        FotoUsuario fotoUsuario = new FotoUsuario();
        fotoUsuario.setUsuarioId(dto.getUsuarioId());
        fotoUsuario.setFotoId(dto.getFotoId());
        
        return fotoUsuario;
    }

    public void updateFromDTO(FotoUsuario fotoUsuario, FotoUsuarioUpdateDTO dto) {
        if (fotoUsuario == null || dto == null) {
            return;
        }
        
        if (dto.getUsuarioId() != null) {
            fotoUsuario.setUsuarioId(dto.getUsuarioId());
        }
        if (dto.getFotoId() != null) {
            fotoUsuario.setFotoId(dto.getFotoId());
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
}

