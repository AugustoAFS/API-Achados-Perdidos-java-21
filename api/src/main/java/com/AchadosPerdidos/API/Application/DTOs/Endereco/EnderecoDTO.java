package com.AchadosPerdidos.API.Application.DTOs.Endereco;

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
@Schema(description = "DTO de Endereco")
public class EnderecoDTO {
    private Integer id;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cep;
    private Integer cidadeId;
    private Date dtaCriacao;
    private Boolean flgInativo;
    private Date dtaRemocao;
}


