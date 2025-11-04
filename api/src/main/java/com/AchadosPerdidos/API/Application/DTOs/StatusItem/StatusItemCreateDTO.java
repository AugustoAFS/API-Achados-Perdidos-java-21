package com.AchadosPerdidos.API.Application.DTOs.StatusItem;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para criação de Status do Item")
public class StatusItemCreateDTO {
    private String nome;
    private String descricao;
    private String statusItem;
}


