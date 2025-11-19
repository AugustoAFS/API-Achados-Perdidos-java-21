package com.AchadosPerdidos.API.Domain.Entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "fotos_item", schema = "ap_achados_perdidos")
public class FotoItem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer Id;

    @Column(name = "Item_id", nullable = false)
    private Integer Item_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Foto_id", nullable = false, foreignKey = @ForeignKey(name = "fk_fotos_item_foto"))
    private Foto Foto_id;

    @Column(name = "Dta_Criacao", nullable = false, updatable = false)
    private LocalDateTime Dta_Criacao;

    @Column(name = "Flg_Inativo", nullable = false)
    private Boolean Flg_Inativo;

    @Column(name = "Dta_Remocao")
    private LocalDateTime Dta_Remocao;




    public Integer getItemId() {
        return Item_id;
    }

    public void setItemId(Integer item_id) {
        this.Item_id = item_id;
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

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        this.Id = id;
    }
}
