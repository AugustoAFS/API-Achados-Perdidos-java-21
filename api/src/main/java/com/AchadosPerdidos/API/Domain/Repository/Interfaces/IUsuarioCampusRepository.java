package com.AchadosPerdidos.API.Domain.Repository.Interfaces;

import com.AchadosPerdidos.API.Domain.Entity.UsuarioCampus;
import java.util.List;

/**
 * Interface do Repository para UsuarioCampus
 * Define operações de persistência para associação N:N
 * Gerencia vinculação de Usuários a Campus
 */
public interface IUsuarioCampusRepository {
    UsuarioCampus findByUsuarioIdAndCampusId(Integer usuarioId, Integer campusId);
    boolean deleteByUsuarioIdAndCampusId(Integer usuarioId, Integer campusId);
    List<UsuarioCampus> findActive();
    List<UsuarioCampus> findByUsuarioId(Integer usuarioId);
    List<UsuarioCampus> findByCampusId(Integer campusId);
}

