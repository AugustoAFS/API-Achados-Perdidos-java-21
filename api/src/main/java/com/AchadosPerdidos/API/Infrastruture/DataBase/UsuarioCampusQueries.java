package com.AchadosPerdidos.API.Infrastruture.DataBase;

import com.AchadosPerdidos.API.Domain.Entity.UsuarioCampus;
import com.AchadosPerdidos.API.Infrastruture.DataBase.Interfaces.IUsuarioCampusQueries;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Queries para operações de UsuarioCampus
 * CRUD básico é feito via JPA no UsuarioCampusRepository
 */
@Repository
public class UsuarioCampusQueries implements IUsuarioCampusQueries {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<UsuarioCampus> findActive() {
        TypedQuery<UsuarioCampus> query = entityManager.createQuery(
            "SELECT uc FROM UsuarioCampus uc WHERE uc.Flg_Inativo = false", UsuarioCampus.class);
        return query.getResultList();
    }

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

