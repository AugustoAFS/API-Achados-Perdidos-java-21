package com.AchadosPerdidos.API.Domain.Entity;

import com.AchadosPerdidos.API.Domain.Enum.Provedor_Armazenamento;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Fotos {
    private Integer id;
    private String url;
    private Provedor_Armazenamento provedorArmazenamento;
    private String chaveArmazenamento;
    private String nomeArquivoOriginal;
    private Long tamanhoArquivoBytes;
    private Date dtaCriacao;
    private Boolean flgInativo;
    private Date dtaRemocao;
}