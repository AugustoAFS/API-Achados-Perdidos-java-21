package com.AchadosPerdidos.API.Domain.Repository.Interfaces;

import com.AchadosPerdidos.API.Domain.Entity.UsuarioCampus;
import java.util.List;

public interface IUsuarioCampusRepository {
    List<UsuarioCampus> findAll();
    UsuarioCampus findByUsuarioIdAndCampusId(Integer usuarioId, Integer campusId);
    UsuarioCampus save(UsuarioCampus usuarioCampus);
    boolean deleteByUsuarioIdAndCampusId(Integer usuarioId, Integer campusId);
    List<UsuarioCampus> findActive();
    List<UsuarioCampus> findByUsuarioId(Integer usuarioId);
    List<UsuarioCampus> findByCampusId(Integer campusId);
}

