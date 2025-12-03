package com.AchadosPerdidos.API.Application.Services.Interfaces;

import com.AchadosPerdidos.API.Application.DTOs.FotoUsuario.FotoUsuarioCreateDTO;
import com.AchadosPerdidos.API.Application.DTOs.FotoUsuario.FotoUsuarioDTO;
import com.AchadosPerdidos.API.Application.DTOs.FotoUsuario.FotoUsuarioListDTO;
import com.AchadosPerdidos.API.Application.DTOs.FotoUsuario.FotoUsuarioUpdateDTO;

public interface IFotoUsuarioService {
    FotoUsuarioListDTO getAllFotosUsuario();
    FotoUsuarioListDTO getActiveFotosUsuario();
}
