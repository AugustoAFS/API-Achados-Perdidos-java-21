package com.AchadosPerdidos.API.Domain.Repository;

import com.AchadosPerdidos.API.Domain.Entity.Foto;
import com.AchadosPerdidos.API.Domain.Repository.Interfaces.IFotosRepository;
import com.AchadosPerdidos.API.Infrastruture.DataBase.FotosQueries;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Repository para gerenciar operações de Fotos (AWS S3)
 * Usa JPA para CRUD básico e Queries apenas para operações com JOINs
 */
@Repository
public class FotosRepository implements IFotosRepository {

    @PersistenceContext
    private EntityManager entityManager;
    
    @Autowired
    private FotosQueries fotosQueries; // Apenas para operações com JOINs

    // CRUD básico usando JPA
    @Override
    public List<Foto> findAll() {
        TypedQuery<Foto> query = entityManager.createQuery("SELECT f FROM Foto f ORDER BY f.Dta_Criacao DESC", Foto.class);
        return query.getResultList();
    }

    @Override
    public Foto findById(int id) {
        return entityManager.find(Foto.class, id);
    }

    @Override
    @Transactional
    public Foto save(Foto foto) {
        if (foto.getId() == null || foto.getId() == 0) {
            entityManager.persist(foto);
            return foto;
        } else {
            return entityManager.merge(foto);
        }
    }

    @Override
    @Transactional
    public boolean deleteById(int id) {
        Foto foto = entityManager.find(Foto.class, id);
        if (foto != null) {
            entityManager.remove(foto);
            return true;
        }
        return false;
    }

    @Override
    public List<Foto> findActive() {
        TypedQuery<Foto> query = entityManager.createQuery(
            "SELECT f FROM Foto f WHERE f.Flg_Inativo = false ORDER BY f.Dta_Criacao DESC", Foto.class);
        return query.getResultList();
    }

    // Operações complexas com JOINs - usam FotosQueries
    @Override
    public List<Foto> findByUser(int userId) {
        return fotosQueries.findByUser(userId); // Usa JOIN
    }

    @Override
    public List<Foto> findByItem(int itemId) {
        return fotosQueries.findByItem(itemId); // Usa JOIN
    }

    @Override
    public List<Foto> findProfilePhotos(int userId) {
        return fotosQueries.findProfilePhotos(userId); // Usa JOIN
    }

    @Override
    public List<Foto> findItemPhotos(int itemId) {
        return fotosQueries.findItemPhotos(itemId); // Usa JOIN
    }

    @Override
    public Foto findMainItemPhoto(int itemId) {
        return fotosQueries.findMainItemPhoto(itemId); // Usa JOIN
    }

    @Override
    public Foto findProfilePhoto(int userId) {
        return fotosQueries.findProfilePhoto(userId); // Usa JOIN
    }
}
