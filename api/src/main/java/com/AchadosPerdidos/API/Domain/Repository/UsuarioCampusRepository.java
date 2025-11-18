package com.AchadosPerdidos.API.Domain.Repository;

import com.AchadosPerdidos.API.Domain.Entity.UsuarioCampus;
import com.AchadosPerdidos.API.Domain.Repository.Interfaces.IUsuarioCampusRepository;
import com.AchadosPerdidos.API.Infrastruture.DataBase.UsuarioCampusQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UsuarioCampusRepository implements IUsuarioCampusRepository {

    @Autowired
    private UsuarioCampusQueries usuarioCampusQueries;

    @Override
    public List<UsuarioCampus> findAll() {
        return usuarioCampusQueries.findAll();
    }

    @Override
    public UsuarioCampus findByUsuarioIdAndCampusId(Integer usuarioId, Integer campusId) {
        return usuarioCampusQueries.findByUsuarioIdAndCampusId(usuarioId, campusId);
    }

    @Override
    public UsuarioCampus save(UsuarioCampus usuarioCampus) {
        UsuarioCampus existing = usuarioCampusQueries.findByUsuarioIdAndCampusId(usuarioCampus.getUsuario_id(), usuarioCampus.getCampus_id());
        if (existing == null) {
            return usuarioCampusQueries.insert(usuarioCampus);
        } else {
            return usuarioCampusQueries.update(usuarioCampus);
        }
    }

    @Override
    public boolean deleteByUsuarioIdAndCampusId(Integer usuarioId, Integer campusId) {
        return usuarioCampusQueries.deleteByUsuarioIdAndCampusId(usuarioId, campusId);
    }

    @Override
    public List<UsuarioCampus> findActive() {
        return usuarioCampusQueries.findActive();
    }

    @Override
    public List<UsuarioCampus> findByUsuarioId(Integer usuarioId) {
        return usuarioCampusQueries.findByUsuarioId(usuarioId);
    }

    @Override
    public List<UsuarioCampus> findByCampusId(Integer campusId) {
        return usuarioCampusQueries.findByCampusId(campusId);
    }
}

