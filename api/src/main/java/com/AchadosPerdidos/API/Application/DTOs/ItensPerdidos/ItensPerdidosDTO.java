package com.AchadosPerdidos.API.Application.DTOs.ItensPerdidos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Schema(description = "DTO completo de item perdido")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItensPerdidosDTO {
    
    @Schema(description = "ID único do item", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private int id;
    
    @Schema(description = "Nome do item", example = "Chave do Laboratório")
    private String nome;
    
    @Schema(description = "Descrição detalhada do item", example = "Chave do laboratório de informática, cor prata")
    private String descricao;
    
    @Schema(description = "Data e hora em que o item foi encontrado", example = "2024-01-01T10:30:00", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime encontradoEm;
    
    @Schema(description = "ID do usuário que relatou o item", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private int usuarioRelatorId;
    
    @Schema(description = "ID do local onde o item foi encontrado", example = "2")
    private int localId;
    
    @Schema(description = "ID do status do item", example = "1")
    private int statusItemId;
    
    @Schema(description = "Data de criação", example = "2024-01-01T00:00:00", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime dtaCriacao;
    
    @Schema(description = "Flag indicando se está inativo", example = "false")
    private Boolean flgInativo;
    
    @Schema(description = "Data de remoção", example = "null", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime dtaRemocao;

}

