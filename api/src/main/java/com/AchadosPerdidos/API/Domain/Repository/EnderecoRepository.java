package com.AchadosPerdidos.API.Domain.Repository;

import com.AchadosPerdidos.API.Domain.Entity.Endereco;
import com.AchadosPerdidos.API.Domain.Repository.Interfaces.IEnderecoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Repository para gerenciar Endereços
 * Usa JPA para CRUD básico
 */
@Repository
public class EnderecoRepository implements IEnderecoRepository {

    @PersistenceContext
    private EntityManager entityManager;

    // CRUD básico usando JPA
    @Override
    public List<Endereco> findAll() {
        TypedQuery<Endereco> query = entityManager.createQuery("SELECT e FROM Endereco e ORDER BY e.Logradouro", Endereco.class);
        return query.getResultList();
    }

    @Override
    public Optional<Endereco> findById(Integer id) {
        Endereco endereco = entityManager.find(Endereco.class, id);
        return Optional.ofNullable(endereco);
    }

    @Override
    @Transactional
    public Endereco save(Endereco endereco) {
        if (endereco.getId() == null || endereco.getId() == 0) {
            entityManager.persist(endereco);
            return endereco;
        } else {
            return entityManager.merge(endereco);
        }
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        Endereco endereco = entityManager.find(Endereco.class, id);
        if (endereco != null) {
            entityManager.remove(endereco);
        }
    }
    
    // Queries customizadas (necessário porque o campo é Flg_Inativo com underscore)
    @Override
    public List<Endereco> findActive() {
        TypedQuery<Endereco> query = entityManager.createQuery(
            "SELECT e FROM Endereco e WHERE e.Flg_Inativo = false ORDER BY e.Logradouro", Endereco.class);
        return query.getResultList();
    }
    
    // Query customizada simples (sem JOIN)
    @Override
    public List<Endereco> findByCidade(Integer cidadeId) {
        TypedQuery<Endereco> query = entityManager.createQuery(
            "SELECT e FROM Endereco e WHERE e.Cidade_id.Id = :cidadeId AND e.Flg_Inativo = false ORDER BY e.Logradouro", 
            Endereco.class);
        query.setParameter("cidadeId", cidadeId);
        return query.getResultList();
    }
}

