package com.AchadosPerdidos.API.Application.DTOs.Endereco;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO para atualizacao de Endereco")

public class EnderecoUpdateDTO{
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cep;
    private Integer cidadeId;
    private Boolean flgInativo;

    public EnderecoUpdateDTO() {
        // Construtor padrao para frameworks de serializacao
    }

    public EnderecoUpdateDTO(String logradouro, String numero, String complemento, String bairro, String cep, Integer cidadeId, Boolean flgInativo) {
        this.logradouro = logradouro;
        this.numero = numero;
        this.complemento = complemento;
        this.bairro = bairro;
        this.cep = cep;
        this.cidadeId = cidadeId;
        this.flgInativo = flgInativo;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public Integer getCidadeId() {
        return cidadeId;
    }

    public void setCidadeId(Integer cidadeId) {
        this.cidadeId = cidadeId;
    }

    public Boolean getFlgInativo() {
        return flgInativo;
    }

    public void setFlgInativo(Boolean flgInativo) {
        this.flgInativo = flgInativo;
    }
}
