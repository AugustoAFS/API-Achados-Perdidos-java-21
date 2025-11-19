package com.AchadosPerdidos.API.Domain.Repository.Interfaces;

import com.AchadosPerdidos.API.Domain.Entity.FotoUsuario;
import java.util.List;

/**
 * Interface do Repository para FotoUsuario
 * Gerencia associação N:N entre Usuários e suas Fotos de Perfil
 */
public interface IFotoUsuarioRepository {
    FotoUsuario findByUsuarioIdAndFotoId(Integer usuarioId, Integer fotoId);
    boolean deleteByUsuarioIdAndFotoId(Integer usuarioId, Integer fotoId);
    List<FotoUsuario> findActive();
    List<FotoUsuario> findByUsuarioId(Integer usuarioId);
    List<FotoUsuario> findByFotoId(Integer fotoId);
}

