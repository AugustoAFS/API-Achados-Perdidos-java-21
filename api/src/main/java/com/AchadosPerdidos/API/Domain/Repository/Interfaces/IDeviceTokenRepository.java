package com.AchadosPerdidos.API.Domain.Repository.Interfaces;

import com.AchadosPerdidos.API.Domain.Entity.DeviceToken;
import java.util.List;

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

