package com.AchadosPerdidos.API.Application.Services.Interfaces;

import com.AchadosPerdidos.API.Application.DTOs.FotoUsuario.FotoUsuarioListDTO;

public interface IFotoUsuarioService {
    FotoUsuarioListDTO getAllFotosUsuario();
    FotoUsuarioListDTO getActiveFotosUsuario();
}
