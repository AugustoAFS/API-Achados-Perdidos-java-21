package com.AchadosPerdidos.API.Application.DTOs.Campus;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "DTO para lista de campi")
public class CampusListDTO{
    @Schema(description = "Lista de campi")
    private List<CampusDTO> campi;
    
    @Schema(description = "Total de campi na lista")
    private int totalCount;

    public CampusListDTO() {
        // Construtor padrao para frameworks de serializacao
    }

    public CampusListDTO(List<CampusDTO> campi, int totalCount) {
        this.campi = campi;
        this.totalCount = totalCount;
    }

    public List<CampusDTO> getCampi() {
        return campi;
    }

    public void setCampi(List<CampusDTO> campi) {
        this.campi = campi;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}