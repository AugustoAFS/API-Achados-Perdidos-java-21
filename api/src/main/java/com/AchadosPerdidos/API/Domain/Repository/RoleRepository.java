package com.AchadosPerdidos.API.Domain.Repository;

import com.AchadosPerdidos.API.Domain.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository para gerenciar Roles (Papéis/Permissões)
 * Ex: ADMIN, USER, MODERATOR
 * Usa JPA para CRUD básico
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    
    // CRUD básico já vem do JpaRepository: save, findById, findAll, deleteById
    
    // Queries customizadas (necessário porque o campo é Flg_Inativo com underscore)
    @Query("SELECT r FROM Role r WHERE r.Flg_Inativo = false")
    List<Role> findByFlgInativoFalse();
    
    @Query("SELECT r FROM Role r WHERE r.Id = :id AND r.Flg_Inativo = false")
    Optional<Role> findByIdAndFlgInativoFalse(@Param("id") Integer id);
    
    @Query("SELECT r FROM Role r WHERE r.Nome = :nome AND r.Flg_Inativo = false")
    Optional<Role> findByNome(@Param("nome") String nome);
    
    // Métodos customizados
    default List<Role> findActive() {
        return findByFlgInativoFalse();
    }
}

