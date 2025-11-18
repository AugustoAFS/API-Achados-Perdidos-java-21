package com.AchadosPerdidos.API.Domain.Entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "itens_devolvido", schema = "ap_achados_perdidos")
public class ItemDevolvido implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String detalhes_devolucao;

    private Integer item_id;

    private Integer usuario_devolvedor_id;

    private Integer usuario_achou_id;

    private LocalDateTime Dta_Criacao;

    private Boolean Flg_Inativo;

    private LocalDateTime Dta_Remocao;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDetalhes_devolucao() {
        return detalhes_devolucao;
    }

    public void setDetalhes_devolucao(String detalhes_devolucao) {
        this.detalhes_devolucao = detalhes_devolucao;
    }

    public Integer getItem_id() {
        return item_id;
    }

    public void setItem_id(Integer item_id) {
        this.item_id = item_id;
    }

    public Integer getUsuario_devolvedor_id() {
        return usuario_devolvedor_id;
    }

    public void setUsuario_devolvedor_id(Integer usuario_devolvedor_id) {
        this.usuario_devolvedor_id = usuario_devolvedor_id;
    }

    public Integer getUsuario_achou_id() {
        return usuario_achou_id;
    }

    public void setUsuario_achou_id(Integer usuario_achou_id) {
        this.usuario_achou_id = usuario_achou_id;
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
