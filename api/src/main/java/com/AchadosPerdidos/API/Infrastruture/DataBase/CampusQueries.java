package com.AchadosPerdidos.API.Infrastruture.DataBase;

import com.AchadosPerdidos.API.Domain.Entity.Campus;
import com.AchadosPerdidos.API.Infrastruture.DataBase.Interfaces.ICampusQueries;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Queries para operações de Campus
 * CRUD básico é feito via JPA no CampusRepository
 */
@Repository
public class CampusQueries implements ICampusQueries {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Campus> findActive() {
        TypedQuery<Campus> query = entityManager.createQuery(
            "SELECT c FROM Campus c WHERE c.Flg_Inativo = false ORDER BY c.Nome", Campus.class);
        return query.getResultList();
    }

    @Override
    public Optional<Campus> findByIdAndFlgInativoFalse(Integer id) {
        TypedQuery<Campus> query = entityManager.createQuery(
            "SELECT c FROM Campus c WHERE c.Id = :id AND c.Flg_Inativo = false", Campus.class);
        query.setParameter("id", id);
        List<Campus> resultados = query.getResultList();
        return resultados.isEmpty() ? Optional.empty() : Optional.of(resultados.get(0));
    }

    @Override
    public List<Campus> findByInstitution(Integer institutionId) {
        TypedQuery<Campus> query = entityManager.createQuery(
            "SELECT c FROM Campus c WHERE c.Instituicao_id.Id = :institutionId AND c.Flg_Inativo = false ORDER BY c.Nome", 
            Campus.class);
        query.setParameter("institutionId", institutionId);
        return query.getResultList();
    }
}

