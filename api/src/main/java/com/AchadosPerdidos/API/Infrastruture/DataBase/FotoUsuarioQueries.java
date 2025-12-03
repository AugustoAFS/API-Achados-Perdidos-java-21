package com.AchadosPerdidos.API.Infrastruture.DataBase;

import com.AchadosPerdidos.API.Domain.Entity.FotoUsuario;
import com.AchadosPerdidos.API.Infrastruture.DataBase.Interfaces.IFotoUsuarioQueries;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Queries para operações de FotoUsuario
 * CRUD básico é feito via JPA no FotoUsuarioRepository
 */
@Repository
public class FotoUsuarioQueries implements IFotoUsuarioQueries {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<FotoUsuario> findActive() {
        TypedQuery<FotoUsuario> query = entityManager.createQuery(
            "SELECT fu FROM FotoUsuario fu WHERE fu.Flg_Inativo = false", FotoUsuario.class);
        return query.getResultList();
    }

    @Override
    public FotoUsuario findByUsuarioIdAndFotoId(Integer usuarioId, Integer fotoId) {
        TypedQuery<FotoUsuario> query = entityManager.createQuery(
            "SELECT fu FROM FotoUsuario fu WHERE fu.Usuario_id.Id = :usuarioId AND fu.Foto_id.Id = :fotoId", 
            FotoUsuario.class);
        query.setParameter("usuarioId", usuarioId);
        query.setParameter("fotoId", fotoId);
        List<FotoUsuario> resultados = query.getResultList();
        return resultados.isEmpty() ? null : resultados.get(0);
    }

    @Override
    public List<FotoUsuario> findByUsuarioId(Integer usuarioId) {
        TypedQuery<FotoUsuario> query = entityManager.createQuery(
            "SELECT fu FROM FotoUsuario fu WHERE fu.Usuario_id.Id = :usuarioId AND fu.Flg_Inativo = false", 
            FotoUsuario.class);
        query.setParameter("usuarioId", usuarioId);
        return query.getResultList();
    }

    @Override
    public List<FotoUsuario> findByFotoId(Integer fotoId) {
        TypedQuery<FotoUsuario> query = entityManager.createQuery(
            "SELECT fu FROM FotoUsuario fu WHERE fu.Foto_id.Id = :fotoId AND fu.Flg_Inativo = false", 
            FotoUsuario.class);
        query.setParameter("fotoId", fotoId);
        return query.getResultList();
    }
}

