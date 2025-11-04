package com.AchadosPerdidos.API.Application.Services.Interfaces;

import com.AchadosPerdidos.API.Application.DTOs.Cidade.CidadeDTO;
import com.AchadosPerdidos.API.Application.DTOs.Cidade.CidadeCreateDTO;
import com.AchadosPerdidos.API.Application.DTOs.Cidade.CidadeUpdateDTO;

import java.util.List;

public interface ICidadeService {
    List<CidadeDTO> getAllCidades();
    CidadeDTO getCidadeById(Integer id);
    CidadeDTO createCidade(CidadeCreateDTO createDTO);
    CidadeDTO updateCidade(Integer id, CidadeUpdateDTO updateDTO);
    CidadeDTO deleteCidade(Integer id);
    List<CidadeDTO> getCidadesByEstado(Integer estadoId);
    List<CidadeDTO> getActiveCidades();
}

