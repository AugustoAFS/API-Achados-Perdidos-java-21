package com.AchadosPerdidos.API.Application.DTOs.Endereco;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;


public class EnderecoListDTO{
    @Schema(description = "Lista de enderecos")
    private List<EnderecoDTO> Enderecos;

    public EnderecoListDTO() {
        // Construtor padrao para frameworks de serializacao
    }

    public EnderecoListDTO(List<EnderecoDTO> enderecos) {
        this.Enderecos = enderecos;
    }
    public List<EnderecoDTO> getEnderecos() {
        return Enderecos;
    }
    public void setEnderecos(List<EnderecoDTO> enderecos) {
        Enderecos = enderecos;
    }
}

