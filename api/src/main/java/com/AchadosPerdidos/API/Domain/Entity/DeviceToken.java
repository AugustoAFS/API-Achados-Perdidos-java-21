package com.AchadosPerdidos.API.Domain.Entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "device_tokens", schema = "ap_achados_perdidos")
public class DeviceToken implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer Id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Usuario_id", nullable = false, foreignKey = @ForeignKey(name = "fk_device_tokens_usuario"))
    private Usuario Usuario_id;

    @Column(name = "Token", length = 255, nullable = false)
    private String Token;

    @Column(name = "Plataforma", length = 20, nullable = false)
    private String Plataforma;

    @Column(name = "Dta_Criacao", nullable = false, updatable = false)
    private LocalDateTime Dta_Criacao;

    @Column(name = "Dta_Atualizacao")
    private LocalDateTime Dta_Atualizacao;

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

    public Usuario getUsuario_id() {
        return Usuario_id;
    }

    public void setUsuario_id(Usuario usuario_id) {
        this.Usuario_id = usuario_id;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        this.Token = token;
    }

    public String getPlataforma() {
        return Plataforma;
    }

    public void setPlataforma(String plataforma) {
        this.Plataforma = plataforma;
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

