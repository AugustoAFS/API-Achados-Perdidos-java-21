package com.AchadosPerdidos.API.Domain.Repository.Interfaces;

import com.AchadosPerdidos.API.Domain.Entity.Foto;
import java.util.List;

public interface IFotosRepository {
    List<Foto> findAll();
    Foto findById(int id);
    Foto save(Foto foto);
    boolean deleteById(int id);
    List<Foto> findActive();
    List<Foto> findByUser(int userId);
    List<Foto> findByItem(int itemId);
    List<Foto> findProfilePhotos(int userId);
    List<Foto> findItemPhotos(int itemId);
    Foto findMainItemPhoto(int itemId);
    Foto findProfilePhoto(int userId);
}
