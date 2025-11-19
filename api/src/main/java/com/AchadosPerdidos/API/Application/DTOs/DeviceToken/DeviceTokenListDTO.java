package com.AchadosPerdidos.API.Application.DTOs.DeviceToken;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeviceTokenListDTO {
    private List<DeviceTokenDTO> deviceTokens;
    private Integer total;

    public DeviceTokenListDTO(List<DeviceTokenDTO> deviceTokens) {
        this.deviceTokens = deviceTokens;
        this.total = deviceTokens != null ? deviceTokens.size() : 0;
    }
}

