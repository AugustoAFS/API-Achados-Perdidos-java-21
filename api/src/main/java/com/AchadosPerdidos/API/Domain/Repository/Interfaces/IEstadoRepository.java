package com.AchadosPerdidos.API.Domain.Repository.Interfaces;

import com.AchadosPerdidos.API.Domain.Entity.Estado;
import java.util.List;

/**
 * Interface do Repository para Estados (UF)
 * Define operações de persistência para entidade Estado
 * Base da hierarquia de localização: Estado → Cidade → Endereço
 */
public interface IEstadoRepository {
    List<Estado> findAll();
    Estado findById(Integer id);
    Estado findByUf(String uf);
    Estado save(Estado estado);
    boolean deleteById(Integer id);
    List<Estado> findActive();
}

