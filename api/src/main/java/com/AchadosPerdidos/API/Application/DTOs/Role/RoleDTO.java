package com.AchadosPerdidos.API.Application.DTOs.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO {
    private Integer id;
    private String nome;
    private String descricao;
    private LocalDateTime dtaCriacao;
    private Boolean flgInativo;
    private LocalDateTime dtaRemocao;
}
