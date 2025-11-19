package com.AchadosPerdidos.API.Domain.Repository;

import com.AchadosPerdidos.API.Domain.Entity.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository para gerenciar Cidades
 * Usa JPA para CRUD básico
 */
@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Integer> {
    
    // CRUD básico já vem do JpaRepository: save, findById, findAll, deleteById
    
    // Queries customizadas (necessário porque o campo é Flg_Inativo com underscore)
    @Query("SELECT c FROM Cidade c WHERE c.Flg_Inativo = false")
    List<Cidade> findByFlgInativoFalse();
    
    @Query("SELECT c FROM Cidade c WHERE c.Id = :id AND c.Flg_Inativo = false")
    Optional<Cidade> findByIdAndFlgInativoFalse(@Param("id") Integer id);
    
    // Query customizada simples (sem JOIN)
    @Query("SELECT c FROM Cidade c WHERE c.Estado_id.Id = :estadoId AND c.Flg_Inativo = false ORDER BY c.Nome")
    List<Cidade> findByEstado(@Param("estadoId") Integer estadoId);
    
    // Métodos customizados compatíveis com ICidadeRepository
    default List<Cidade> findActive() {
        return findByFlgInativoFalse();
    }
}

