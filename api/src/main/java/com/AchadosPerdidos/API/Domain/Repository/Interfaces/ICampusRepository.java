package com.AchadosPerdidos.API.Domain.Repository.Interfaces;

import com.AchadosPerdidos.API.Domain.Entity.Campus;
import java.util.List;

/**
 * Interface do Repository para Campus
 * Define operações de persistência para entidade Campus
 * Campus são unidades de Instituições (Ex: Campus Paranaguá do IFPR)
 */
public interface ICampusRepository {
    List<Campus> findActive();
}
