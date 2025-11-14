package com.AchadosPerdidos.API.Application.DTOs.Instituicao;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO para criacao de instituicao")
public class InstituicaoCreateDTO {
    @Schema(description = "Nome da instituicao", example = "Instituto Federal do Parana", required = true)
    private String nome;

    @Schema(description = "Codigo da instituicao", example = "IFPR", required = true)
    private String codigo;

    @Schema(description = "Tipo da instituicao", example = "PUBLICA")
    private String tipo;

    @Schema(description = "CNPJ da instituicao", example = "12345678000195")
    private String cnpj;

    public InstituicaoCreateDTO() {
        // Construtor padrao para frameworks de serializacao
    }

    public InstituicaoCreateDTO(String nome, String codigo, String tipo, String cnpj) {
        this.nome = nome;
        this.codigo = codigo;
        this.tipo = tipo;
        this.cnpj = cnpj;
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
}

