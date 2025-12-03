package com.AchadosPerdidos.API.Infrastruture.DataBase.Interfaces;

import com.AchadosPerdidos.API.Domain.Entity.Foto;
import java.util.List;

public interface IFotosQueries {
    List<Foto> findByUser(int userId);
    List<Foto> findByItem(int itemId);
    List<Foto> findProfilePhotos(int userId);
    List<Foto> findItemPhotos(int itemId);
    Foto findMainItemPhoto(int itemId);
    Foto findProfilePhoto(int userId);
}
