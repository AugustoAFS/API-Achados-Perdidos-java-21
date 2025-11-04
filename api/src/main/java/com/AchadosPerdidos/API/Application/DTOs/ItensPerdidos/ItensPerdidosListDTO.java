package com.AchadosPerdidos.API.Application.DTOs.ItensPerdidos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Schema(description = "DTO para lista de itens perdidos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItensPerdidosListDTO {
    
    @Schema(description = "Lista de itens perdidos")
    private List<ItensPerdidosDTO> itensPerdidos;
    
    @Schema(description = "Total de itens na lista")
    private int totalCount;
}

