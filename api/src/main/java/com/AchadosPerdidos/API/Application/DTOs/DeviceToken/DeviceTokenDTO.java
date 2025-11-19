package com.AchadosPerdidos.API.Application.DTOs.DeviceToken;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeviceTokenDTO {
    private Integer id;
    private Integer usuarioId;
    private String token;
    private String plataforma;
    private LocalDateTime dtaCriacao;
    private LocalDateTime dtaAtualizacao;
    private Boolean flgInativo;
    private LocalDateTime dtaRemocao;
}

