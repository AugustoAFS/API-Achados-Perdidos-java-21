package com.AchadosPerdidos.API.Application.DTOs.ItemPerdido;

import com.AchadosPerdidos.API.Application.DTOs.Fotos.FotosCreateDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "DTO para criacao de item perdido com seus dados completos")
public class ItemPerdidoCreateDTO {
    
    // Dados do Item
    @Schema(description = "Nome do item", example = "Celular Samsung Galaxy", required = true)
    private String nome;
    
    @Schema(description = "Descricao do item", example = "Celular preto, modelo Galaxy S21", required = true)
    private String descricao;
    
    @Schema(description = "ID do local onde o item foi perdido", example = "1", required = true)
    private Integer localId;
    
    @Schema(description = "ID do usuario que perdeu o item", example = "1", required = true)
    private Integer usuarioRelatorId;
    
    // Dados específicos do ItemPerdido
    @Schema(description = "Data em que o item foi perdido", example = "2024-01-01T00:00:00")
    private LocalDateTime perdidoEm;
    
    // Lista de fotos do item
    @Schema(description = "Lista de fotos do item")
    private List<FotosCreateDTO> fotos;

    public ItemPerdidoCreateDTO() {
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

    public LocalDateTime getPerdidoEm() {
        return perdidoEm;
    }

    public void setPerdidoEm(LocalDateTime perdidoEm) {
        this.perdidoEm = perdidoEm;
    }

    public List<FotosCreateDTO> getFotos() {
        return fotos;
    }

    public void setFotos(List<FotosCreateDTO> fotos) {
        this.fotos = fotos;
    }
}
