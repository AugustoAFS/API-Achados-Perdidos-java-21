package com.AchadosPerdidos.API.Application.DTOs.DeviceToken;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeviceTokenCreateDTO {
    private Integer usuarioId;
    private String token;
    private String plataforma;
}

