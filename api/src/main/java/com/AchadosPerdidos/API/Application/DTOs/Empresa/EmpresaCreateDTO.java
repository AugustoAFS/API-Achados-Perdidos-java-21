package com.AchadosPerdidos.API.Application.DTOs.Empresa;

import io.swagger.v3.oas.annotations.media.Schema;


public class EmpresaCreateDTO{
    @Schema(description = "Nome da empresa", example = "Empresa ABC Ltda", required = true)
    private String nome;
    @Schema(description = "Nome fantasia da empresa", example = "ABC", required = true)
    private String nomeFantasia;
    @Schema(description = "CNPJ da empresa", example = "12345678000195")
    private String cnpj;
    @Schema(description = "ID do endereco da empresa", example = "1")
    private Integer enderecoId;

    public EmpresaCreateDTO() {
        // Construtor padrao para frameworks de serializacao
    }

    public EmpresaCreateDTO(String nome, String nomeFantasia, String cnpj, Integer enderecoId) {
        this.nome = nome;
        this.nomeFantasia = nomeFantasia;
        this.cnpj = cnpj;
        this.enderecoId = enderecoId;
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
}