package com.AchadosPerdidos.API.Application.DTOs.Campus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CampusDTO {
    private Integer id;
    private String nome;
    private Integer instituicaoId;
    private Integer enderecoId;
    private LocalDateTime dtaCriacao;
    private Boolean flgInativo;
    private LocalDateTime dtaRemocao;
}
