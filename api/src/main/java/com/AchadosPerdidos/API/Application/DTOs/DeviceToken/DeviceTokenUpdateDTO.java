package com.AchadosPerdidos.API.Application.DTOs.DeviceToken;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeviceTokenUpdateDTO {
    private String token;
    private String plataforma;
    private Boolean flgInativo;
}

