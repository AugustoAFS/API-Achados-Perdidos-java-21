package com.AchadosPerdidos.API.Domain.Repository;

import com.AchadosPerdidos.API.Domain.Entity.Estado;
import com.AchadosPerdidos.API.Domain.Repository.Interfaces.IEstadoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Repository para gerenciar Estados (UF)
 * Usa JPA para CRUD básico
 */
@Repository
public class EstadoRepository implements IEstadoRepository {

    @PersistenceContext
    private EntityManager entityManager;

    // CRUD básico usando JPA
    @Override
    public List<Estado> findAll() {
        TypedQuery<Estado> query = entityManager.createQuery("SELECT e FROM Estado e ORDER BY e.UF", Estado.class);
        return query.getResultList();
    }

    @Override
    public Optional<Estado> findById(Integer id) {
        Estado estado = entityManager.find(Estado.class, id);
        return Optional.ofNullable(estado);
    }

    @Override
    @Transactional
    public Estado save(Estado estado) {
        if (estado.getId() == null || estado.getId() == 0) {
            entityManager.persist(estado);
            return estado;
        } else {
            return entityManager.merge(estado);
        }
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        Estado estado = entityManager.find(Estado.class, id);
        if (estado != null) {
            entityManager.remove(estado);
        }
    }
    
    // Queries customizadas (necessário porque o campo é Flg_Inativo com underscore)
    @Override
    public List<Estado> findActive() {
        TypedQuery<Estado> query = entityManager.createQuery(
            "SELECT e FROM Estado e WHERE e.Flg_Inativo = false ORDER BY e.UF", Estado.class);
        return query.getResultList();
    }
    
    @Override
    public Estado findByUf(String uf) {
        TypedQuery<Estado> query = entityManager.createQuery(
            "SELECT e FROM Estado e WHERE e.UF = :uf AND e.Flg_Inativo = false", Estado.class);
        query.setParameter("uf", uf);
        List<Estado> resultados = query.getResultList();
        return resultados.isEmpty() ? null : resultados.get(0);
    }
}

