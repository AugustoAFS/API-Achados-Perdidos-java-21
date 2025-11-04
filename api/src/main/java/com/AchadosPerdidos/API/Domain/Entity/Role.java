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
public class Role {
    private Integer id;
    private String nome;
    private String descricao;
    private Date dtaCriacao;
    private Boolean flgInativo;
    private Date dtaRemocao;
}


