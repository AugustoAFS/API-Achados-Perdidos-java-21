package com.AchadosPerdidos.API.Domain.Entity;

import com.AchadosPerdidos.API.Domain.Enum.Tipo_Item;
import com.AchadosPerdidos.API.Domain.Enum.Status_Item;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "itens", schema = "ap_achados_perdidos")
public class Itens implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer Id;

    @Column(name = "Nome", length = 255, nullable = false)
    private String Nome;

    @Column(name = "Descricao", columnDefinition = "TEXT", nullable = false)
    private String Descricao;

    @Enumerated(EnumType.STRING)
    @Column(name = "Tipo_item", length = 50, nullable = false)
    private Tipo_Item Tipo_item;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status_item", length = 50, nullable = false)
    private Status_Item Status_item;

    @Column(name = "Desc_Local_Item", columnDefinition = "TEXT", nullable = false)
    private String Desc_Local_Item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Usuario_relator_id", nullable = false, foreignKey = @ForeignKey(name = "fk_itens_usuario_relator"))
    private Usuario Usuario_relator_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Usuario_reivindicador_id", foreignKey = @ForeignKey(name = "fk_itens_usuario_reivindicador"))
    private Usuario Usuario_reivindicador_id;

    @Column(name = "Dta_Reivindicacao")
    private LocalDateTime Dta_Reivindicacao;

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

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        this.Nome = nome;
    }

    public String getDescricao() {
        return Descricao;
    }

    public void setDescricao(String descricao) {
        this.Descricao = descricao;
    }

    public Tipo_Item getTipoItem() {
        return Tipo_item;
    }

    public void setTipoItem(Tipo_Item tipo_item) {
        this.Tipo_item = tipo_item;
    }

    public Status_Item getStatus_item() {
        return Status_item;
    }

    public void setStatus_item(Status_Item status_item) {
        this.Status_item = status_item;
    }

    public String getDesc_Local_Item() {
        return Desc_Local_Item;
    }

    public void setDesc_Local_Item(String desc_Local_Item) {
        this.Desc_Local_Item = desc_Local_Item;
    }

    public Usuario getUsuario_relator_id() {
        return Usuario_relator_id;
    }

    public void setUsuario_relator_id(Usuario usuario_relator_id) {
        this.Usuario_relator_id = usuario_relator_id;
    }

    public Usuario getUsuario_reivindicador_id() {
        return Usuario_reivindicador_id;
    }

    public void setUsuario_reivindicador_id(Usuario usuario_reivindicador_id) {
        this.Usuario_reivindicador_id = usuario_reivindicador_id;
    }

    public LocalDateTime getDta_Reivindicacao() {
        return Dta_Reivindicacao;
    }

    public void setDta_Reivindicacao(LocalDateTime dta_Reivindicacao) {
        this.Dta_Reivindicacao = dta_Reivindicacao;
    }

    public LocalDateTime getDtaCriacao() {
        return Dta_Criacao;
    }

    public void setDtaCriacao(LocalDateTime dta_Criacao) {
        this.Dta_Criacao = dta_Criacao;
    }

    public Boolean getFlgInativo() {
        return Flg_Inativo;
    }

    public void setFlgInativo(Boolean flg_Inativo) {
        this.Flg_Inativo = flg_Inativo;
    }

    public LocalDateTime getDtaRemocao() {
        return Dta_Remocao;
    }

    public void setDtaRemocao(LocalDateTime dta_Remocao) {
        this.Dta_Remocao = dta_Remocao;
    }
}

