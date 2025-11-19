package com.AchadosPerdidos.API.Application.DTOs.FotoItem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FotoItemUpdateDTO {
    private Integer itemId;
    private Integer fotoId;
    private Boolean flgInativo;
}
