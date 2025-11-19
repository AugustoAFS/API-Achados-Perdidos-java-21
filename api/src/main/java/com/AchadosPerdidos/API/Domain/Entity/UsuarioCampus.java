package com.AchadosPerdidos.API.Domain.Entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "usuario_campus", schema = "ap_achados_perdidos")
public class UsuarioCampus implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer Id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Usuario_id", nullable = false, foreignKey = @ForeignKey(name = "fk_usuariocampus_usuario"))
    private Usuario Usuario_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Campus_id", nullable = false, foreignKey = @ForeignKey(name = "fk_usuariocampus_campus"))
    private Campus Campus_id;

    @Column(name = "Dta_Criacao", nullable = false, updatable = false)
    private LocalDateTime Dta_Criacao;

    @Column(name = "Flg_Inativo", nullable = false)
    private Boolean Flg_Inativo;

    @Column(name = "Dta_Remocao")
    private LocalDateTime Dta_Remocao;




    public Usuario getUsuario_id() {
        return Usuario_id;
    }

    public void setUsuario_id(Usuario usuario_id) {
        this.Usuario_id = usuario_id;
    }

    public Campus getCampus_id() {
        return Campus_id;
    }

    public void setCampus_id(Campus campus_id) {
        this.Campus_id = campus_id;
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

    public LocalDateTime getDtaRemocao() {
        return Dta_Remocao;
    }

    public void setDtaRemocao(LocalDateTime Dta_Remocao) {
        this.Dta_Remocao = Dta_Remocao;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        this.Id = id;
    }
}

