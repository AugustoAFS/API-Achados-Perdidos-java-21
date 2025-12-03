package com.AchadosPerdidos.API.Domain.Repository.Interfaces;

import com.AchadosPerdidos.API.Domain.Entity.Instituicoes;
import java.util.List;
import java.util.Optional;

/**
 * Interface do Repository para Instituições de Ensino
 * Define operações de persistência para entidade Instituicoes
 * Ex: IFPR, UTFPR, UFPR, etc.
 */
public interface IInstituicaoRepository {
    // Operações CRUD básicas
    List<Instituicoes> findAll();
    Optional<Instituicoes> findById(Integer id);
    Instituicoes save(Instituicoes instituicao);
    void deleteById(Integer id);
    
    // Buscas específicas
    List<Instituicoes> findActive();
    List<Instituicoes> findByType(String tipoInstituicao);
}
