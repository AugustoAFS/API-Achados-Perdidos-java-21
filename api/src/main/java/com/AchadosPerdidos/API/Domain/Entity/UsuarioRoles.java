package com.AchadosPerdidos.API.Domain.Entity;

import java.time.LocalDateTime;

public class UsuarioRoles {
    private int Usuario_Id;
    private int Role_Id;
    private LocalDateTime Dta_Criacao;
    private Boolean Flg_Inativo;
    private LocalDateTime Dta_Remocao;

    public UsuarioRoles() {}

    public int getUsuario_Id() { return Usuario_Id; }
    public void setUsuario_Id(int usuario_Id) { this.Usuario_Id = usuario_Id; }
    
    public int getRole_Id() { return Role_Id; }
    public void setRole_Id(int role_Id) { this.Role_Id = role_Id; }
    
    public LocalDateTime getDta_Criacao() { return Dta_Criacao; }
    public void setDta_Criacao(LocalDateTime dta_Criacao) { this.Dta_Criacao = dta_Criacao; }
    
    public Boolean getFlg_Inativo() { return Flg_Inativo; }
    public void setFlg_Inativo(Boolean flg_Inativo) { this.Flg_Inativo = flg_Inativo; }
    
    public LocalDateTime getDta_Remocao() { return Dta_Remocao; }
    public void setDta_Remocao(LocalDateTime dta_Remocao) { this.Dta_Remocao = dta_Remocao; }
}
