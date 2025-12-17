package com.AchadosPerdidos.API.Infrastruture.DataBase;

import com.AchadosPerdidos.API.Domain.Entity.FotoItem;
import com.AchadosPerdidos.API.Infrastruture.DataBase.Interfaces.IFotoItemQueries;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Queries para operações de FotoItem
 * CRUD básico é feito via JPA no FotoItemRepository
 */
@Repository
public class FotoItemQueries implements IFotoItemQueries {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<FotoItem> findActive() {
        TypedQuery<FotoItem> query = entityManager.createQuery(
            "SELECT fi FROM FotoItem fi WHERE fi.Flg_Inativo = false", FotoItem.class);
        return query.getResultList();
    }

    @Override
    public FotoItem findByItemIdAndFotoId(Integer itemId, Integer fotoId) {
        TypedQuery<FotoItem> query = entityManager.createQuery(
            "SELECT fi FROM FotoItem fi WHERE fi.Item_id = :itemId AND fi.Foto_id.Id = :fotoId", 
            FotoItem.class);
        query.setParameter("itemId", itemId);
        query.setParameter("fotoId", fotoId);
        List<FotoItem> resultados = query.getResultList();
        return resultados.isEmpty() ? null : resultados.get(0);
    }

    @Override
    public List<FotoItem> findByItemId(Integer itemId) {
        TypedQuery<FotoItem> query = entityManager.createQuery(
            "SELECT fi FROM FotoItem fi WHERE fi.Item_id = :itemId AND fi.Flg_Inativo = false", 
            FotoItem.class);
        query.setParameter("itemId", itemId);
        return query.getResultList();
    }

    @Override
    public List<FotoItem> findByFotoId(Integer fotoId) {
        TypedQuery<FotoItem> query = entityManager.createQuery(
            "SELECT fi FROM FotoItem fi WHERE fi.Foto_id.Id = :fotoId AND fi.Flg_Inativo = false", 
            FotoItem.class);
        query.setParameter("fotoId", fotoId);
        return query.getResultList();
    }
}

