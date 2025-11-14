package com.AchadosPerdidos.API.Application.DTOs.Empresa;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO completo de empresa")

public class EmpresaDTO{
    @Schema(description = "ID da empresa", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer id;
    @Schema(description = "Nome da empresa", example = "Empresa ABC Ltda")
    private String nome;
    @Schema(description = "Nome fantasia", example = "ABC Ltda")
    private String nomeFantasia;
    @Schema(description = "CNPJ", example = "12345678000195")
    private String cnpj;
    @Schema(description = "ID do endereco", example = "10")
    private Integer enderecoId;
    @Schema(description = "Data de criacao", example = "2024-01-01T00:00:00")
    private java.util.Date dtaCriacao;
    @Schema(description = "Flag de inativacao", example = "false")
    private Boolean flgInativo;
    @Schema(description = "Data de remocao logica", example = "2024-02-01T00:00:00")
    private java.util.Date dtaRemocao;

    public EmpresaDTO() {
        // Construtor padrao para frameworks de serializacao
    }

    public EmpresaDTO(Integer id, String nome, String nomeFantasia, String cnpj, Integer enderecoId, java.util.Date dtaCriacao, Boolean flgInativo, java.util.Date dtaRemocao) {
        this.id = id;
        this.nome = nome;
        this.nomeFantasia = nomeFantasia;
        this.cnpj = cnpj;
        this.enderecoId = enderecoId;
        this.dtaCriacao = dtaCriacao;
        this.flgInativo = flgInativo;
        this.dtaRemocao = dtaRemocao;
    }

    public Integer getId() {
        return id;
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
    public java.util.Date getDtaCriacao() {
        return dtaCriacao;
    }
    public Boolean getFlgInativo() {
        return flgInativo;
    }
    public java.util.Date getDtaRemocao() {
        return dtaRemocao;
    }

    public void setId(Integer id) {
        this.id = id;
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
    public void setDtaCriacao(java.util.Date dtaCriacao) {
        this.dtaCriacao = dtaCriacao;
    }
    public void setFlgInativo(Boolean flgInativo) {
        this.flgInativo = flgInativo;
    }
    public void setDtaRemocao(java.util.Date dtaRemocao) {
        this.dtaRemocao = dtaRemocao;
    }


}