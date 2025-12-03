package com.AchadosPerdidos.API.Domain.Repository.Interfaces;

import com.AchadosPerdidos.API.Domain.Entity.UsuarioCampus;
import java.util.List;
import java.util.Optional;

/**
 * Interface do Repository para UsuarioCampus
 * Define operações de persistência para associação N:N
 * Gerencia vinculação de Usuários a Campus
 */
public interface IUsuarioCampusRepository {
    // Operações CRUD básicas
    List<UsuarioCampus> findAll();
    Optional<UsuarioCampus> findById(Integer id);
    UsuarioCampus save(UsuarioCampus usuarioCampus);
    void deleteById(Integer id);
    
    // Buscas específicas
    UsuarioCampus findByUsuarioIdAndCampusId(Integer usuarioId, Integer campusId);
    boolean deleteByUsuarioIdAndCampusId(Integer usuarioId, Integer campusId);
    List<UsuarioCampus> findActive();
    List<UsuarioCampus> findByUsuarioId(Integer usuarioId);
    List<UsuarioCampus> findByCampusId(Integer campusId);
}

