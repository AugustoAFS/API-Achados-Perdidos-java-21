package com.AchadosPerdidos.API.Domain.Repository.Interfaces;

import com.AchadosPerdidos.API.Domain.Entity.FotoUsuario;
import java.util.List;

public interface IFotoUsuarioRepository {
    List<FotoUsuario> findAll();
    FotoUsuario findByUsuarioIdAndFotoId(Integer usuarioId, Integer fotoId);
    FotoUsuario save(FotoUsuario fotoUsuario);
    boolean deleteByUsuarioIdAndFotoId(Integer usuarioId, Integer fotoId);
    List<FotoUsuario> findActive();
    List<FotoUsuario> findByUsuarioId(Integer usuarioId);
    List<FotoUsuario> findByFotoId(Integer fotoId);
}

