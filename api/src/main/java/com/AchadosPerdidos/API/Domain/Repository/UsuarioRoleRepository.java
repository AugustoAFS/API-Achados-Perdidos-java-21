package com.AchadosPerdidos.API.Domain.Repository;

import com.AchadosPerdidos.API.Domain.Entity.UsuarioRole;
import com.AchadosPerdidos.API.Domain.Repository.Interfaces.IUsuarioRoleRepository;
import com.AchadosPerdidos.API.Infrastruture.DataBase.UsuarioRoleQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UsuarioRoleRepository implements IUsuarioRoleRepository {

    @Autowired
    private UsuarioRoleQueries usuarioRoleQueries;

    @Override
    public List<UsuarioRole> findAll() {
        return usuarioRoleQueries.findAll();
    }

    @Override
    public UsuarioRole findByUsuarioIdAndRoleId(Integer usuarioId, Integer roleId) {
        return usuarioRoleQueries.findByUsuarioIdAndRoleId(usuarioId, roleId);
    }

    @Override
    public UsuarioRole save(UsuarioRole usuarioRole) {
        UsuarioRole existing = usuarioRoleQueries.findByUsuarioIdAndRoleId(usuarioRole.getUsuario_id(), usuarioRole.getRole_id());
        if (existing == null) {
            return usuarioRoleQueries.insert(usuarioRole);
        } else {
            return usuarioRoleQueries.update(usuarioRole);
        }
    }

    @Override
    public boolean deleteByUsuarioIdAndRoleId(Integer usuarioId, Integer roleId) {
        return usuarioRoleQueries.deleteByUsuarioIdAndRoleId(usuarioId, roleId);
    }

    @Override
    public List<UsuarioRole> findActive() {
        return usuarioRoleQueries.findActive();
    }

    @Override
    public List<UsuarioRole> findByUsuarioId(Integer usuarioId) {
        return usuarioRoleQueries.findByUsuarioId(usuarioId);
    }

    @Override
    public List<UsuarioRole> findByRoleId(Integer roleId) {
        return usuarioRoleQueries.findByRoleId(roleId);
    }
}

