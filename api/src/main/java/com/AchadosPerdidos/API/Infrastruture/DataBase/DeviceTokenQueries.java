package com.AchadosPerdidos.API.Infrastruture.DataBase;

import com.AchadosPerdidos.API.Domain.Entity.DeviceToken;
import com.AchadosPerdidos.API.Infrastruture.DataBase.Interfaces.IDeviceTokenQueries;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Queries para operações de DeviceToken
 * CRUD básico é feito via JPA no DeviceTokenRepository
 */
@Repository
public class DeviceTokenQueries implements IDeviceTokenQueries {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<DeviceToken> findActive() {
        TypedQuery<DeviceToken> query = entityManager.createQuery(
            "SELECT dt FROM DeviceToken dt WHERE dt.Flg_Inativo = false ORDER BY dt.Dta_Atualizacao DESC", DeviceToken.class);
        return query.getResultList();
    }

    @Override
    public List<DeviceToken> findByUsuarioId(Integer usuarioId) {
        TypedQuery<DeviceToken> query = entityManager.createQuery(
            "SELECT dt FROM DeviceToken dt WHERE dt.Usuario_id.Id = :usuarioId AND dt.Flg_Inativo = false", 
            DeviceToken.class);
        query.setParameter("usuarioId", usuarioId);
        return query.getResultList();
    }

    @Override
    public DeviceToken findByUsuarioIdAndToken(Integer usuarioId, String token) {
        TypedQuery<DeviceToken> query = entityManager.createQuery(
            "SELECT dt FROM DeviceToken dt WHERE dt.Usuario_id.Id = :usuarioId AND dt.Token = :token", 
            DeviceToken.class);
        query.setParameter("usuarioId", usuarioId);
        query.setParameter("token", token);
        List<DeviceToken> resultados = query.getResultList();
        return resultados.isEmpty() ? null : resultados.get(0);
    }

    @Override
    public List<DeviceToken> findActiveTokensByUsuarioId(Integer usuarioId) {
        TypedQuery<DeviceToken> query = entityManager.createQuery(
            "SELECT dt FROM DeviceToken dt WHERE dt.Usuario_id.Id = :usuarioId AND dt.Flg_Inativo = false ORDER BY dt.Dta_Atualizacao DESC", 
            DeviceToken.class);
        query.setParameter("usuarioId", usuarioId);
        return query.getResultList();
    }
}

