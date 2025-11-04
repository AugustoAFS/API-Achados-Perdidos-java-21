package com.AchadosPerdidos.API.Application.DTOs.Fotos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO completo de foto")
public class FotosDTO {
    @Schema(description = "ID da foto", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer id;

    @Schema(description = "URL da foto", example = "https://bucket.s3.amazonaws.com/fotos/foto_item_123.jpg")
    private String url;

    @Schema(description = "Provedor de armazenamento", example = "S3")
    private String provedorArmazenamento;

    @Schema(description = "Chave de armazenamento", example = "fotos/foto_item_123.jpg")
    private String chaveArmazenamento;

    @Schema(description = "Nome original do arquivo", example = "foto_item_123.jpg")
    private String nomeArquivoOriginal;

    @Schema(description = "Tamanho em bytes", example = "1024000")
    private Long tamanhoArquivoBytes;

    @Schema(description = "Data de criação", example = "2024-01-01T00:00:00")
    private java.util.Date dtaCriacao;

    @Schema(description = "Flag de inativação", example = "false")
    private Boolean flgInativo;

    @Schema(description = "Data de remoção lógica", example = "2024-02-01T00:00:00")
    private java.util.Date dtaRemocao;
}
