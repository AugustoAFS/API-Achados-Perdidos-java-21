package com.AchadosPerdidos.API.Application.Mapper;

import com.AchadosPerdidos.API.Application.DTOs.DeviceToken.DeviceTokenCreateDTO;
import com.AchadosPerdidos.API.Application.DTOs.DeviceToken.DeviceTokenDTO;
import com.AchadosPerdidos.API.Application.DTOs.DeviceToken.DeviceTokenListDTO;
import com.AchadosPerdidos.API.Application.DTOs.DeviceToken.DeviceTokenUpdateDTO;
import com.AchadosPerdidos.API.Domain.Entity.DeviceToken;
import com.AchadosPerdidos.API.Domain.Entity.Usuario;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DeviceTokenMapper {

    public DeviceTokenDTO toDTO(DeviceToken deviceToken) {
        if (deviceToken == null) {
            return null;
        }

        Integer usuarioId = deviceToken.getUsuario_id() != null ? deviceToken.getUsuario_id().getId() : null;

        return new DeviceTokenDTO(
            deviceToken.getId(),
            usuarioId,
            deviceToken.getToken(),
            deviceToken.getPlataforma(),
            deviceToken.getDta_Criacao(),
            deviceToken.getDta_Atualizacao(),
            deviceToken.getFlg_Inativo(),
            deviceToken.getDta_Remocao()
        );
    }

    public DeviceToken toEntity(DeviceTokenCreateDTO createDTO) {
        if (createDTO == null) {
            return null;
        }

        DeviceToken deviceToken = new DeviceToken();
        deviceToken.setUsuario_id(toUsuario(createDTO.getUsuarioId()));
        deviceToken.setToken(createDTO.getToken());
        deviceToken.setPlataforma(createDTO.getPlataforma() != null ? createDTO.getPlataforma().toUpperCase() : null);
        deviceToken.setDta_Criacao(java.time.LocalDateTime.now());
        deviceToken.setFlg_Inativo(false);

        return deviceToken;
    }

    public DeviceToken toEntity(DeviceTokenUpdateDTO updateDTO, DeviceToken existingEntity) {
        if (updateDTO == null || existingEntity == null) {
            return existingEntity;
        }

        if (updateDTO.getToken() != null) {
            existingEntity.setToken(updateDTO.getToken());
        }
        
        if (updateDTO.getPlataforma() != null) {
            existingEntity.setPlataforma(updateDTO.getPlataforma().toUpperCase());
        }
        
        if (updateDTO.getFlgInativo() != null) {
            existingEntity.setFlg_Inativo(updateDTO.getFlgInativo());
            if (updateDTO.getFlgInativo()) {
                existingEntity.setDta_Remocao(java.time.LocalDateTime.now());
            } else {
                existingEntity.setDta_Remocao(null);
            }
        }
        
        existingEntity.setDta_Atualizacao(java.time.LocalDateTime.now());

        return existingEntity;
    }

    public DeviceTokenListDTO toListDTO(List<DeviceToken> deviceTokens) {
        if (deviceTokens == null) {
            return new DeviceTokenListDTO();
        }

        List<DeviceTokenDTO> dtos = deviceTokens.stream()
            .map(this::toDTO)
            .collect(Collectors.toList());

        return new DeviceTokenListDTO(dtos);
    }

    private Usuario toUsuario(Integer id) {
        if (id == null) {
            return null;
        }
        Usuario usuario = new Usuario();
        usuario.setId(id);
        return usuario;
    }
}

