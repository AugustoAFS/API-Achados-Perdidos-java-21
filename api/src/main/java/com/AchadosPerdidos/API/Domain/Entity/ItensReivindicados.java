package com.AchadosPerdidos.API.Domain.Entity;

import java.time.LocalDateTime;

public class ItensReivindicados {
    private int Id;
    private String Detalhes_Reivindicacao;
    private int Item_Id;
    private int Usuario_Reivindicador_Id;
    private Integer Usuario_Achou_Id;
    private LocalDateTime Dta_Criacao;
    private Boolean Flg_Inativo;
    private LocalDateTime Dta_Remocao;

    public ItensReivindicados() {}

    public int getId() { return Id; }
    public void setId(int id) { this.Id = id; }
    
    public String getDetalhes_Reivindicacao() { return Detalhes_Reivindicacao; }
    public void setDetalhes_Reivindicacao(String detalhes_Reivindicacao) { this.Detalhes_Reivindicacao = detalhes_Reivindicacao; }
    
    public int getItem_Id() { return Item_Id; }
    public void setItem_Id(int item_Id) { this.Item_Id = item_Id; }
    
    public int getUsuario_Reivindicador_Id() { return Usuario_Reivindicador_Id; }
    public void setUsuario_Reivindicador_Id(int usuario_Reivindicador_Id) { this.Usuario_Reivindicador_Id = usuario_Reivindicador_Id; }
    
    public Integer getUsuario_Achou_Id() { return Usuario_Achou_Id; }
    public void setUsuario_Achou_Id(Integer usuario_Achou_Id) { this.Usuario_Achou_Id = usuario_Achou_Id; }
    
    public LocalDateTime getDta_Criacao() { return Dta_Criacao; }
    public void setDta_Criacao(LocalDateTime dta_Criacao) { this.Dta_Criacao = dta_Criacao; }
    
    public Boolean getFlg_Inativo() { return Flg_Inativo; }
    public void setFlg_Inativo(Boolean flg_Inativo) { this.Flg_Inativo = flg_Inativo; }
    
    public LocalDateTime getDta_Remocao() { return Dta_Remocao; }
    public void setDta_Remocao(LocalDateTime dta_Remocao) { this.Dta_Remocao = dta_Remocao; }
}
