package com.AchadosPerdidos.API.Domain.Repository.Interfaces;

import com.AchadosPerdidos.API.Domain.Entity.FotoUsuario;
import java.util.List;
import java.util.Optional;

/**
 * Interface do Repository para FotoUsuario
 * Gerencia associação N:N entre Usuários e suas Fotos de Perfil
 */
public interface IFotoUsuarioRepository {
    // Operações CRUD básicas
    List<FotoUsuario> findAll();
    Optional<FotoUsuario> findById(Integer id);
    FotoUsuario save(FotoUsuario fotoUsuario);
    void deleteById(Integer id);
    
    // Buscas específicas
    FotoUsuario findByUsuarioIdAndFotoId(Integer usuarioId, Integer fotoId);
    boolean deleteByUsuarioIdAndFotoId(Integer usuarioId, Integer fotoId);
    List<FotoUsuario> findActive();
    List<FotoUsuario> findByUsuarioId(Integer usuarioId);
    List<FotoUsuario> findByFotoId(Integer fotoId);
}

