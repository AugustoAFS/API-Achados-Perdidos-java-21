package com.AchadosPerdidos.API.Domain.Repository;

import com.AchadosPerdidos.API.Domain.Entity.Cidade;
import com.AchadosPerdidos.API.Domain.Repository.Interfaces.ICidadeRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Repository para gerenciar Cidades
 * Usa JPA para CRUD básico
 */
@Repository
public class CidadeRepository implements ICidadeRepository {

    @PersistenceContext
    private EntityManager entityManager;

    // CRUD básico usando JPA
    @Override
    public List<Cidade> findAll() {
        TypedQuery<Cidade> query = entityManager.createQuery("SELECT c FROM Cidade c ORDER BY c.Nome", Cidade.class);
        return query.getResultList();
    }

    @Override
    public Optional<Cidade> findById(Integer id) {
        Cidade cidade = entityManager.find(Cidade.class, id);
        return Optional.ofNullable(cidade);
    }

    @Override
    @Transactional
    public Cidade save(Cidade cidade) {
        if (cidade.getId() == null || cidade.getId() == 0) {
            entityManager.persist(cidade);
            return cidade;
        } else {
            return entityManager.merge(cidade);
        }
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        Cidade cidade = entityManager.find(Cidade.class, id);
        if (cidade != null) {
            entityManager.remove(cidade);
        }
    }
    
    // Queries customizadas (necessário porque o campo é Flg_Inativo com underscore)
    @Override
    public List<Cidade> findActive() {
        TypedQuery<Cidade> query = entityManager.createQuery(
            "SELECT c FROM Cidade c WHERE c.Flg_Inativo = false ORDER BY c.Nome", Cidade.class);
        return query.getResultList();
    }
    
    // Query customizada simples (sem JOIN)
    @Override
    public List<Cidade> findByEstado(Integer estadoId) {
        TypedQuery<Cidade> query = entityManager.createQuery(
            "SELECT c FROM Cidade c WHERE c.Estado_id.Id = :estadoId AND c.Flg_Inativo = false ORDER BY c.Nome", 
            Cidade.class);
        query.setParameter("estadoId", estadoId);
        return query.getResultList();
    }
}

