package com.AchadosPerdidos.API.Application.Mapper;

import com.AchadosPerdidos.API.Application.DTOs.UsuarioCampus.UsuarioCampusCreateDTO;
import com.AchadosPerdidos.API.Application.DTOs.UsuarioCampus.UsuarioCampusDTO;
import com.AchadosPerdidos.API.Application.DTOs.UsuarioCampus.UsuarioCampusListDTO;
import com.AchadosPerdidos.API.Application.DTOs.UsuarioCampus.UsuarioCampusUpdateDTO;
import com.AchadosPerdidos.API.Domain.Entity.Campus;
import com.AchadosPerdidos.API.Domain.Entity.Usuario;
import com.AchadosPerdidos.API.Domain.Entity.UsuarioCampus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UsuarioCampusMapper {

    public UsuarioCampusDTO toDTO(UsuarioCampus usuarioCampus) {
        if (usuarioCampus == null) {
            return null;
        }
        
        Integer usuarioId = usuarioCampus.getUsuario_id() != null ? usuarioCampus.getUsuario_id().getId() : null;
        Integer campusId = usuarioCampus.getCampus_id() != null ? usuarioCampus.getCampus_id().getId() : null;

        return new UsuarioCampusDTO(
            usuarioCampus.getId(),
            usuarioId,
            campusId,
            usuarioCampus.getDta_Criacao(),
            usuarioCampus.getFlg_Inativo(),
            usuarioCampus.getDtaRemocao()
        );
    }

    public UsuarioCampus toEntity(UsuarioCampusDTO dto) {
        if (dto == null) {
            return null;
        }
        
        UsuarioCampus usuarioCampus = new UsuarioCampus();
        usuarioCampus.setId(dto.getId());
        usuarioCampus.setUsuario_id(toUsuario(dto.getUsuarioId()));
        usuarioCampus.setCampus_id(toCampus(dto.getCampusId()));
        usuarioCampus.setDta_Criacao(dto.getDtaCriacao());
        usuarioCampus.setFlg_Inativo(dto.getFlgInativo());
        usuarioCampus.setDtaRemocao(dto.getDtaRemocao());
        
        return usuarioCampus;
    }

    public UsuarioCampus fromCreateDTO(UsuarioCampusCreateDTO dto) {
        if (dto == null) {
            return null;
        }
        
        UsuarioCampus usuarioCampus = new UsuarioCampus();
        usuarioCampus.setUsuario_id(toUsuario(dto.getUsuarioId()));
        usuarioCampus.setCampus_id(toCampus(dto.getCampusId()));
        
        return usuarioCampus;
    }

    public void updateFromDTO(UsuarioCampus usuarioCampus, UsuarioCampusUpdateDTO dto) {
        if (usuarioCampus == null || dto == null) {
            return;
        }
        
        if (dto.getUsuarioId() != null) {
            usuarioCampus.setUsuario_id(toUsuario(dto.getUsuarioId()));
        }
        if (dto.getCampusId() != null) {
            usuarioCampus.setCampus_id(toCampus(dto.getCampusId()));
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

    private Usuario toUsuario(Integer id) {
        if (id == null) {
            return null;
        }
        Usuario usuario = new Usuario();
        usuario.setId(id);
        return usuario;
    }

    private Campus toCampus(Integer id) {
        if (id == null) {
            return null;
        }
        Campus campus = new Campus();
        campus.setId(id);
        return campus;
    }
}

