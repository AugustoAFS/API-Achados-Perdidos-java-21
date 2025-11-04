package com.AchadosPerdidos.API.Domain.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItensPerdidos {
    private int Id;
    private String Nome;
    private String Descricao;
    private LocalDateTime Encontrado_Em;
    private int Usuario_Relator_Id;
    private int Local_Id;
    private int Status_Item_Id;
    private LocalDateTime Dta_Criacao;
    private Boolean Flg_Inativo;
    private LocalDateTime Dta_Remocao;
}
