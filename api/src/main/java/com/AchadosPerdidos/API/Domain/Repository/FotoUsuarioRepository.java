package com.AchadosPerdidos.API.Domain.Repository;

import com.AchadosPerdidos.API.Domain.Entity.FotoUsuario;
import com.AchadosPerdidos.API.Domain.Repository.Interfaces.IFotoUsuarioRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Repository para gerenciar associação entre Usuários e suas Fotos de Perfil
 * Usa JPA para CRUD básico
 */
@Repository
public class FotoUsuarioRepository implements IFotoUsuarioRepository {

    @PersistenceContext
    private EntityManager entityManager;

    // CRUD básico usando JPA
    @Override
    public List<FotoUsuario> findAll() {
        TypedQuery<FotoUsuario> query = entityManager.createQuery("SELECT fu FROM FotoUsuario fu", FotoUsuario.class);
        return query.getResultList();
    }

    @Override
    public Optional<FotoUsuario> findById(Integer id) {
        FotoUsuario fotoUsuario = entityManager.find(FotoUsuario.class, id);
        return Optional.ofNullable(fotoUsuario);
    }

    @Override
    @Transactional
    public FotoUsuario save(FotoUsuario fotoUsuario) {
        if (fotoUsuario.getId() == null || fotoUsuario.getId() == 0) {
            entityManager.persist(fotoUsuario);
            return fotoUsuario;
        } else {
            return entityManager.merge(fotoUsuario);
        }
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        FotoUsuario fotoUsuario = entityManager.find(FotoUsuario.class, id);
        if (fotoUsuario != null) {
            entityManager.remove(fotoUsuario);
        }
    }
    
    // Queries customizadas (necessário porque o campo é Flg_Inativo com underscore)
    @Override
    public List<FotoUsuario> findActive() {
        TypedQuery<FotoUsuario> query = entityManager.createQuery(
            "SELECT fu FROM FotoUsuario fu WHERE fu.Flg_Inativo = false", FotoUsuario.class);
        return query.getResultList();
    }
    
    // Queries customizadas simples
    @Override
    public FotoUsuario findByUsuarioIdAndFotoId(Integer usuarioId, Integer fotoId) {
        TypedQuery<FotoUsuario> query = entityManager.createQuery(
            "SELECT fu FROM FotoUsuario fu WHERE fu.Usuario_id.Id = :usuarioId AND fu.Foto_id.Id = :fotoId", 
            FotoUsuario.class);
        query.setParameter("usuarioId", usuarioId);
        query.setParameter("fotoId", fotoId);
        List<FotoUsuario> resultados = query.getResultList();
        return resultados.isEmpty() ? null : resultados.get(0);
    }
    
    @Override
    @Transactional
    public boolean deleteByUsuarioIdAndFotoId(Integer usuarioId, Integer fotoId) {
        FotoUsuario fotoUsuario = findByUsuarioIdAndFotoId(usuarioId, fotoId);
        if (fotoUsuario != null) {
            entityManager.remove(fotoUsuario);
            return true;
        }
        return false;
    }
    
    @Override
    public List<FotoUsuario> findByUsuarioId(Integer usuarioId) {
        TypedQuery<FotoUsuario> query = entityManager.createQuery(
            "SELECT fu FROM FotoUsuario fu WHERE fu.Usuario_id.Id = :usuarioId AND fu.Flg_Inativo = false", 
            FotoUsuario.class);
        query.setParameter("usuarioId", usuarioId);
        return query.getResultList();
    }
    
    @Override
    public List<FotoUsuario> findByFotoId(Integer fotoId) {
        TypedQuery<FotoUsuario> query = entityManager.createQuery(
            "SELECT fu FROM FotoUsuario fu WHERE fu.Foto_id.Id = :fotoId AND fu.Flg_Inativo = false", 
            FotoUsuario.class);
        query.setParameter("fotoId", fotoId);
        return query.getResultList();
    }
}

