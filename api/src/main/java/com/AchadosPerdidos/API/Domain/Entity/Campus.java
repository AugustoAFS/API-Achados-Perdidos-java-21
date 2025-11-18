package com.AchadosPerdidos.API.Domain.Entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "campus", schema = "ap_achados_perdidos")
public class Campus implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;

    private Integer instituicao_id;

    private Integer endereco_id;

    private LocalDateTime Dta_Criacao;

    private Boolean Flg_Inativo;

    private LocalDateTime Dta_Remocao;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getInstituicaoId() {
        return instituicao_id;
    }

    public void setInstituicaoId(Integer instituicao_id) {
        this.instituicao_id = instituicao_id;
    }

    public Integer getEnderecoId() {
        return endereco_id;
    }

    public void setEnderecoId(Integer endereco_id) {
        this.endereco_id = endereco_id;
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


