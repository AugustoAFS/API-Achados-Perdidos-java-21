package com.AchadosPerdidos.API.Application.Services.Interfaces;

import com.AchadosPerdidos.API.Application.DTOs.Estado.EstadoDTO;
import com.AchadosPerdidos.API.Application.DTOs.Estado.EstadoCreateDTO;
import com.AchadosPerdidos.API.Application.DTOs.Estado.EstadoUpdateDTO;

import java.util.List;

public interface IEstadoService {
    List<EstadoDTO> getAllEstados();
    EstadoDTO getEstadoById(Integer id);
    EstadoDTO getEstadoByUf(String uf);
    EstadoDTO createEstado(EstadoCreateDTO createDTO);
    EstadoDTO updateEstado(Integer id, EstadoUpdateDTO updateDTO);
    EstadoDTO deleteEstado(Integer id);
    List<EstadoDTO> getActiveEstados();
}

