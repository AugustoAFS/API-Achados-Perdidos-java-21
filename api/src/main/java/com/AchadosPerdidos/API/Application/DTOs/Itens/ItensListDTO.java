package com.AchadosPerdidos.API.Application.DTOs.Itens;

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
@Schema(description = "DTO para lista de itens")
public class ItensListDTO {
    
    @Schema(description = "Lista de itens")
    private List<ItensDTO> itens;
    
    @Schema(description = "Total de itens na lista")
    private int totalCount;
}
