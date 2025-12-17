package com.AchadosPerdidos.API.Domain.Repository;

import com.AchadosPerdidos.API.Domain.Entity.FotoItem;
import com.AchadosPerdidos.API.Domain.Repository.Interfaces.IFotoItemRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Repository para gerenciar associação entre Itens e suas Fotos
 * Usa JPA para CRUD básico
 */
@Repository
public class FotoItemRepository implements IFotoItemRepository {

    @PersistenceContext
    private EntityManager entityManager;

    // CRUD básico usando JPA
    @Override
    public List<FotoItem> findAll() {
        TypedQuery<FotoItem> query = entityManager.createQuery("SELECT fi FROM FotoItem fi", FotoItem.class);
        return query.getResultList();
    }

    @Override
    public Optional<FotoItem> findById(Integer id) {
        FotoItem fotoItem = entityManager.find(FotoItem.class, id);
        return Optional.ofNullable(fotoItem);
    }

    @Override
    @Transactional
    public FotoItem save(FotoItem fotoItem) {
        if (fotoItem.getId() == null || fotoItem.getId() == 0) {
            entityManager.persist(fotoItem);
            return fotoItem;
        } else {
            return entityManager.merge(fotoItem);
        }
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        FotoItem fotoItem = entityManager.find(FotoItem.class, id);
        if (fotoItem != null) {
            entityManager.remove(fotoItem);
        }
    }
    
    // Queries customizadas (necessário porque o campo é Flg_Inativo com underscore)
    @Override
    public List<FotoItem> findActive() {
        TypedQuery<FotoItem> query = entityManager.createQuery(
            "SELECT fi FROM FotoItem fi WHERE fi.Flg_Inativo = false", FotoItem.class);
        return query.getResultList();
    }
    
    // Queries customizadas simples
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
    @Transactional
    public boolean deleteByItemIdAndFotoId(Integer itemId, Integer fotoId) {
        FotoItem fotoItem = findByItemIdAndFotoId(itemId, fotoId);
        if (fotoItem != null) {
            entityManager.remove(fotoItem);
            return true;
        }
        return false;
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

