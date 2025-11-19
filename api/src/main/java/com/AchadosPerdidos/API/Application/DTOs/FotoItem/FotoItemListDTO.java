package com.AchadosPerdidos.API.Application.DTOs.FotoItem;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FotoItemListDTO {
    private List<FotoItemDTO> fotoItens;
    private int totalCount;
}