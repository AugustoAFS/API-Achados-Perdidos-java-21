package com.AchadosPerdidos.API.Application.DTOs.Fotos;

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
@Schema(description = "DTO para lista de fotos")
public class FotosListDTO {
    
    @Schema(description = "Lista de fotos")
    private List<FotosDTO> fotos;
    
    @Schema(description = "Total de fotos na lista")
    private int totalCount;
}
