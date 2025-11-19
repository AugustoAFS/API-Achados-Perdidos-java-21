package com.AchadosPerdidos.API.Domain.Repository;

import com.AchadosPerdidos.API.Domain.Entity.DeviceToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository para gerenciar tokens de dispositivos (Push Notifications)
 * Usa JPA para CRUD básico
 */
@Repository
public interface DeviceTokenRepository extends JpaRepository<DeviceToken, Integer> {
    
    // CRUD básico já vem do JpaRepository: save, findById, findAll, deleteById
    
    // Queries customizadas (necessário porque o campo é Flg_Inativo com underscore)
    @Query("SELECT dt FROM DeviceToken dt WHERE dt.Flg_Inativo = false")
    List<DeviceToken> findByFlgInativoFalse();
    
    @Query("SELECT dt FROM DeviceToken dt WHERE dt.Id = :id AND dt.Flg_Inativo = false")
    Optional<DeviceToken> findByIdAndFlgInativoFalse(@Param("id") Integer id);
    
    // Queries customizadas simples
    @Query("SELECT dt FROM DeviceToken dt WHERE dt.Usuario_id.Id = :usuarioId AND dt.Flg_Inativo = false")
    List<DeviceToken> findByUsuarioId(@Param("usuarioId") Integer usuarioId);
    
    @Query("SELECT dt FROM DeviceToken dt WHERE dt.Usuario_id.Id = :usuarioId AND dt.Token = :token")
    Optional<DeviceToken> findByUsuarioIdAndTokenOptional(@Param("usuarioId") Integer usuarioId, @Param("token") String token);
    
    @Query("SELECT dt FROM DeviceToken dt WHERE dt.Usuario_id.Id = :usuarioId AND dt.Flg_Inativo = false ORDER BY dt.Dta_Atualizacao DESC")
    List<DeviceToken> findActiveTokensByUsuarioId(@Param("usuarioId") Integer usuarioId);
    
    // Métodos customizados
    default List<DeviceToken> findActive() {
        return findByFlgInativoFalse();
    }
    
    // Conversão para compatibilidade com interface
    default DeviceToken findByUsuarioIdAndToken(Integer usuarioId, String token) {
        return findByUsuarioIdAndTokenOptional(usuarioId, token).orElse(null);
    }
}

