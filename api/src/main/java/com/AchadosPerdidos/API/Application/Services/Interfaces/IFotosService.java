package com.AchadosPerdidos.API.Application.Services.Interfaces;

import com.AchadosPerdidos.API.Application.DTOs.Fotos.FotosDTO;
import com.AchadosPerdidos.API.Application.DTOs.Fotos.FotosListDTO;

public interface IFotosService {
    FotosListDTO getAllFotos();
    FotosDTO getFotoById(int id);
    FotosDTO createFoto(FotosDTO fotosDTO);
    FotosDTO updateFoto(int id, FotosDTO fotosDTO);
    boolean deleteFoto(int id);
    FotosListDTO getActiveFotos();
    FotosListDTO getFotosByUser(int userId);
    FotosListDTO getFotosByItem(int itemId);
    FotosListDTO getProfilePhotos(int userId);
    FotosListDTO getItemPhotos(int itemId);
    FotosDTO getMainItemPhoto(int itemId);
    FotosDTO getProfilePhoto(int userId);
}
