package com.AchadosPerdidos.API.Domain.Repository;

import com.AchadosPerdidos.API.Domain.Entity.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository para gerenciar Endereços
 * Usa JPA para CRUD básico
 */
@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {
    
    // CRUD básico já vem do JpaRepository: save, findById, findAll, deleteById
    
    // Queries customizadas (necessário porque o campo é Flg_Inativo com underscore)
    @Query("SELECT e FROM Endereco e WHERE e.Flg_Inativo = false")
    List<Endereco> findByFlgInativoFalse();
    
    @Query("SELECT e FROM Endereco e WHERE e.Id = :id AND e.Flg_Inativo = false")
    Optional<Endereco> findByIdAndFlgInativoFalse(@Param("id") Integer id);
    
    // Query customizada simples (sem JOIN)
    @Query("SELECT e FROM Endereco e WHERE e.Cidade_id.Id = :cidadeId AND e.Flg_Inativo = false ORDER BY e.Logradouro")
    List<Endereco> findByCidade(@Param("cidadeId") Integer cidadeId);
    
    // Métodos customizados
    default List<Endereco> findActive() {
        return findByFlgInativoFalse();
    }
}

