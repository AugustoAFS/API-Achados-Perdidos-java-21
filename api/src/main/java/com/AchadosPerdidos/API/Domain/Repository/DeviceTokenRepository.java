package com.AchadosPerdidos.API.Domain.Repository;

import com.AchadosPerdidos.API.Domain.Entity.DeviceToken;
import com.AchadosPerdidos.API.Domain.Repository.Interfaces.IDeviceTokenRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Repository para gerenciar tokens de dispositivos (Push Notifications)
 * Usa JPA para CRUD básico
 */
@Repository
public class DeviceTokenRepository implements IDeviceTokenRepository {

    @PersistenceContext
    private EntityManager entityManager;

    // CRUD básico usando JPA
    @Override
    public List<DeviceToken> findAll() {
        TypedQuery<DeviceToken> query = entityManager.createQuery("SELECT dt FROM DeviceToken dt ORDER BY dt.Dta_Atualizacao DESC", DeviceToken.class);
        return query.getResultList();
    }

    @Override
    public Optional<DeviceToken> findById(Integer id) {
        DeviceToken deviceToken = entityManager.find(DeviceToken.class, id);
        return Optional.ofNullable(deviceToken);
    }

    @Override
    @Transactional
    public DeviceToken save(DeviceToken deviceToken) {
        if (deviceToken.getId() == null || deviceToken.getId() == 0) {
            entityManager.persist(deviceToken);
            return deviceToken;
        } else {
            return entityManager.merge(deviceToken);
        }
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        DeviceToken deviceToken = entityManager.find(DeviceToken.class, id);
        if (deviceToken != null) {
            entityManager.remove(deviceToken);
        }
    }
    
    // Queries customizadas (necessário porque o campo é Flg_Inativo com underscore)
    @Override
    public List<DeviceToken> findActive() {
        TypedQuery<DeviceToken> query = entityManager.createQuery(
            "SELECT dt FROM DeviceToken dt WHERE dt.Flg_Inativo = false ORDER BY dt.Dta_Atualizacao DESC", DeviceToken.class);
        return query.getResultList();
    }
    
    // Queries customizadas simples
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

