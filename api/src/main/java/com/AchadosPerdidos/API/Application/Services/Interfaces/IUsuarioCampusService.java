package com.AchadosPerdidos.API.Application.Services.Interfaces;

import com.AchadosPerdidos.API.Application.DTOs.UsuarioCampus.UsuarioCampusCreateDTO;
import com.AchadosPerdidos.API.Application.DTOs.UsuarioCampus.UsuarioCampusDTO;
import com.AchadosPerdidos.API.Application.DTOs.UsuarioCampus.UsuarioCampusListDTO;
import com.AchadosPerdidos.API.Application.DTOs.UsuarioCampus.UsuarioCampusUpdateDTO;

import java.util.List;

public interface IUsuarioCampusService {
    List<UsuarioCampusListDTO> getAllUsuarioCampus();
    UsuarioCampusDTO createUsuarioCampus(UsuarioCampusCreateDTO createDTO);
    UsuarioCampusListDTO getActiveUsuarioCampus();
    List<UsuarioCampusListDTO> getUsuarioCampusByUsuarioId(Integer id);
    UsuarioCampusDTO updateUsuarioCampus(Integer usuarioId, Integer campusId, UsuarioCampusUpdateDTO updateDTO);
    boolean deleteUsuarioCampus(Integer usuarioId, Integer campusId);
}

