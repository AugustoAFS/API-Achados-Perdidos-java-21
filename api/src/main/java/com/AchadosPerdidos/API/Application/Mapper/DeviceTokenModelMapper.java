package com.AchadosPerdidos.API.Application.Mapper;

import com.AchadosPerdidos.API.Application.DTOs.DeviceToken.DeviceTokenCreateDTO;
import com.AchadosPerdidos.API.Application.DTOs.DeviceToken.DeviceTokenDTO;
import com.AchadosPerdidos.API.Application.DTOs.DeviceToken.DeviceTokenListDTO;
import com.AchadosPerdidos.API.Application.DTOs.DeviceToken.DeviceTokenUpdateDTO;
import com.AchadosPerdidos.API.Domain.Entity.DeviceToken;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DeviceTokenModelMapper {

    public DeviceTokenDTO toDTO(DeviceToken deviceToken) {
        if (deviceToken == null) {
            return null;
        }

        DeviceTokenDTO dto = new DeviceTokenDTO();
        dto.setId(deviceToken.getId());
        dto.setUsuarioId(deviceToken.getUsuario_id());
        dto.setToken(deviceToken.getToken());
        dto.setPlataforma(deviceToken.getPlataforma());
        
        if (deviceToken.getDta_Criacao() != null) {
            dto.setDtaCriacao(Date.from(deviceToken.getDta_Criacao().atZone(ZoneId.systemDefault()).toInstant()));
        }
        
        if (deviceToken.getDta_Atualizacao() != null) {
            dto.setDtaAtualizacao(Date.from(deviceToken.getDta_Atualizacao().atZone(ZoneId.systemDefault()).toInstant()));
        }
        
        dto.setFlgInativo(deviceToken.getFlg_Inativo());
        
        if (deviceToken.getDta_Remocao() != null) {
            dto.setDtaRemocao(Date.from(deviceToken.getDta_Remocao().atZone(ZoneId.systemDefault()).toInstant()));
        }

        return dto;
    }

    public DeviceToken toEntity(DeviceTokenCreateDTO createDTO) {
        if (createDTO == null) {
            return null;
        }

        DeviceToken deviceToken = new DeviceToken();
        deviceToken.setUsuario_id(createDTO.getUsuarioId());
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
}

