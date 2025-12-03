package com.AchadosPerdidos.API.Infrastruture.DataBase;

import com.AchadosPerdidos.API.Domain.Entity.Cidade;
import com.AchadosPerdidos.API.Infrastruture.DataBase.Interfaces.ICidadeQueries;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Queries para operações de Cidade
 * CRUD básico é feito via JPA no CidadeRepository
 */
@Repository
public class CidadeQueries implements ICidadeQueries {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Cidade> findActive() {
        TypedQuery<Cidade> query = entityManager.createQuery(
            "SELECT c FROM Cidade c WHERE c.Flg_Inativo = false ORDER BY c.Nome", Cidade.class);
        return query.getResultList();
    }

    @Override
    public List<Cidade> findByEstado(Integer estadoId) {
        TypedQuery<Cidade> query = entityManager.createQuery(
            "SELECT c FROM Cidade c WHERE c.Estado_id.Id = :estadoId AND c.Flg_Inativo = false ORDER BY c.Nome", 
            Cidade.class);
        query.setParameter("estadoId", estadoId);
        return query.getResultList();
    }
}

