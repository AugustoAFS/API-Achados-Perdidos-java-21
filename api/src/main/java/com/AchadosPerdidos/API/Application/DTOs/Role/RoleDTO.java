package com.AchadosPerdidos.API.Application.DTOs.Role;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO de Role")
public class RoleDTO {
    private Integer id;
    private String nome;
    private String descricao;
    private Date dtaCriacao;
    private Boolean flgInativo;
    private Date dtaRemocao;
}


