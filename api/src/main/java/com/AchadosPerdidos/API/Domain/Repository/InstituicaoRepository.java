package com.AchadosPerdidos.API.Domain.Repository;

import com.AchadosPerdidos.API.Domain.Entity.Instituicoes;
import com.AchadosPerdidos.API.Domain.Repository.Interfaces.IInstituicaoRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository para gerenciar Instituições de Ensino
 * Usa JPA para CRUD básico
 */
@Repository
public interface InstituicaoRepository extends JpaRepository<Instituicoes, Integer>, IInstituicaoRepository {
    
    // CRUD básico já vem do JpaRepository: save, findById, findAll, deleteById
    
    // Queries customizadas (necessário porque o campo é Flg_Inativo com underscore)
    @Query("SELECT i FROM Instituicoes i WHERE i.Flg_Inativo = false")
    List<Instituicoes> findByFlgInativoFalse();
    
    @Query("SELECT i FROM Instituicoes i WHERE i.Id = :id AND i.Flg_Inativo = false")
    Optional<Instituicoes> findByIdAndFlgInativoFalse(@Param("id") Integer id);
    
    // Query customizada simples (sem JOIN)
    @Query("SELECT i FROM Instituicoes i WHERE i.Tipo = :tipoInstituicao AND i.Flg_Inativo = false ORDER BY i.Nome")
    List<Instituicoes> findByType(@Param("tipoInstituicao") String tipoInstituicao);
    
    // Implementação padrão dos métodos da interface
    @Override
    default List<Instituicoes> findActive() {
        return findByFlgInativoFalse();
    }
}
