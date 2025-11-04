package com.AchadosPerdidos.API.Application.DTOs.Local;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.util.List;

@Schema(description = "DTO para lista de locais")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LocalListDTO {
    
    @Schema(description = "Lista de locais")
    private List<LocalDTO> Locais;
}

