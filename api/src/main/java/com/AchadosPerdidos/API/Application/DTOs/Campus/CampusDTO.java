package com.AchadosPerdidos.API.Application.DTOs.Campus;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO completo de campus")
public class CampusDTO{
    @Schema(description = "ID do campus", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer id;

    @Schema(description = "Nome do campus", example = "IFPR - Sede Curitiba")
    private String nome;

    @Schema(description = "ID da instituição", example = "1")
    private Integer instituicaoId;

    @Schema(description = "ID do endereço", example = "10")
    private Integer enderecoId;

    @Schema(description = "Data de criação", example = "2024-01-01T00:00:00")
    private java.util.Date dtaCriacao;

    @Schema(description = "Flag de inativação", example = "false")
    private Boolean flgInativo;

    @Schema(description = "Data de remoção lógica", example = "2024-02-01T00:00:00")
    private java.util.Date dtaRemocao;

    public CampusDTO() {}

    public CampusDTO(Integer id, String nome, Integer instituicaoId, Integer enderecoId, java.util.Date dtaCriacao, Boolean flgInativo, java.util.Date dtaRemocao) {
        this.id = id;
        this.nome = nome;
        this.instituicaoId = instituicaoId;
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

    public Integer getInstituicaoId() {
        return instituicaoId;
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

    public void setInstituicaoId(Integer instituicaoId) {
        this.instituicaoId = instituicaoId;
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
