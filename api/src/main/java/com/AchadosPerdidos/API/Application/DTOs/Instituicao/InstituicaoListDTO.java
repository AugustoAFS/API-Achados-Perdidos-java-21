package com.AchadosPerdidos.API.Application.DTOs.Instituicao;

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
@Schema(description = "DTO para lista de instituições")
public class InstituicaoListDTO {
    
    @Schema(description = "Lista de instituições")
    private List<InstituicaoDTO> instituicoes;
    
    @Schema(description = "Total de instituições na lista")
    private int totalCount;
}
