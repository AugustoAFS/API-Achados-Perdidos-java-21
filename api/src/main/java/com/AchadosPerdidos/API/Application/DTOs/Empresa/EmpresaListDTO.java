package com.AchadosPerdidos.API.Application.DTOs.Empresa;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "DTO para lista de empresas")

public class EmpresaListDTO {
    @Schema(description = "Lista de empresas")
    private List<EmpresaDTO> empresas;
    @Schema(description = "Total de empresas na lista")
    private int totalCount;

    public EmpresaListDTO() {
        // Construtor padrao para frameworks de serializacao
    }

    public EmpresaListDTO(List<EmpresaDTO> empresas, int totalCount) {
        this.empresas = empresas;
        this.totalCount = totalCount;
    }

    public List<EmpresaDTO> getEmpresas() {
        return empresas;
    }
    public int getTotalCount() {
        return totalCount;
    }
    
    public void setEmpresas(List<EmpresaDTO> empresas) {
        this.empresas = empresas;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
