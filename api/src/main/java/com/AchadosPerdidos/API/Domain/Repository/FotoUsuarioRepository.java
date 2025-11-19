package com.AchadosPerdidos.API.Domain.Repository;

import com.AchadosPerdidos.API.Domain.Entity.FotoUsuario;
import com.AchadosPerdidos.API.Domain.Repository.Interfaces.IFotoUsuarioRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository para gerenciar associação entre Usuários e suas Fotos de Perfil
 * Usa JPA para CRUD básico
 */
@Repository
public interface FotoUsuarioRepository extends JpaRepository<FotoUsuario, Integer>, IFotoUsuarioRepository {
    
    // CRUD básico já vem do JpaRepository: save, findById, findAll, deleteById
    
    // Queries customizadas (necessário porque o campo é Flg_Inativo com underscore)
    @Query("SELECT fu FROM FotoUsuario fu WHERE fu.Flg_Inativo = false")
    List<FotoUsuario> findByFlgInativoFalse();
    
    @Query("SELECT fu FROM FotoUsuario fu WHERE fu.Id = :id AND fu.Flg_Inativo = false")
    Optional<FotoUsuario> findByIdAndFlgInativoFalse(@Param("id") Integer id);
    
    // Queries customizadas simples
    @Query("SELECT fu FROM FotoUsuario fu WHERE fu.Usuario_id.Id = :usuarioId AND fu.Foto_id.Id = :fotoId")
    Optional<FotoUsuario> findByUsuarioIdAndFotoIdOptional(@Param("usuarioId") Integer usuarioId, @Param("fotoId") Integer fotoId);
    
    @Query("SELECT fu FROM FotoUsuario fu WHERE fu.Usuario_id.Id = :usuarioId AND fu.Flg_Inativo = false")
    List<FotoUsuario> findByUsuarioId(@Param("usuarioId") Integer usuarioId);
    
    @Query("SELECT fu FROM FotoUsuario fu WHERE fu.Foto_id.Id = :fotoId AND fu.Flg_Inativo = false")
    List<FotoUsuario> findByFotoId(@Param("fotoId") Integer fotoId);
    
    // Implementação padrão dos métodos da interface
    @Override
    default List<FotoUsuario> findActive() {
        return findByFlgInativoFalse();
    }
    
    @Override
    default boolean deleteByUsuarioIdAndFotoId(Integer usuarioId, Integer fotoId) {
        Optional<FotoUsuario> fotoUsuario = findByUsuarioIdAndFotoIdOptional(usuarioId, fotoId);
        if (fotoUsuario.isPresent()) {
            delete(fotoUsuario.get());
            return true;
        }
        return false;
    }
    
    // Conversão para compatibilidade com interface
    default FotoUsuario findByUsuarioIdAndFotoId(Integer usuarioId, Integer fotoId) {
        return findByUsuarioIdAndFotoIdOptional(usuarioId, fotoId).orElse(null);
    }
}

