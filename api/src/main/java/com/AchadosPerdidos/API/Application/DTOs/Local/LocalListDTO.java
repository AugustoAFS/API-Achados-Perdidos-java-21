package com.AchadosPerdidos.API.Application.DTOs.Local;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public class LocalListDTO {
    @Schema(description = "Lista de locais")
    private List<LocalDTO> Locais;

    public LocalListDTO() {
        // Construtor padrao para frameworks de serializacao
    }

    public LocalListDTO(List<LocalDTO> Locais) {
        this.Locais = Locais;
    }

    public List<LocalDTO> getLocais() {
        return Locais;
    }

    public void setLocais(List<LocalDTO> Locais) {
        this.Locais = Locais;
    }
}

