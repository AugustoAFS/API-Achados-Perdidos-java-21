package com.AchadosPerdidos.API.Application.DTOs.Endereco;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoListDTO {
    private List<EnderecoDTO> Enderecos;
}

