package com.AchadosPerdidos.API.Application.DTOs.FotoUsuario;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FotoUsuarioUpdateDTO {
    private Integer usuarioId;
    private Integer fotoId;
    private Boolean flgInativo;
}
