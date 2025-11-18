package com.AchadosPerdidos.API.Application.DTOs.Usuario;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO para atualizacao de usuario")
public class UsuariosUpdateDTO {
    @Schema(description = "Nome completo", example = "Joao Silva")
    private String nomeCompleto;

    @Schema(description = "CPF", example = "12345678901")
    private String cpf;

    @Schema(description = "Email", example = "joao@ifpr.edu.br")
    private String email;

    @Schema(description = "Matricula", example = "2024001")
    private String matricula;

    @Schema(description = "Endereco ID", example = "5")
    private Integer enderecoId;

    @Schema(description = "Flag de inativacao", example = "false")
    private Boolean flgInativo;

    public UsuariosUpdateDTO() {
        // Construtor padrao para frameworks de serializacao
    }

    public UsuariosUpdateDTO(String nomeCompleto, String cpf, String email, String matricula, Integer enderecoId, Boolean flgInativo) {
        this.nomeCompleto = nomeCompleto;
        this.cpf = cpf;
        this.email = email;
        this.matricula = matricula;
        this.enderecoId = enderecoId;
        this.flgInativo = flgInativo;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
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

