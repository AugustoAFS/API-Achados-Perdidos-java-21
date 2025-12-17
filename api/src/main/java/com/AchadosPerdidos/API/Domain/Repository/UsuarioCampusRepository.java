package com.AchadosPerdidos.API.Domain.Repository;

import com.AchadosPerdidos.API.Domain.Entity.UsuarioCampus;
import com.AchadosPerdidos.API.Domain.Repository.Interfaces.IUsuarioCampusRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Repository para gerenciar associação entre Usuários e Campus
 * Usa JPA para CRUD básico
 */
@Repository
public class UsuarioCampusRepository implements IUsuarioCampusRepository {

    @PersistenceContext
    private EntityManager entityManager;

    // CRUD básico usando JPA
    @Override
    public List<UsuarioCampus> findAll() {
        TypedQuery<UsuarioCampus> query = entityManager.createQuery("SELECT uc FROM UsuarioCampus uc", UsuarioCampus.class);
        return query.getResultList();
    }

    @Override
    public Optional<UsuarioCampus> findById(Integer id) {
        UsuarioCampus usuarioCampus = entityManager.find(UsuarioCampus.class, id);
        return Optional.ofNullable(usuarioCampus);
    }

    @Override
    @Transactional
    public UsuarioCampus save(UsuarioCampus usuarioCampus) {
        if (usuarioCampus.getId() == null || usuarioCampus.getId() == 0) {
            entityManager.persist(usuarioCampus);
            return usuarioCampus;
        } else {
            return entityManager.merge(usuarioCampus);
        }
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        UsuarioCampus usuarioCampus = entityManager.find(UsuarioCampus.class, id);
        if (usuarioCampus != null) {
            entityManager.remove(usuarioCampus);
        }
    }
    
    // Queries customizadas (necessário porque o campo é Flg_Inativo com underscore)
    @Override
    public List<UsuarioCampus> findActive() {
        TypedQuery<UsuarioCampus> query = entityManager.createQuery(
            "SELECT uc FROM UsuarioCampus uc WHERE uc.Flg_Inativo = false", UsuarioCampus.class);
        return query.getResultList();
    }
    
    // Queries customizadas simples
    @Override
    public UsuarioCampus findByUsuarioIdAndCampusId(Integer usuarioId, Integer campusId) {
        TypedQuery<UsuarioCampus> query = entityManager.createQuery(
            "SELECT uc FROM UsuarioCampus uc WHERE uc.Usuario_id.Id = :usuarioId AND uc.Campus_id.Id = :campusId", 
            UsuarioCampus.class);
        query.setParameter("usuarioId", usuarioId);
        query.setParameter("campusId", campusId);
        List<UsuarioCampus> resultados = query.getResultList();
        return resultados.isEmpty() ? null : resultados.get(0);
    }
    
    @Override
    @Transactional
    public boolean deleteByUsuarioIdAndCampusId(Integer usuarioId, Integer campusId) {
        UsuarioCampus usuarioCampus = findByUsuarioIdAndCampusId(usuarioId, campusId);
        if (usuarioCampus != null) {
            entityManager.remove(usuarioCampus);
            return true;
        }
        return false;
    }
    
    @Override
    public List<UsuarioCampus> findByUsuarioId(Integer usuarioId) {
        TypedQuery<UsuarioCampus> query = entityManager.createQuery(
            "SELECT uc FROM UsuarioCampus uc WHERE uc.Usuario_id.Id = :usuarioId AND uc.Flg_Inativo = false", 
            UsuarioCampus.class);
        query.setParameter("usuarioId", usuarioId);
        return query.getResultList();
    }
    
    @Override
    public List<UsuarioCampus> findByCampusId(Integer campusId) {
        TypedQuery<UsuarioCampus> query = entityManager.createQuery(
            "SELECT uc FROM UsuarioCampus uc WHERE uc.Campus_id.Id = :campusId AND uc.Flg_Inativo = false", 
            UsuarioCampus.class);
        query.setParameter("campusId", campusId);
        return query.getResultList();
    }
}

