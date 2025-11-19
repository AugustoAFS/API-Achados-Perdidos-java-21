package com.AchadosPerdidos.API.Application.DTOs.Usuario;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuariosListDTO {
    private List<UsuariosDTO> usuarios;
    private int totalCount;
}
