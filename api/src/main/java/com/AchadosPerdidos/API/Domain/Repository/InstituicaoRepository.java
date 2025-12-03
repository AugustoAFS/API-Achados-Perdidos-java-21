package com.AchadosPerdidos.API.Domain.Repository;

import com.AchadosPerdidos.API.Domain.Entity.Instituicoes;
import com.AchadosPerdidos.API.Domain.Repository.Interfaces.IInstituicaoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Repository para gerenciar Instituições de Ensino
 * Usa JPA para CRUD básico
 */
@Repository
public class InstituicaoRepository implements IInstituicaoRepository {

    @PersistenceContext
    private EntityManager entityManager;

    // CRUD básico usando JPA
    @Override
    public List<Instituicoes> findAll() {
        TypedQuery<Instituicoes> query = entityManager.createQuery("SELECT i FROM Instituicoes i ORDER BY i.Nome", Instituicoes.class);
        return query.getResultList();
    }

    @Override
    public Optional<Instituicoes> findById(Integer id) {
        Instituicoes instituicao = entityManager.find(Instituicoes.class, id);
        return Optional.ofNullable(instituicao);
    }

    @Override
    @Transactional
    public Instituicoes save(Instituicoes instituicao) {
        if (instituicao.getId() == null || instituicao.getId() == 0) {
            entityManager.persist(instituicao);
            return instituicao;
        } else {
            return entityManager.merge(instituicao);
        }
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        Instituicoes instituicao = entityManager.find(Instituicoes.class, id);
        if (instituicao != null) {
            entityManager.remove(instituicao);
        }
    }
    
    // Queries customizadas (necessário porque o campo é Flg_Inativo com underscore)
    @Override
    public List<Instituicoes> findActive() {
        TypedQuery<Instituicoes> query = entityManager.createQuery(
            "SELECT i FROM Instituicoes i WHERE i.Flg_Inativo = false ORDER BY i.Nome", Instituicoes.class);
        return query.getResultList();
    }
    
    // Query customizada simples (sem JOIN)
    @Override
    public List<Instituicoes> findByType(String tipoInstituicao) {
        TypedQuery<Instituicoes> query = entityManager.createQuery(
            "SELECT i FROM Instituicoes i WHERE i.Tipo = :tipoInstituicao AND i.Flg_Inativo = false ORDER BY i.Nome", 
            Instituicoes.class);
        query.setParameter("tipoInstituicao", tipoInstituicao);
        return query.getResultList();
    }
}
