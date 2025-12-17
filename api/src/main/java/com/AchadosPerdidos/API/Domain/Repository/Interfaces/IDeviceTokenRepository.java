package com.AchadosPerdidos.API.Domain.Repository.Interfaces;

import com.AchadosPerdidos.API.Domain.Entity.DeviceToken;
import java.util.List;
import java.util.Optional;

/**
 * Interface do Repository para DeviceToken
 * Define operações de persistência para tokens de dispositivos
 * Gerencia tokens FCM para Push Notifications (OneSignal)
 */
public interface IDeviceTokenRepository {
    // Operações CRUD básicas
    List<DeviceToken> findAll();
    Optional<DeviceToken> findById(Integer id);
    DeviceToken save(DeviceToken deviceToken);
    void deleteById(Integer id);
    
    // Buscas específicas
    List<DeviceToken> findByUsuarioId(Integer usuarioId);
    DeviceToken findByUsuarioIdAndToken(Integer usuarioId, String token);
    List<DeviceToken> findActiveTokensByUsuarioId(Integer usuarioId);
    List<DeviceToken> findActive();
}

