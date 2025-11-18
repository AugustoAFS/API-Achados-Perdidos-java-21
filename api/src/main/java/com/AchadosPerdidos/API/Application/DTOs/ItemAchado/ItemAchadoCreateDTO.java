package com.AchadosPerdidos.API.Application.DTOs.ItemAchado;

import com.AchadosPerdidos.API.Application.DTOs.Fotos.FotosCreateDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "DTO para criacao de item achado com seus dados completos")
public class ItemAchadoCreateDTO {
    
    // Dados do Item
    @Schema(description = "Nome do item", example = "Celular Samsung Galaxy", required = true)
    private String nome;
    
    @Schema(description = "Descricao do item", example = "Celular preto, modelo Galaxy S21", required = true)
    private String descricao;
    
    @Schema(description = "ID do local onde o item foi encontrado", example = "1", required = true)
    private Integer localId;
    
    @Schema(description = "ID do usuario que encontrou o item", example = "1", required = true)
    private Integer usuarioRelatorId;
    
    // Dados específicos do ItemAchado
    @Schema(description = "Data em que o item foi encontrado", example = "2024-01-01T00:00:00")
    private LocalDateTime encontradoEm;
    
    // Lista de fotos do item
    @Schema(description = "Lista de fotos do item")
    private List<FotosCreateDTO> fotos;

    public ItemAchadoCreateDTO() {
        // Construtor padrao para frameworks de serializacao
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getLocalId() {
        return localId;
    }

    public void setLocalId(Integer localId) {
        this.localId = localId;
    }

    public Integer getUsuarioRelatorId() {
        return usuarioRelatorId;
    }

    public void setUsuarioRelatorId(Integer usuarioRelatorId) {
        this.usuarioRelatorId = usuarioRelatorId;
    }

    public LocalDateTime getEncontradoEm() {
        return encontradoEm;
    }

    public void setEncontradoEm(LocalDateTime encontradoEm) {
        this.encontradoEm = encontradoEm;
    }

    public List<FotosCreateDTO> getFotos() {
        return fotos;
    }

    public void setFotos(List<FotosCreateDTO> fotos) {
        this.fotos = fotos;
    }
}
