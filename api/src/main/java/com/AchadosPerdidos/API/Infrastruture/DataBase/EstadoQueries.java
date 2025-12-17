package com.AchadosPerdidos.API.Infrastruture.DataBase;

import com.AchadosPerdidos.API.Domain.Entity.Estado;
import com.AchadosPerdidos.API.Infrastruture.DataBase.Interfaces.IEstadoQueries;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Queries para operações de Estado
 * CRUD básico é feito via JPA no EstadoRepository
 */
@Repository
public class EstadoQueries implements IEstadoQueries {

    @PersistenceContext
    private EntityManager entityManager;

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

