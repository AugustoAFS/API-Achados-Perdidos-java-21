package com.AchadosPerdidos.API.Application.Services.Interfaces;

import com.AchadosPerdidos.API.Application.DTOs.Empresa.EmpresaDTO;
import com.AchadosPerdidos.API.Application.DTOs.Empresa.EmpresaListDTO;

public interface IEmpresaService {
    EmpresaListDTO getAllEmpresas();
    EmpresaDTO getEmpresaById(int id);
    EmpresaDTO createEmpresa(EmpresaDTO empresaDTO);
    EmpresaDTO updateEmpresa(int id, EmpresaDTO empresaDTO);
    boolean deleteEmpresa(int id);
    EmpresaListDTO getActiveEmpresas();
    EmpresaListDTO getEmpresasByCountry(String paisSede);
}
