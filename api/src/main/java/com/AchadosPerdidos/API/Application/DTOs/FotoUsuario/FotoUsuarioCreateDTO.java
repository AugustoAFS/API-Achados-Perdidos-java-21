package com.AchadosPerdidos.API.Application.DTOs.FotoUsuario;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FotoUsuarioCreateDTO {
    private Integer usuarioId;
    private Integer fotoId;
}
