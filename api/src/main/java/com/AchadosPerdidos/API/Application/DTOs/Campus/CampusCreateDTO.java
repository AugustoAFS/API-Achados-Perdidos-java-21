package com.AchadosPerdidos.API.Application.DTOs.Campus;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO para criacao de campus")
public class CampusCreateDTO{
    @Schema(description = "Nome do campus", example = "IFPR - Sede Curitiba", required = true)
    private String nome;

    @Schema(description = "ID da instituicao", example = "1", required = true)
    private Integer instituicaoId;

    @Schema(description = "ID do endereco", example = "10", required = true)
    private Integer enderecoId;

    public CampusCreateDTO() {}

    public CampusCreateDTO(String nome, Integer instituicaoId, Integer enderecoId) {
        this.nome = nome;
        this.instituicaoId = instituicaoId;
        this.enderecoId = enderecoId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getInstituicaoId() {
        return instituicaoId;
    }

    public void setInstituicaoId(Integer instituicaoId) {
        this.instituicaoId = instituicaoId;
    }

    public Integer getEnderecoId() {
        return enderecoId;
    }

    public void setEnderecoId(Integer enderecoId) {
        this.enderecoId = enderecoId;
    }
}
