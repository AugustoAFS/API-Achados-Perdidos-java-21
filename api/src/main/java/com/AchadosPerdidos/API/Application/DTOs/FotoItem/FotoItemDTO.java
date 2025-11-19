package com.AchadosPerdidos.API.Application.DTOs.FotoItem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FotoItemDTO {
    private Integer id;
    private Integer itemId;
    private Integer fotoId;
    private LocalDateTime dtaCriacao;
    private Boolean flgInativo;
    private LocalDateTime dtaRemocao;
}

