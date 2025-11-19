package com.AchadosPerdidos.API.Application.DTOs.FotoUsuario;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FotoUsuarioListDTO {
    private List<FotoUsuarioDTO> fotoUsuarios;
    private int totalCount;
}
