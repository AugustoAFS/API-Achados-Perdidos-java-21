package com.AchadosPerdidos.API.Application.DTOs.Instituicao;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO para atualizacao de instituicao")
public class InstituicaoUpdateDTO {
    @Schema(description = "Nome da instituicao", example = "Instituto Federal do Parana")
    private String nome;

    @Schema(description = "Codigo da instituicao", example = "IFPR")
    private String codigo;

    @Schema(description = "Tipo da instituicao", example = "PUBLICA")
    private String tipo;

    @Schema(description = "CNPJ da instituicao", example = "12345678000195")
    private String cnpj;

    @Schema(description = "Flag indicando se a instituicao esta inativa", example = "false")
    private Boolean flgInativo;

    public InstituicaoUpdateDTO() {
        // Construtor padrao para frameworks de serializacao
    }

    public InstituicaoUpdateDTO(String nome, String codigo, String tipo, String cnpj, Boolean flgInativo) {
        this.nome = nome;
        this.codigo = codigo;
        this.tipo = tipo;
        this.cnpj = cnpj;
        this.flgInativo = flgInativo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public Boolean getFlgInativo() {
        return flgInativo;
    }

    public void setFlgInativo(Boolean flgInativo) {
        this.flgInativo = flgInativo;
    }
}

