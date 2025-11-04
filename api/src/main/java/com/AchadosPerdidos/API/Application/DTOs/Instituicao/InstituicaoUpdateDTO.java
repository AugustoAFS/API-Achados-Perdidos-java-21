package com.AchadosPerdidos.API.Application.DTOs.Instituicao;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para atualização de instituição")
public class InstituicaoUpdateDTO {
    
    @Schema(description = "Nome da instituição", example = "Instituto Federal do Paraná")
    private String nome;
    
    @Schema(description = "Código da instituição", example = "IFPR")
    private String codigo;
    
    @Schema(description = "Tipo da instituição (PUBLICA ou PRIVADA)", example = "PUBLICA")
    private String tipo;
    
    @Schema(description = "CNPJ da instituição", example = "12345678000195")
    private String cnpj;
    
    @Schema(description = "Flag indicando se a instituição está inativa", example = "false")
    private Boolean flgInativo;
}
