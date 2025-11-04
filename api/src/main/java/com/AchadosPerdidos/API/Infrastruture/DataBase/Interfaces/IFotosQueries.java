package com.AchadosPerdidos.API.Infrastruture.DataBase.Interfaces;

import com.AchadosPerdidos.API.Domain.Entity.Fotos;
import java.util.List;

public interface IFotosQueries {
    List<Fotos> findAll();
    Fotos findById(int id);
    Fotos insert(Fotos fotos);
    Fotos update(Fotos fotos);
    boolean deleteById(int id);
    List<Fotos> findActive();
    List<Fotos> findByUser(int userId);
    List<Fotos> findByItem(int itemId);
    List<Fotos> findProfilePhotos(int userId);
    List<Fotos> findItemPhotos(int itemId);
    Fotos findMainItemPhoto(int itemId);
    Fotos findProfilePhoto(int userId);
}
