package com.AchadosPerdidos.API.Application.Services.Interfaces;

import com.AchadosPerdidos.API.Application.DTOs.FotoUsuario.FotoUsuarioCreateDTO;
import com.AchadosPerdidos.API.Application.DTOs.FotoUsuario.FotoUsuarioDTO;
import com.AchadosPerdidos.API.Application.DTOs.FotoUsuario.FotoUsuarioListDTO;
import com.AchadosPerdidos.API.Application.DTOs.FotoUsuario.FotoUsuarioUpdateDTO;

public interface IFotoUsuarioService {
    FotoUsuarioListDTO getAllFotosUsuario();
    FotoUsuarioDTO getFotoUsuarioByUsuarioIdAndFotoId(Integer usuarioId, Integer fotoId);
    FotoUsuarioDTO createFotoUsuario(FotoUsuarioCreateDTO createDTO);
    FotoUsuarioDTO updateFotoUsuario(Integer usuarioId, Integer fotoId, FotoUsuarioUpdateDTO updateDTO);
    boolean deleteFotoUsuario(Integer usuarioId, Integer fotoId);
    FotoUsuarioListDTO getActiveFotosUsuario();
    FotoUsuarioListDTO findByUsuarioId(Integer usuarioId);
    FotoUsuarioListDTO findByFotoId(Integer fotoId);
}

