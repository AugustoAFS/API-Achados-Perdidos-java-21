package com.AchadosPerdidos.API.Infrastruture.DataBase;

import com.AchadosPerdidos.API.Domain.Entity.Instituicoes;
import com.AchadosPerdidos.API.Infrastruture.DataBase.Interfaces.IInstituicaoQueries;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Queries para operações de Instituicao
 * CRUD básico é feito via JPA no InstituicaoRepository
 */
@Repository
public class InstituicaoQueries implements IInstituicaoQueries {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Instituicoes> findActive() {
        TypedQuery<Instituicoes> query = entityManager.createQuery(
            "SELECT i FROM Instituicoes i WHERE i.Flg_Inativo = false ORDER BY i.Nome", Instituicoes.class);
        return query.getResultList();
    }

    @Override
    public List<Instituicoes> findByType(String tipoInstituicao) {
        TypedQuery<Instituicoes> query = entityManager.createQuery(
            "SELECT i FROM Instituicoes i WHERE i.Tipo = :tipoInstituicao AND i.Flg_Inativo = false ORDER BY i.Nome", 
            Instituicoes.class);
        query.setParameter("tipoInstituicao", tipoInstituicao);
        return query.getResultList();
    }
}

