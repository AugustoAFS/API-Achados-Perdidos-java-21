package com.AchadosPerdidos.API.Domain.Repository.Interfaces;

import com.AchadosPerdidos.API.Domain.Entity.Role;
import java.util.List;
import java.util.Optional;

/**
 * Interface do Repository para Roles (Papéis/Permissões)
 * Define operações de persistência para entidade Role
 * Define níveis de acesso: ADMIN, USER, MODERATOR
 */
public interface IRoleRepository {
    // Operações CRUD básicas
    List<Role> findAll();
    Optional<Role> findById(Integer id);
    Role save(Role role);
    void deleteById(Integer id);
    
    // Buscas específicas
    Optional<Role> findByNome(String nome);
    List<Role> findActive();
}

