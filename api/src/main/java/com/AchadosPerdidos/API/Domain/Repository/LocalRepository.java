package com.AchadosPerdidos.API.Domain.Repository;

import com.AchadosPerdidos.API.Domain.Entity.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository para gerenciar Locais dentro dos Campus
 * Ex: Biblioteca, Sala 101, Quadra, Refeitório, etc.
 * Usa JPA para CRUD básico
 */
@Repository
public interface LocalRepository extends JpaRepository<Local, Integer> {
    
    // CRUD básico já vem do JpaRepository: save, findById, findAll, deleteById
    
    // Queries customizadas (necessário porque o campo é Flg_Inativo com underscore)
    @Query("SELECT l FROM Local l WHERE l.Flg_Inativo = false")
    List<Local> findByFlgInativoFalse();
    
    @Query("SELECT l FROM Local l WHERE l.Id = :id AND l.Flg_Inativo = false")
    Optional<Local> findByIdAndFlgInativoFalse(@Param("id") Integer id);
    
    // Query customizada simples (sem JOIN)
    @Query("SELECT l FROM Local l WHERE l.Campus_id.Id = :campusId AND l.Flg_Inativo = false ORDER BY l.Nome")
    List<Local> findByCampus(@Param("campusId") Integer campusId);
    
    // Métodos customizados
    default List<Local> findActive() {
        return findByFlgInativoFalse();
    }
}

