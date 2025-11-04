package com.AchadosPerdidos.API.Application.DTOs.Campus;

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
@Schema(description = "DTO para lista de campi")
public class CampusListDTO {
    
    @Schema(description = "Lista de campi")
    private List<CampusDTO> campi;
    
    @Schema(description = "Total de campi na lista")
    private int totalCount;
}
