package com.AchadosPerdidos.API.Application.Services.Interfaces;

import com.AchadosPerdidos.API.Application.DTOs.DeviceToken.DeviceTokenCreateDTO;
import com.AchadosPerdidos.API.Application.DTOs.DeviceToken.DeviceTokenDTO;
import com.AchadosPerdidos.API.Application.DTOs.DeviceToken.DeviceTokenListDTO;
import com.AchadosPerdidos.API.Application.DTOs.DeviceToken.DeviceTokenUpdateDTO;

public interface IDeviceTokenService {
    DeviceTokenListDTO getAllDeviceTokens();
    DeviceTokenListDTO getDeviceTokensByUsuario(Integer usuarioId);
    DeviceTokenDTO createDeviceToken(DeviceTokenCreateDTO createDTO);
    DeviceTokenDTO updateDeviceToken(Integer id, DeviceTokenUpdateDTO updateDTO);
    boolean deleteDeviceToken(Integer id);
}
