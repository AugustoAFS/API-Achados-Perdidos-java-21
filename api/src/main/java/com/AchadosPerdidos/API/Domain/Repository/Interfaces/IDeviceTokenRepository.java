package com.AchadosPerdidos.API.Domain.Repository.Interfaces;

import com.AchadosPerdidos.API.Domain.Entity.DeviceToken;
import java.util.List;

/**
 * Interface do Repository para DeviceToken
 * Define operações de persistência para tokens de dispositivos
 * Gerencia tokens FCM para Push Notifications (OneSignal)
 */
public interface IDeviceTokenRepository {
    List<DeviceToken> findAll();
    DeviceToken findById(Integer id);
    List<DeviceToken> findByUsuarioId(Integer usuarioId);
    DeviceToken findByUsuarioIdAndToken(Integer usuarioId, String token);
    List<DeviceToken> findActiveTokensByUsuarioId(Integer usuarioId);
    DeviceToken save(DeviceToken deviceToken);
    boolean deleteById(Integer id);
    List<DeviceToken> findActive();
}

