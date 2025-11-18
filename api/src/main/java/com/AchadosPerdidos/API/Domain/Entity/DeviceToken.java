package com.AchadosPerdidos.API.Domain.Entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "device_tokens", schema = "ap_achados_perdidos")
public class DeviceToken implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer usuario_id;

    private String token;

    private String plataforma;

    private LocalDateTime Dta_Criacao;

    private LocalDateTime Dta_Atualizacao;

    private Boolean Flg_Inativo;

    private LocalDateTime Dta_Remocao;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(Integer usuario_id) {
        this.usuario_id = usuario_id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(String plataforma) {
        this.plataforma = plataforma;
    }

    public LocalDateTime getDta_Criacao() {
        return Dta_Criacao;
    }

    public void setDta_Criacao(LocalDateTime Dta_Criacao) {
        this.Dta_Criacao = Dta_Criacao;
    }

    public LocalDateTime getDta_Atualizacao() {
        return Dta_Atualizacao;
    }

    public void setDta_Atualizacao(LocalDateTime Dta_Atualizacao) {
        this.Dta_Atualizacao = Dta_Atualizacao;
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

