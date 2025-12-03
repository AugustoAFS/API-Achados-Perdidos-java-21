package com.AchadosPerdidos.API.Infrastruture.DataBase;

import com.AchadosPerdidos.API.Domain.Entity.Endereco;
import com.AchadosPerdidos.API.Infrastruture.DataBase.Interfaces.IEnderecoQueries;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Queries para operações de Endereco
 * CRUD básico é feito via JPA no EnderecoRepository
 */
@Repository
public class EnderecoQueries implements IEnderecoQueries {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Endereco> findActive() {
        TypedQuery<Endereco> query = entityManager.createQuery(
            "SELECT e FROM Endereco e WHERE e.Flg_Inativo = false ORDER BY e.Logradouro", Endereco.class);
        return query.getResultList();
    }

    @Override
    public List<Endereco> findByCidade(Integer cidadeId) {
        TypedQuery<Endereco> query = entityManager.createQuery(
            "SELECT e FROM Endereco e WHERE e.Cidade_id.Id = :cidadeId AND e.Flg_Inativo = false ORDER BY e.Logradouro", 
            Endereco.class);
        query.setParameter("cidadeId", cidadeId);
        return query.getResultList();
    }
}

