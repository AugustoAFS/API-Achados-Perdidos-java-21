package com.AchadosPerdidos.API.Application.DTOs.Item;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.AchadosPerdidos.API.Domain.Enum.Tipo_Item;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemUpdateDTO {
    private String nome;
    private String descricao;
    private Tipo_Item tipoItem;
    private Integer localId;
    private Integer usuarioRelatorId;
    private Boolean flgInativo;
}
