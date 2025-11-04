package com.AchadosPerdidos.API.Application.DTOs.Endereco;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Schema(description = "DTO para lista de endereços")
@Getter
@Setter
@AllArgsConstructor
public class EnderecoListDTO {
    
    @Schema(description = "Lista de endereços")
    private List<EnderecoDTO> Enderecos;
}

