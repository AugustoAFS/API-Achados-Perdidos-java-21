package com.AchadosPerdidos.API.Domain.Entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "campus", schema = "ap_achados_perdidos")
public class Campus implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer Id;

    @Column(name = "Nome", length = 150, nullable = false)
    private String Nome;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Instituicao_id", nullable = false, foreignKey = @ForeignKey(name = "fk_campus_instituicao"))
    private Instituicoes Instituicao_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Endereco_id", nullable = false, foreignKey = @ForeignKey(name = "fk_campus_endereco"))
    private Endereco Endereco_id;

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

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        this.Nome = nome;
    }

    public Instituicoes getInstituicaoId() {
        return Instituicao_id;
    }

    public void setInstituicaoId(Instituicoes instituicao_id) {
        this.Instituicao_id = instituicao_id;
    }

    public Endereco getEnderecoId() {
        return Endereco_id;
    }

    public void setEnderecoId(Endereco endereco_id) {
        this.Endereco_id = endereco_id;
    }

    public LocalDateTime getDtaCriacao() {
        return Dta_Criacao;
    }

    public void setDtaCriacao(LocalDateTime Dta_Criacao) {
        this.Dta_Criacao = Dta_Criacao;
    }

    public Boolean getFlgInativo() {
        return Flg_Inativo;
    }

    public void setFlgInativo(Boolean Flg_Inativo) {
        this.Flg_Inativo = Flg_Inativo;
    }

    public LocalDateTime getDtaRemocao() {
        return Dta_Remocao;
    }

    public void setDtaRemocao(LocalDateTime Dta_Remocao) {
        this.Dta_Remocao = Dta_Remocao;
    }
}
