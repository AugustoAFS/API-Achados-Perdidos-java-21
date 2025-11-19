package com.AchadosPerdidos.API.Application.DTOs.UsuarioCampus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioCampusUpdateDTO {
    private Integer usuarioId;
    private Integer campusId;
    private Boolean flgInativo;
}
