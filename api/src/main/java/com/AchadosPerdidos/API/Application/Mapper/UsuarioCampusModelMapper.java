package com.AchadosPerdidos.API.Application.Mapper;

import com.AchadosPerdidos.API.Application.DTOs.UsuarioCampus.UsuarioCampusCreateDTO;
import com.AchadosPerdidos.API.Application.DTOs.UsuarioCampus.UsuarioCampusDTO;
import com.AchadosPerdidos.API.Application.DTOs.UsuarioCampus.UsuarioCampusListDTO;
import com.AchadosPerdidos.API.Application.DTOs.UsuarioCampus.UsuarioCampusUpdateDTO;
import com.AchadosPerdidos.API.Domain.Entity.UsuarioCampus;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UsuarioCampusModelMapper {

    public UsuarioCampusDTO toDTO(UsuarioCampus usuarioCampus) {
        if (usuarioCampus == null) {
            return null;
        }
        
        return new UsuarioCampusDTO(
            usuarioCampus.getId(),
            usuarioCampus.getUsuario_id(),
            usuarioCampus.getCampus_id(),
            usuarioCampus.getDta_Criacao() != null ? Date.from(usuarioCampus.getDta_Criacao().atZone(ZoneId.systemDefault()).toInstant()) : null,
            usuarioCampus.getFlg_Inativo(),
            usuarioCampus.getDtaRemocao() != null ? Date.from(usuarioCampus.getDtaRemocao().atZone(ZoneId.systemDefault()).toInstant()) : null
        );
    }

    public UsuarioCampus toEntity(UsuarioCampusDTO dto) {
        if (dto == null) {
            return null;
        }
        
        UsuarioCampus usuarioCampus = new UsuarioCampus();
        usuarioCampus.setId(dto.getId());
        usuarioCampus.setUsuario_id(dto.getUsuarioId());
        usuarioCampus.setCampus_id(dto.getCampusId());
        if (dto.getDtaCriacao() != null) {
            usuarioCampus.setDta_Criacao(dto.getDtaCriacao().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        }
        usuarioCampus.setFlg_Inativo(dto.getFlgInativo());
        if (dto.getDtaRemocao() != null) {
            usuarioCampus.setDtaRemocao(dto.getDtaRemocao().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        }
        
        return usuarioCampus;
    }

    public UsuarioCampus fromCreateDTO(UsuarioCampusCreateDTO dto) {
        if (dto == null) {
            return null;
        }
        
        UsuarioCampus usuarioCampus = new UsuarioCampus();
        usuarioCampus.setUsuario_id(dto.getUsuarioId());
        usuarioCampus.setCampus_id(dto.getCampusId());
        
        return usuarioCampus;
    }

    public void updateFromDTO(UsuarioCampus usuarioCampus, UsuarioCampusUpdateDTO dto) {
        if (usuarioCampus == null || dto == null) {
            return;
        }
        
        if (dto.getUsuarioId() != null) {
            usuarioCampus.setUsuario_id(dto.getUsuarioId());
        }
        if (dto.getCampusId() != null) {
            usuarioCampus.setCampus_id(dto.getCampusId());
        }
        if (dto.getFlgInativo() != null) {
            usuarioCampus.setFlg_Inativo(dto.getFlgInativo());
        }
    }

    public UsuarioCampusListDTO toListDTO(List<UsuarioCampus> usuarioCampus) {
        if (usuarioCampus == null) {
            return null;
        }
        
        List<UsuarioCampusDTO> dtoList = usuarioCampus.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        
        return new UsuarioCampusListDTO(dtoList, dtoList.size());
    }
}

