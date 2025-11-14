package com.AchadosPerdidos.API.Application.DTOs.Campus;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO para atualizacao de campus")
public class CampusUpdateDTO{
    @Schema(description = "Nome do campus", example = "IFPR - Sede Curitiba")
    private String nome;

    @Schema(description = "ID da instituicao", example = "1")
    private Integer instituicaoId;

    @Schema(description = "ID do endereco", example = "10")
    private Integer enderecoId;
    
    @Schema(description = "Flag de inativacao", example = "false")
    private Boolean flgInativo;

    public CampusUpdateDTO() {
        // Construtor padrao para frameworks de serializacao
    }

    public CampusUpdateDTO(String nome, Integer instituicaoId, Integer enderecoId, Boolean flgInativo) {
        this.nome = nome;
        this.instituicaoId = instituicaoId;
        this.enderecoId = enderecoId;
        this.flgInativo = flgInativo;
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

    public Boolean getFlgInativo() {
        return flgInativo;
    }

    public void setFlgInativo(Boolean flgInativo) {
        this.flgInativo = flgInativo;
    }
}