package com.AchadosPerdidos.API.Domain.Entity;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemReivindicado {
    private Integer id;
    private String detalhesReivindicacao;
    private Integer itemId;
    private Integer usuarioReivindicadorId;
    private Integer usuarioAchouId;
    private Date dtaCriacao;
    private Boolean flgInativo;
    private Date dtaRemocao;
}


