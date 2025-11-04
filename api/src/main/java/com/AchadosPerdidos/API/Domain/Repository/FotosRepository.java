package com.AchadosPerdidos.API.Domain.Repository;

import com.AchadosPerdidos.API.Domain.Entity.Fotos;
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
    public List<Fotos> findAll() {
        return fotosQueries.findAll();
    }

    @Override
    public Fotos findById(int id) {
        return fotosQueries.findById(id);
    }

    @Override
    public Fotos save(Fotos fotos) {
        if (fotos.getId() == null || fotos.getId() == 0) {
            return fotosQueries.insert(fotos);
        } else {
            return fotosQueries.update(fotos);
        }
    }

    @Override
    public boolean deleteById(int id) {
        return fotosQueries.deleteById(id);
    }

    @Override
    public List<Fotos> findActive() {
        return fotosQueries.findActive();
    }

    @Override
    public List<Fotos> findByUser(int userId) {
        return fotosQueries.findByUser(userId);
    }

    @Override
    public List<Fotos> findByItem(int itemId) {
        return fotosQueries.findByItem(itemId);
    }

    @Override
    public List<Fotos> findProfilePhotos(int userId) {
        return fotosQueries.findProfilePhotos(userId);
    }

    @Override
    public List<Fotos> findItemPhotos(int itemId) {
        return fotosQueries.findItemPhotos(itemId);
    }

    @Override
    public Fotos findMainItemPhoto(int itemId) {
        return fotosQueries.findMainItemPhoto(itemId);
    }

    @Override
    public Fotos findProfilePhoto(int userId) {
        return fotosQueries.findProfilePhoto(userId);
    }
}
