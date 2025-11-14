package com.AchadosPerdidos.API.Domain.Entity;

import java.time.LocalDateTime;

public class ItensPerdidos {
    private int Id;
    private String Nome;
    private String Descricao;
    private LocalDateTime Encontrado_Em;
    private int Usuario_Relator_Id;
    private int Local_Id;
    private int Status_Item_Id;
    private LocalDateTime Dta_Criacao;
    private Boolean Flg_Inativo;
    private LocalDateTime Dta_Remocao;

    public ItensPerdidos() {}

    public int getId() { return Id; }
    public void setId(int id) { this.Id = id; }
    
    public String getNome() { return Nome; }
    public void setNome(String nome) { this.Nome = nome; }
    
    public String getDescricao() { return Descricao; }
    public void setDescricao(String descricao) { this.Descricao = descricao; }
    
    public LocalDateTime getEncontrado_Em() { return Encontrado_Em; }
    public void setEncontrado_Em(LocalDateTime encontrado_Em) { this.Encontrado_Em = encontrado_Em; }
    
    public int getUsuario_Relator_Id() { return Usuario_Relator_Id; }
    public void setUsuario_Relator_Id(int usuario_Relator_Id) { this.Usuario_Relator_Id = usuario_Relator_Id; }
    
    public int getLocal_Id() { return Local_Id; }
    public void setLocal_Id(int local_Id) { this.Local_Id = local_Id; }
    
    public int getStatus_Item_Id() { return Status_Item_Id; }
    public void setStatus_Item_Id(int status_Item_Id) { this.Status_Item_Id = status_Item_Id; }
    
    public LocalDateTime getDta_Criacao() { return Dta_Criacao; }
    public void setDta_Criacao(LocalDateTime dta_Criacao) { this.Dta_Criacao = dta_Criacao; }
    
    public Boolean getFlg_Inativo() { return Flg_Inativo; }
    public void setFlg_Inativo(Boolean flg_Inativo) { this.Flg_Inativo = flg_Inativo; }
    
    public LocalDateTime getDta_Remocao() { return Dta_Remocao; }
    public void setDta_Remocao(LocalDateTime dta_Remocao) { this.Dta_Remocao = dta_Remocao; }
}
