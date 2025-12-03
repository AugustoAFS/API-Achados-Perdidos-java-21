package com.AchadosPerdidos.API.Infrastruture.DataBase.Interfaces;

import com.AchadosPerdidos.API.Domain.Entity.DeviceToken;
import java.util.List;

public interface IDeviceTokenQueries {
    List<DeviceToken> findActive();
    List<DeviceToken> findByUsuarioId(Integer usuarioId);
    DeviceToken findByUsuarioIdAndToken(Integer usuarioId, String token);
    List<DeviceToken> findActiveTokensByUsuarioId(Integer usuarioId);
}

