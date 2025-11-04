package com.AchadosPerdidos.API.Application.DTOs.Usuario;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para lista de usuários")
public class UsuariosListDTO {
    
    @Schema(description = "Lista de usuários")
    private List<UsuariosDTO> usuarios;
    
    @Schema(description = "Total de usuários na lista")
    private int totalCount;
}