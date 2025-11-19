package com.AchadosPerdidos.API.Application.DTOs.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleUpdateDTO {
    private String nome;
    private String descricao;
    private Boolean flgInativo;
}
