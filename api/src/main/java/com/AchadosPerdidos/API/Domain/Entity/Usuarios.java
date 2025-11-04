package com.AchadosPerdidos.API.Domain.Entity;

import lombok.Data;
import java.util.Date;

@Data
public class Usuarios {
    private Integer id;
    private String nomeCompleto;
    private String cpf;
    private String email;
    private String hashSenha;
    private String matricula;
    private String numeroTelefone;
    private Integer empresaId;
    private Integer enderecoId;
    private Date dtaCriacao;
    private Boolean flgInativo;
    private Date dtaRemocao;
}