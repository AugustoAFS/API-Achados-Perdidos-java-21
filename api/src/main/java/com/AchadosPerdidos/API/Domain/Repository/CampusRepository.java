package com.AchadosPerdidos.API.Domain.Repository;

import com.AchadosPerdidos.API.Domain.Entity.Campus;
import com.AchadosPerdidos.API.Domain.Repository.Interfaces.ICampusRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository para gerenciar operações de Campus
 * Usa JPA para CRUD básico e Queries apenas para operações complexas com JOINs
 */
@Repository
public interface CampusRepository extends JpaRepository<Campus, Integer>, ICampusRepository {
    
    // CRUD básico já vem do JpaRepository: save, findById, findAll, deleteById
    
    // Queries customizadas (necessário porque o campo é Flg_Inativo com underscore)
    @Query("SELECT c FROM Campus c WHERE c.Flg_Inativo = false")
    List<Campus> findByFlgInativoFalse();
    
    @Query("SELECT c FROM Campus c WHERE c.Id = :id AND c.Flg_Inativo = false")
    Optional<Campus> findByIdAndFlgInativoFalse(@Param("id") Integer id);
    
    // Query customizada simples (sem JOIN) - substitui findByInstitution da Query
    @Query("SELECT c FROM Campus c WHERE c.Instituicao_id.Id = :institutionId AND c.Flg_Inativo = false ORDER BY c.Nome")
    List<Campus> findByInstitution(@Param("institutionId") Integer institutionId);
    
    // Implementação padrão dos métodos da interface
    @Override
    default List<Campus> findActive() {
        return findByFlgInativoFalse();
    }
}
