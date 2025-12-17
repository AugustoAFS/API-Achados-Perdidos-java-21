package com.AchadosPerdidos.API.Infrastruture.DataBase.Interfaces;

import com.AchadosPerdidos.API.Domain.Entity.UsuarioCampus;
import java.util.List;

public interface IUsuarioCampusQueries {
    List<UsuarioCampus> findActive();
    UsuarioCampus findByUsuarioIdAndCampusId(Integer usuarioId, Integer campusId);
    List<UsuarioCampus> findByUsuarioId(Integer usuarioId);
    List<UsuarioCampus> findByCampusId(Integer campusId);
}

