package com.AchadosPerdidos.API.Domain.Repository;

import com.AchadosPerdidos.API.Domain.Entity.Foto;
import com.AchadosPerdidos.API.Domain.Repository.Interfaces.IFotosRepository;
import com.AchadosPerdidos.API.Infrastruture.DataBase.FotosQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FotosRepository implements IFotosRepository {

    @Autowired
    private FotosQueries fotosQueries;

    @Override
    public List<Foto> findAll() {
        return fotosQueries.findAll();
    }

    @Override
    public Foto findById(int id) {
        return fotosQueries.findById(id);
    }

    @Override
    public Foto save(Foto foto) {
        if (foto.getId() == null || foto.getId() == 0) {
            return fotosQueries.insert(foto);
        } else {
            return fotosQueries.update(foto);
        }
    }

    @Override
    public boolean deleteById(int id) {
        return fotosQueries.deleteById(id);
    }

    @Override
    public List<Foto> findActive() {
        return fotosQueries.findActive();
    }

    @Override
    public List<Foto> findByUser(int userId) {
        return fotosQueries.findByUser(userId);
    }

    @Override
    public List<Foto> findByItem(int itemId) {
        return fotosQueries.findByItem(itemId);
    }

    @Override
    public List<Foto> findProfilePhotos(int userId) {
        return fotosQueries.findProfilePhotos(userId);
    }

    @Override
    public List<Foto> findItemPhotos(int itemId) {
        return fotosQueries.findItemPhotos(itemId);
    }

    @Override
    public Foto findMainItemPhoto(int itemId) {
        return fotosQueries.findMainItemPhoto(itemId);
    }

    @Override
    public Foto findProfilePhoto(int userId) {
        return fotosQueries.findProfilePhoto(userId);
    }
}
