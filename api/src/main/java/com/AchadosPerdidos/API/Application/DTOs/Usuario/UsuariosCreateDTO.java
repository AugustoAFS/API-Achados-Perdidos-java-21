package com.AchadosPerdidos.API.Application.DTOs.Usuario;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO para criacao de usuario")
public class UsuariosCreateDTO {
    private String nomeCompleto;
    private String cpf;
    private String email;
    private String senha;
    private String matricula;
    private String numeroTelefone;
    private Integer enderecoId;

    public UsuariosCreateDTO() {
        // Construtor padrao para frameworks de serializacao
    }

    public UsuariosCreateDTO(String nomeCompleto, String cpf, String email, String senha, String matricula, String numeroTelefone, Integer enderecoId) {
        this.nomeCompleto = nomeCompleto;
        this.cpf = cpf;
        this.email = email;
        this.senha = senha;
        this.matricula = matricula;
        this.numeroTelefone = numeroTelefone;
        this.enderecoId = enderecoId;
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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getNumeroTelefone() {
        return numeroTelefone;
    }

    public void setNumeroTelefone(String numeroTelefone) {
        this.numeroTelefone = numeroTelefone;
    }

    public Integer getEnderecoId() {
        return enderecoId;
    }

    public void setEnderecoId(Integer enderecoId) {
        this.enderecoId = enderecoId;
    }
}

