package com.AchadosPerdidos.API.Application.DTOs.Instituicao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InstituicaoListDTO {
    private List<InstituicaoDTO> instituicoes;
    private int totalCount;
}
