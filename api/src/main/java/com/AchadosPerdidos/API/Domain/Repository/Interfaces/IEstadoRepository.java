package com.AchadosPerdidos.API.Domain.Repository.Interfaces;

import com.AchadosPerdidos.API.Domain.Entity.Estado;
import java.util.List;
import java.util.Optional;

/**
 * Interface do Repository para Estados (UF)
 * Define operações de persistência para entidade Estado
 * Base da hierarquia de localização: Estado → Cidade → Endereço
 */
public interface IEstadoRepository {
    // Operações CRUD básicas
    List<Estado> findAll();
    Optional<Estado> findById(Integer id);
    Estado save(Estado estado);
    void deleteById(Integer id);
    
    // Buscas específicas
    Estado findByUf(String uf);
    List<Estado> findActive();
}

