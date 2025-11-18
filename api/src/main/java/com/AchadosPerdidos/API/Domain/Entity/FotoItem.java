package com.AchadosPerdidos.API.Domain.Entity;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "fotos_item", schema = "ap_achados_perdidos")
public class FotoItem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer item_id;

    private Integer foto_id;

    private LocalDateTime Dta_Criacao;

    private Boolean Flg_Inativo;
    
    private LocalDateTime Dta_Remocao;

    public Integer getItemId() {
        return item_id;
    }

    public void setItemId(Integer item_id) {
        this.item_id = item_id;
    }

    public Integer getFotoId() {
        return foto_id;
    }

    public void setFotoId(Integer foto_id) {
        this.foto_id = foto_id;
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
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
