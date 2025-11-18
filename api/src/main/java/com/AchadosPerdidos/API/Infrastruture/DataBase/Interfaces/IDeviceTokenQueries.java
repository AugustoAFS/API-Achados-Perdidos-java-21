package com.AchadosPerdidos.API.Infrastruture.DataBase.Interfaces;

import com.AchadosPerdidos.API.Domain.Entity.DeviceToken;
import java.util.List;

public interface IDeviceTokenQueries {
    List<DeviceToken> findAll();
    DeviceToken findById(Integer id);
    List<DeviceToken> findByUsuarioId(Integer usuarioId);
    DeviceToken findByUsuarioIdAndToken(Integer usuarioId, String token);
    List<DeviceToken> findActiveTokensByUsuarioId(Integer usuarioId);
    DeviceToken insert(DeviceToken deviceToken);
    DeviceToken update(DeviceToken deviceToken);
    boolean deleteById(Integer id);
    List<DeviceToken> findActive();
}

