package com.AchadosPerdidos.API.Infrastruture.DataBase.Interfaces;

import com.AchadosPerdidos.API.Domain.Entity.FotoUsuario;
import java.util.List;

public interface IFotoUsuarioQueries {
    List<FotoUsuario> findActive();
    FotoUsuario findByUsuarioIdAndFotoId(Integer usuarioId, Integer fotoId);
    List<FotoUsuario> findByUsuarioId(Integer usuarioId);
    List<FotoUsuario> findByFotoId(Integer fotoId);
}

