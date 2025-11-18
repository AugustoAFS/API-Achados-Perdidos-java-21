package com.AchadosPerdidos.API.Domain.Repository;

import com.AchadosPerdidos.API.Domain.Entity.DeviceToken;
import com.AchadosPerdidos.API.Domain.Repository.Interfaces.IDeviceTokenRepository;
import com.AchadosPerdidos.API.Infrastruture.DataBase.DeviceTokenQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DeviceTokenRepository implements IDeviceTokenRepository {

    @Autowired
    private DeviceTokenQueries deviceTokenQueries;

    @Override
    public List<DeviceToken> findAll() {
        return deviceTokenQueries.findAll();
    }

    @Override
    public DeviceToken findById(Integer id) {
        return deviceTokenQueries.findById(id);
    }

    @Override
    public List<DeviceToken> findByUsuarioId(Integer usuarioId) {
        return deviceTokenQueries.findByUsuarioId(usuarioId);
    }

    @Override
    public DeviceToken findByUsuarioIdAndToken(Integer usuarioId, String token) {
        return deviceTokenQueries.findByUsuarioIdAndToken(usuarioId, token);
    }

    @Override
    public List<DeviceToken> findActiveTokensByUsuarioId(Integer usuarioId) {
        return deviceTokenQueries.findActiveTokensByUsuarioId(usuarioId);
    }

    @Override
    public DeviceToken save(DeviceToken deviceToken) {
        DeviceToken existing = null;
        if (deviceToken.getId() != null) {
            existing = deviceTokenQueries.findById(deviceToken.getId());
        } else if (deviceToken.getUsuario_id() != null && deviceToken.getToken() != null) {
            existing = deviceTokenQueries.findByUsuarioIdAndToken(deviceToken.getUsuario_id(), deviceToken.getToken());
        }
        
        if (existing == null) {
            return deviceTokenQueries.insert(deviceToken);
        } else {
            // Atualiza o token existente
            deviceToken.setId(existing.getId());
            return deviceTokenQueries.update(deviceToken);
        }
    }

    @Override
    public boolean deleteById(Integer id) {
        return deviceTokenQueries.deleteById(id);
    }

    @Override
    public List<DeviceToken> findActive() {
        return deviceTokenQueries.findActive();
    }
}

