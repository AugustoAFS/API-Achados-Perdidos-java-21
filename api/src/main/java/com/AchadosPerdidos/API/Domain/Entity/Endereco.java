package com.AchadosPerdidos.API.Domain.Entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "enderecos", schema = "ap_achados_perdidos")
public class Endereco implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer Id;

    @Column(name = "Logradouro", length = 255, nullable = false)
    private String Logradouro;

    @Column(name = "Numero", length = 20)
    private String Numero;

    @Column(name = "Complemento", length = 100)
    private String Complemento;

    @Column(name = "Bairro", length = 100)
    private String Bairro;

    @Column(name = "CEP", length = 8)
    private String CEP;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Cidade_id", nullable = false, foreignKey = @ForeignKey(name = "fk_enderecos_cidade"))
    private Cidade Cidade_id;

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

    public String getLogradouro() {
        return Logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.Logradouro = logradouro;
    }

    public String getNumero() {
        return Numero;
    }

    public void setNumero(String numero) {
        this.Numero = numero;
    }

    public String getComplemento() {
        return Complemento;
    }

    public void setComplemento(String complemento) {
        this.Complemento = complemento;
    }

    public String getBairro() {
        return Bairro;
    }

    public void setBairro(String bairro) {
        this.Bairro = bairro;
    }

    public String getCep() {
        return CEP;
    }

    public void setCep(String cep) {
        this.CEP = cep;
    }

    public Cidade getCidadeId() {
        return Cidade_id;
    }

    public void setCidadeId(Cidade cidade) {
        this.Cidade_id = cidade;
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
