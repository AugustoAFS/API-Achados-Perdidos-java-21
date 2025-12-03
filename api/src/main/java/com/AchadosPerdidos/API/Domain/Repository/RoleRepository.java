package com.AchadosPerdidos.API.Domain.Repository;

import com.AchadosPerdidos.API.Domain.Entity.Role;
import com.AchadosPerdidos.API.Domain.Repository.Interfaces.IRoleRepository;
import com.AchadosPerdidos.API.Infrastruture.DataBase.RoleQueries;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Repository para gerenciar Roles (Papéis/Permissões)
 * Ex: ADMIN, USER, MODERATOR
 * Usa JPA para CRUD básico
 */
@Repository
public class RoleRepository implements IRoleRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private RoleQueries roleQueries;

    // CRUD básico usando JPA
    @Override
    public List<Role> findAll() {
        return entityManager.createQuery("SELECT r FROM Role r ORDER BY r.Nome", Role.class).getResultList();
    }

    @Override
    public Optional<Role> findById(Integer id) {
        Role role = entityManager.find(Role.class, id);
        return Optional.ofNullable(role);
    }

    @Override
    @Transactional
    public Role save(Role role) {
        if (role.getId() == null || role.getId() == 0) {
            entityManager.persist(role);
            return role;
        } else {
            return entityManager.merge(role);
        }
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        Role role = entityManager.find(Role.class, id);
        if (role != null) {
            entityManager.remove(role);
        }
    }
    
    // Queries customizadas - delegadas para RoleQueries
    @Override
    public List<Role> findActive() {
        return roleQueries.findActive();
    }
    
    @Override
    public Optional<Role> findByNome(String nome) {
        return roleQueries.findByNome(nome);
    }
}

