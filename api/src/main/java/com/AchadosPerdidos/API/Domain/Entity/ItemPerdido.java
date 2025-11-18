package com.AchadosPerdidos.API.Domain.Entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "itens_perdidos", schema = "ap_achados_perdidos")
public class ItemPerdido implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDateTime perdido_em;

    private LocalDateTime Dta_Criacao;

    private Boolean Flg_Inativo;

    private LocalDateTime Dta_Remocao;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getPerdido_em() {
        return perdido_em;
    }

    public void setPerdido_em(LocalDateTime perdido_em) {
        this.perdido_em = perdido_em;
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


