package com.AchadosPerdidos.API.Application.DTOs.Fotos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.AchadosPerdidos.API.Domain.Enum.Provedor_Armazenamento;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FotosUpdateDTO {
    private String url;
    private Provedor_Armazenamento provedorArmazenamento;
    private String chaveArmazenamento;
    private String nomeArquivoOriginal;
    private Long tamanhoArquivoBytes;
    private Boolean flgInativo;
}
