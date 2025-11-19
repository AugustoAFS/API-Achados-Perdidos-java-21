package com.AchadosPerdidos.API.Domain.Repository;

import com.AchadosPerdidos.API.Domain.Entity.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository para gerenciar Estados (UF)
 * Usa JPA para CRUD básico
 */
@Repository
public interface EstadoRepository extends JpaRepository<Estado, Integer> {
    
    // CRUD básico já vem do JpaRepository: save, findById, findAll, deleteById
    
    // Queries customizadas (necessário porque o campo é Flg_Inativo com underscore)
    @Query("SELECT e FROM Estado e WHERE e.Flg_Inativo = false")
    List<Estado> findByFlgInativoFalse();
    
    @Query("SELECT e FROM Estado e WHERE e.Id = :id AND e.Flg_Inativo = false")
    Optional<Estado> findByIdAndFlgInativoFalse(@Param("id") Integer id);
    
    Optional<Estado> findByUF(String uf);
    
    // Métodos customizados
    default List<Estado> findActive() {
        return findByFlgInativoFalse();
    }
    
    default Estado findByUf(String uf) {
        return findByUF(uf).orElse(null);
    }
}

