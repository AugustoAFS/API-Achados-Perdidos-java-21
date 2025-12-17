package com.AchadosPerdidos.API.Application.Services.Interfaces;

import com.AchadosPerdidos.API.Application.DTOs.Fotos.FotosDTO;
import com.AchadosPerdidos.API.Application.DTOs.Fotos.FotosListDTO;

public interface IFotosService {
    FotosListDTO getAllFotos();
    FotosDTO createFotoUsaurio(FotosDTO fotosDTO);
    FotosDTO createFotoItem(FotosDTO fotosDTO);
    FotosDTO updateFotoUsuario(int id, FotosDTO fotosDTO);
    FotosDTO updateFotoItem(int id, FotosDTO fotosDTO);
    boolean deleteFotoUsuario(int id);
    boolean deleteFotoItem(int id);
}
