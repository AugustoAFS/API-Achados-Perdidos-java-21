package com.AchadosPerdidos.API.Application.Services.Interfaces;

import com.AchadosPerdidos.API.Application.DTOs.UsuarioCampus.UsuarioCampusCreateDTO;
import com.AchadosPerdidos.API.Application.DTOs.UsuarioCampus.UsuarioCampusDTO;
import com.AchadosPerdidos.API.Application.DTOs.UsuarioCampus.UsuarioCampusListDTO;
import com.AchadosPerdidos.API.Application.DTOs.UsuarioCampus.UsuarioCampusUpdateDTO;

public interface IUsuarioCampusService {
    UsuarioCampusListDTO getAllUsuarioCampus();
    UsuarioCampusDTO createUsuarioCampus(UsuarioCampusCreateDTO createDTO);
    UsuarioCampusListDTO getActiveUsuarioCampus();
    UsuarioCampusDTO updateUsuarioCampus(Integer usuarioId, Integer campusId, UsuarioCampusUpdateDTO updateDTO);
    boolean deleteUsuarioCampus(Integer usuarioId, Integer campusId);
}

