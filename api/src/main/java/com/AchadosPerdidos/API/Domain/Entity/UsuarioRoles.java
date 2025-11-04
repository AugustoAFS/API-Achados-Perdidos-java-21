package com.AchadosPerdidos.API.Domain.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioRoles {
    private int Usuario_Id;
    private int Role_Id;
    private LocalDateTime Dta_Criacao;
    private Boolean Flg_Inativo;
    private LocalDateTime Dta_Remocao;
}
