package com.AchadosPerdidos.API.Domain.Repository.Interfaces;

import com.AchadosPerdidos.API.Domain.Entity.Role;
import java.util.List;

/**
 * Interface do Repository para Roles (Papéis/Permissões)
 * Define operações de persistência para entidade Role
 * Define níveis de acesso: ADMIN, USER, MODERATOR
 */
public interface IRoleRepository {
    List<Role> findAll();
    Role findById(Integer id);
    Role findByNome(String nome);
    List<Role> findActive();
}

