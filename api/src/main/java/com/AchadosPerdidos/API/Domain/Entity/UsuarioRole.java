package com.AchadosPerdidos.API.Domain.Entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "usuario_roles", schema = "ap_achados_perdidos")
public class UsuarioRole implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer usuario_id;

    private Integer role_id;

    private LocalDateTime Dta_Criacao;

    private Boolean Flg_Inativo;

    private LocalDateTime Dta_Remocao;

    public Integer getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(Integer usuario_id) {
        this.usuario_id = usuario_id;
    }

    public Integer getRole_id() {
        return role_id;
    }

    public void setRole_id(Integer role_id) {
        this.role_id = role_id;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
