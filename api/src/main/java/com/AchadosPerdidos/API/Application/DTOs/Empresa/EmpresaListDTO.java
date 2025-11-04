package com.AchadosPerdidos.API.Application.DTOs.Empresa;

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
@Schema(description = "DTO para lista de empresas")
public class EmpresaListDTO {
    
    @Schema(description = "Lista de empresas")
    private List<EmpresaDTO> empresas;
    
    @Schema(description = "Total de empresas na lista")
    private int totalCount;
}
