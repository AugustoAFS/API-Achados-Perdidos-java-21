package com.AchadosPerdidos.API.Infrastruture.DataBase;

import com.AchadosPerdidos.API.Domain.Entity.Role;
import com.AchadosPerdidos.API.Infrastruture.DataBase.Interfaces.IRoleQueries;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Queries para operações de Role
 * CRUD básico é feito via JPA no RoleRepository
 */
@Repository
public class RoleQueries implements IRoleQueries {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Role> findActive() {
        TypedQuery<Role> query = entityManager.createQuery(
            "SELECT r FROM Role r WHERE r.Flg_Inativo = false ORDER BY r.Nome", Role.class);
        return query.getResultList();
    }

    @Override
    public Optional<Role> findByNome(String nome) {
        TypedQuery<Role> query = entityManager.createQuery(
            "SELECT r FROM Role r WHERE r.Nome = :nome AND r.Flg_Inativo = false", Role.class);
        query.setParameter("nome", nome);
        List<Role> resultados = query.getResultList();
        return resultados.isEmpty() ? Optional.empty() : Optional.of(resultados.get(0));
    }
}

