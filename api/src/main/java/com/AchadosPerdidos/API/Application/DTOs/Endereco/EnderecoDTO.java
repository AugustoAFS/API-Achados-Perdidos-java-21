package com.AchadosPerdidos.API.Application.DTOs.Endereco;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Date;

@Schema(description = "DTO de Endereco")

public class EnderecoDTO{
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

    public EnderecoDTO() {
        // Construtor padrao para frameworks de serializacao
    }

    public EnderecoDTO(Integer id, String logradouro, String numero, String complemento, String bairro, String cep, Integer cidadeId, Date dtaCriacao, Boolean flgInativo, Date dtaRemocao) {
        this.id = id;
        this.logradouro = logradouro;
        this.numero = numero;
        this.complemento = complemento;
        this.bairro = bairro;
        this.cep = cep;
        this.cidadeId = cidadeId;
        this.dtaCriacao = dtaCriacao;
        this.flgInativo = flgInativo;
        this.dtaRemocao = dtaRemocao;
    }

    public Integer getId() {
        return id;
    }
    public String getLogradouro() {
        return logradouro;
    }
    public String getNumero() {
        return numero;
    }
    public String getComplemento() {
        return complemento;
    }
    public String getBairro() {
        return bairro;
    }
    public String getCep() {
        return cep;
    }
    public Integer getCidadeId() {
        return cidadeId;
    }
    public Date getDtaCriacao() {
        return dtaCriacao;
    }
    public Boolean getFlgInativo() {
        return flgInativo;
    }
    public Date getDtaRemocao() {
        return dtaRemocao;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }
    public void setNumero(String numero) {
        this.numero = numero;
    }
    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }
    public void setBairro(String bairro) {
        this.bairro = bairro;
    }
    public void setCep(String cep) {
        this.cep = cep;
    }
    public void setCidadeId(Integer cidadeId) {
        this.cidadeId = cidadeId;
    }
    public void setDtaCriacao(Date dtaCriacao) {
        this.dtaCriacao = dtaCriacao;
    }
    public void setFlgInativo(Boolean flgInativo) {
        this.flgInativo = flgInativo;
    }
    public void setDtaRemocao(Date dtaRemocao) {
        this.dtaRemocao = dtaRemocao;
    }
    
}