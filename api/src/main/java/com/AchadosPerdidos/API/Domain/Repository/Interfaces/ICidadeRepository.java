package com.AchadosPerdidos.API.Domain.Repository.Interfaces;

import com.AchadosPerdidos.API.Domain.Entity.Cidade;
import java.util.List;
import java.util.Optional;

/**
 * Interface do Repository para Cidades
 * Define operações de persistência para entidade Cidade
 * Vinculadas a Estados (UF)
 */
public interface ICidadeRepository {
    // Operações CRUD básicas
    List<Cidade> findAll();
    Optional<Cidade> findById(Integer id);
    Cidade save(Cidade cidade);
    void deleteById(Integer id);
    
    // Buscas específicas
    List<Cidade> findActive();
    List<Cidade> findByEstado(Integer estadoId);
}

