package com.AchadosPerdidos.API.Domain.Repository;

import com.AchadosPerdidos.API.Domain.Entity.FotoUsuario;
import com.AchadosPerdidos.API.Domain.Repository.Interfaces.IFotoUsuarioRepository;
import com.AchadosPerdidos.API.Infrastruture.DataBase.FotoUsuarioQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FotoUsuarioRepository implements IFotoUsuarioRepository {

    @Autowired
    private FotoUsuarioQueries fotoUsuarioQueries;

    @Override
    public List<FotoUsuario> findAll() {
        return fotoUsuarioQueries.findAll();
    }

    @Override
    public FotoUsuario findByUsuarioIdAndFotoId(Integer usuarioId, Integer fotoId) {
        return fotoUsuarioQueries.findByUsuarioIdAndFotoId(usuarioId, fotoId);
    }

    @Override
    public FotoUsuario save(FotoUsuario fotoUsuario) {
        FotoUsuario existing = fotoUsuarioQueries.findByUsuarioIdAndFotoId(fotoUsuario.getUsuarioId(), fotoUsuario.getFotoId());
        if (existing == null) {
            return fotoUsuarioQueries.insert(fotoUsuario);
        } else {
            return fotoUsuarioQueries.update(fotoUsuario);
        }
    }

    @Override
    public boolean deleteByUsuarioIdAndFotoId(Integer usuarioId, Integer fotoId) {
        return fotoUsuarioQueries.deleteByUsuarioIdAndFotoId(usuarioId, fotoId);
    }

    @Override
    public List<FotoUsuario> findActive() {
        return fotoUsuarioQueries.findActive();
    }

    @Override
    public List<FotoUsuario> findByUsuarioId(Integer usuarioId) {
        return fotoUsuarioQueries.findByUsuarioId(usuarioId);
    }

    @Override
    public List<FotoUsuario> findByFotoId(Integer fotoId) {
        return fotoUsuarioQueries.findByFotoId(fotoId);
    }
}

