package com.AchadosPerdidos.API.Domain.Entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "fotos_usuario", schema = "ap_achados_perdidos")
public class FotoUsuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer Id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Usuario_id", nullable = false, foreignKey = @ForeignKey(name = "fk_fotos_usuario_usuario"))
    private Usuario Usuario_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Foto_id", nullable = false, foreignKey = @ForeignKey(name = "fk_fotos_usuario_foto"))
    private Foto Foto_id;

    @Column(name = "Dta_Criacao", nullable = false, updatable = false)
    private LocalDateTime Dta_Criacao;

    @Column(name = "Flg_Inativo", nullable = false)
    private Boolean Flg_Inativo;

    @Column(name = "Dta_Remocao")
    private LocalDateTime Dta_Remocao;

    public Usuario getUsuarioId() {
        return Usuario_id;
    }

    public void setUsuarioId(Usuario usuario_id) {
        this.Usuario_id = usuario_id;
    }
    
    public Foto getFotoId() {
        return Foto_id;
    }

    public void setFotoId(Foto foto_id) {
        this.Foto_id = foto_id;
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

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        this.Id = id;
    }
}
