package com.AchadosPerdidos.API.Domain.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItensReivindicados {
    private int Id;
    private String Detalhes_Reivindicacao;
    private int Item_Id;
    private int Usuario_Reivindicador_Id;
    private Integer Usuario_Achou_Id;
    private LocalDateTime Dta_Criacao;
    private Boolean Flg_Inativo;
    private LocalDateTime Dta_Remocao;
}
