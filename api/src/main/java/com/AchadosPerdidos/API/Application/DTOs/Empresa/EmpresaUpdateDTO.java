package com.AchadosPerdidos.API.Application.DTOs.Empresa;

import io.swagger.v3.oas.annotations.media.Schema;


public class EmpresaUpdateDTO{
    @Schema(description = "Nome da empresa", example = "Empresa ABC Ltda")
    private String nome;
    @Schema(description = "Nome fantasia da empresa", example = "ABC")
    private String nomeFantasia;
    @Schema(description = "CNPJ da empresa", example = "12345678000195")
    private String cnpj;    
    @Schema(description = "ID do endereco da empresa", example = "1")
    private Integer enderecoId;
    @Schema(description = "Flag indicando se esta inativo", example = "false")
    private Boolean flgInativo;

    public EmpresaUpdateDTO() {
        // Construtor padrao para frameworks de serializacao
    }

    public EmpresaUpdateDTO(String nome, String nomeFantasia, String cnpj, Integer enderecoId, Boolean flgInativo) {
        this.nome = nome;
        this.nomeFantasia = nomeFantasia;
        this.cnpj = cnpj;
        this.enderecoId = enderecoId;
        this.flgInativo = flgInativo;
    }   

    public String getNome() {
        return nome;
    }
    public String getNomeFantasia() {
        return nomeFantasia;
    }
    public String getCnpj() {
        return cnpj;
    }
    public Integer getEnderecoId() {
        return enderecoId;
    }
    public Boolean getFlgInativo() {
        return flgInativo;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }
    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
    public void setEnderecoId(Integer enderecoId) {
        this.enderecoId = enderecoId;
    }
    public void setFlgInativo(Boolean flgInativo) {
        this.flgInativo = flgInativo;
    }
}