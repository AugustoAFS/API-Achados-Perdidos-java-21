package com.AchadosPerdidos.API.Domain.Repository.Interfaces;

import com.AchadosPerdidos.API.Domain.Entity.Local;
import java.util.List;

/**
 * Interface do Repository para Locais
 * Define operações de persistência para entidade Local
 * Locais específicos dentro dos Campus (Ex: Biblioteca, Sala 101, Quadra)
 */
public interface ILocalRepository {
    List<Local> findAll();
    Local findById(Integer id);
    Local save(Local local);
    boolean deleteById(Integer id);
    List<Local> findActive();
    List<Local> findByCampus(Integer campusId);
}

