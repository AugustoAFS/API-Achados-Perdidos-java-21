package com.AchadosPerdidos.API.Domain.Repository;

import com.AchadosPerdidos.API.Domain.Entity.Campus;
import com.AchadosPerdidos.API.Domain.Repository.Interfaces.ICampusRepository;
import com.AchadosPerdidos.API.Infrastruture.DataBase.CampusQueries;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Repository para gerenciar operações de Campus
 * Usa JPA para CRUD básico e Queries apenas para operações complexas
 */
@Repository
public class CampusRepository implements ICampusRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private CampusQueries campusQueries;

    // CRUD básico usando JPA
    @Override
    public List<Campus> findAll() {
        return entityManager.createQuery("SELECT c FROM Campus c ORDER BY c.Nome", Campus.class).getResultList();
    }

    @Override
    public Optional<Campus> findById(Integer id) {
        Campus campus = entityManager.find(Campus.class, id);
        return Optional.ofNullable(campus);
    }

    @Override
    @Transactional
    public Campus save(Campus campus) {
        if (campus.getId() == null || campus.getId() == 0) {
            entityManager.persist(campus);
            return campus;
        } else {
            return entityManager.merge(campus);
        }
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        Campus campus = entityManager.find(Campus.class, id);
        if (campus != null) {
            entityManager.remove(campus);
        }
    }
    
    // Queries customizadas - delegadas para CampusQueries
    @Override
    public List<Campus> findActive() {
        return campusQueries.findActive();
    }
    
    @Override
    public Optional<Campus> findByIdAndFlgInativoFalse(Integer id) {
        return campusQueries.findByIdAndFlgInativoFalse(id);
    }
    
    @Override
    public List<Campus> findByInstitution(Integer institutionId) {
        return campusQueries.findByInstitution(institutionId);
    }
}
