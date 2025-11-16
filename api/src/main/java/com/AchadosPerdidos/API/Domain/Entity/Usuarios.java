package com.AchadosPerdidos.API.Domain.Entity;

import com.AchadosPerdidos.API.Domain.Validator.EntityValidator;

import java.util.Date;

public class Usuarios {
    private Integer id;
    private String nomeCompleto;
    private String cpf;
    private String email;
    private String hashSenha;
    private String matricula;
    private String numeroTelefone;
    private Integer empresaId;
    private Integer enderecoId;
    private Date dtaCriacao;
    private Boolean flgInativo;
    private Date dtaRemocao;

    public Usuarios() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    
    public String getNomeCompleto() { return nomeCompleto; }
    public void setNomeCompleto(String nomeCompleto) { this.nomeCompleto = nomeCompleto; }
    
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getHashSenha() { return hashSenha; }
    public void setHashSenha(String hashSenha) { this.hashSenha = hashSenha; }
    
    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }
    
    public String getNumeroTelefone() { return numeroTelefone; }
    public void setNumeroTelefone(String numeroTelefone) { this.numeroTelefone = numeroTelefone; }
    
    public Integer getEmpresaId() { return empresaId; }
    public void setEmpresaId(Integer empresaId) { this.empresaId = empresaId; }
    
    public Integer getEnderecoId() { return enderecoId; }
    public void setEnderecoId(Integer enderecoId) { this.enderecoId = enderecoId; }
    
    public Date getDtaCriacao() { return dtaCriacao; }
    public void setDtaCriacao(Date dtaCriacao) { this.dtaCriacao = dtaCriacao; }
    
    public Boolean getFlgInativo() { return flgInativo; }
    public void setFlgInativo(Boolean flgInativo) { this.flgInativo = flgInativo; }
    
    public Date getDtaRemocao() { return dtaRemocao; }
    public void setDtaRemocao(Date dtaRemocao) { this.dtaRemocao = dtaRemocao; }
    
    public void validate() {
        EntityValidator.validateRequired(nomeCompleto, "nome completo");
        EntityValidator.validateMinLength(nomeCompleto, 3, "nome completo");
        EntityValidator.validateMaxLength(nomeCompleto, 255, "nome completo");
        
        EntityValidator.validateRequired(email, "email");
        EntityValidator.validateEmail(email);
        
        EntityValidator.validateRequired(hashSenha, "senha");
        
        if (cpf != null && !cpf.trim().isEmpty()) {
            EntityValidator.validateCpfFormat(cpf);
            if (!EntityValidator.isValidCpf(cpf)) {
                throw new com.AchadosPerdidos.API.Exeptions.BusinessException("CPF inválido");
            }
        }
        
        if (numeroTelefone != null && !numeroTelefone.trim().isEmpty()) {
            EntityValidator.validatePhoneFormat(numeroTelefone);
        }
    }
    
    public void marcarComoInativo() {
        this.flgInativo = true;
        this.dtaRemocao = new Date();
    }
    
    public boolean isAtivo() {
        return !Boolean.TRUE.equals(flgInativo);
    }
}