package com.AchadosPerdidos.API.Application.DTOs.Usuario;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Date;

@Schema(description = "DTO completo de usuario")
public class UsuariosDTO {
    @Schema(description = "ID do usuario", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer id;

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

    @Schema(description = "Data de criacao", example = "2024-01-01T00:00:00")
    private Date dtaCriacao;

    @Schema(description = "Flag de inativacao", example = "false")
    private Boolean flgInativo;

    @Schema(description = "Data de remocao logica", example = "2024-02-01T00:00:00")
    private Date dtaRemocao;

    public UsuariosDTO() {
        // Construtor padrao para frameworks de serializacao
    }

    public UsuariosDTO(Integer id, String nomeCompleto, String cpf, String email, String matricula, Integer enderecoId, Date dtaCriacao, Boolean flgInativo, Date dtaRemocao) {
        this.id = id;
        this.nomeCompleto = nomeCompleto;
        this.cpf = cpf;
        this.email = email;
        this.matricula = matricula;
        this.enderecoId = enderecoId;
        this.dtaCriacao = dtaCriacao;
        this.flgInativo = flgInativo;
        this.dtaRemocao = dtaRemocao;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Date getDtaCriacao() {
        return dtaCriacao;
    }

    public void setDtaCriacao(Date dtaCriacao) {
        this.dtaCriacao = dtaCriacao;
    }

    public Boolean getFlgInativo() {
        return flgInativo;
    }

    public void setFlgInativo(Boolean flgInativo) {
        this.flgInativo = flgInativo;
    }

    public Date getDtaRemocao() {
        return dtaRemocao;
    }

    public void setDtaRemocao(Date dtaRemocao) {
        this.dtaRemocao = dtaRemocao;
    }
}

