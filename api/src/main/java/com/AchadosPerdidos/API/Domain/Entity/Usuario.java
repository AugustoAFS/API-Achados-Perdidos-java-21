package com.AchadosPerdidos.API.Domain.Entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "usuarios", schema = "ap_achados_perdidos")
public class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer Id;

    @Column(name = "Nome_completo", length = 255, nullable = false)
    private String Nome_completo;

    @Column(name = "CPF", length = 11, unique = true)
    private String CPF;

    @Column(name = "Email", length = 255, nullable = false, unique = true)
    private String Email;

    @Column(name = "Hash_senha", length = 255, nullable = false)
    private String Hash_senha;

    @Column(name = "Matricula", length = 50, unique = true)
    private String Matricula;

    @Column(name = "Numero_telefone", length = 20)
    private String Numero_telefone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Endereco_id", foreignKey = @ForeignKey(name = "fk_usuarios_endereco"))
    private Endereco Endereco_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Role_id", nullable = false, foreignKey = @ForeignKey(name = "fk_usuarios_role"))
    private Role Role_id;

    @Column(name = "Dta_Criacao", nullable = false, updatable = false)
    private LocalDateTime Dta_Criacao;

    @Column(name = "Flg_Inativo", nullable = false)
    private Boolean Flg_Inativo;

    @Column(name = "Dta_Remocao")
    private LocalDateTime Dta_Remocao;



    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        this.Id = id;
    }

    public String getNomeCompleto() {
        return Nome_completo;
    }

    public void setNomeCompleto(String nome_completo) {
        this.Nome_completo = nome_completo;
    }
    
    public String getCpf() {
        return CPF;
    }

    public void setCpf(String cpf) {
        this.CPF = cpf;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        this.Email = email;
    }

    public String getHash_senha() {
        return Hash_senha;
    }

    public void setHash_senha(String hash_senha) {
        this.Hash_senha = hash_senha;
    }

    public String getMatricula() {
        return Matricula;
    }

    public void setMatricula(String matricula) {
        this.Matricula = matricula;
    }

    public String getNumero_telefone() {
        return Numero_telefone;
    }

    public void setNumero_telefone(String numero_telefone) {
        this.Numero_telefone = numero_telefone;
    }

    public Endereco getEndereco_id() {
        return Endereco_id;
    }

    public void setEndereco_id(Endereco endereco_id) {
        this.Endereco_id = endereco_id;
    }

    public Role getRole_id() {
        return Role_id;
    }

    public void setRole_id(Role role_id) {
        Role_id = role_id;
    }

    public LocalDateTime getDta_Criacao() {
        return Dta_Criacao;
    }

    public void setDta_Criacao(LocalDateTime Dta_Criacao) {
        this.Dta_Criacao = Dta_Criacao;
    }   

    public Boolean getFlg_Inativo() {
        return Flg_Inativo;
    }

    public void setFlg_Inativo(Boolean Flg_Inativo) {
        this.Flg_Inativo = Flg_Inativo;
    }

    public LocalDateTime getDta_Remocao() {
        return Dta_Remocao;
    }

    public void setDta_Remocao(LocalDateTime Dta_Remocao) {
        this.Dta_Remocao = Dta_Remocao;
    }
}
