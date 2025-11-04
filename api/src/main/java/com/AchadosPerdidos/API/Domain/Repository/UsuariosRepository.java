package com.AchadosPerdidos.API.Domain.Repository;

import com.AchadosPerdidos.API.Domain.Entity.Usuarios;
import com.AchadosPerdidos.API.Domain.Repository.Interfaces.IUsuariosRepository;
import com.AchadosPerdidos.API.Infrastruture.DataBase.UsuariosQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UsuariosRepository implements IUsuariosRepository {

    @Autowired
    private UsuariosQueries usuariosQueries;

    @Override
    public List<Usuarios> findAll() {
        return usuariosQueries.findAll();
    }

    @Override
    public Usuarios findById(int id) {
        return usuariosQueries.findById(id);
    }

    @Override
    public Usuarios findByEmail(String email) {
        return usuariosQueries.findByEmail(email);
    }

    @Override
    public Usuarios findByEmailAndPassword(String email, String senha) {
        return usuariosQueries.findByEmailAndPassword(email, senha);
    }

    @Override
    public Usuarios save(Usuarios usuarios) {
        if (usuarios.getId() == null || usuarios.getId() == 0) {
            return usuariosQueries.insert(usuarios);
        } else {
            return usuariosQueries.update(usuarios);
        }
    }

    @Override
    public boolean deleteById(int id) {
        return usuariosQueries.deleteById(id);
    }

    @Override
    public List<Usuarios> findActive() {
        return usuariosQueries.findActive();
    }

    @Override
    public List<Usuarios> findByRole(int tipoRoleId) {
        return usuariosQueries.findByRole(tipoRoleId);
    }

    @Override
    public List<Usuarios> findByInstitution(int instituicaoId) {
        return usuariosQueries.findByInstitution(instituicaoId);
    }

    @Override
    public List<Usuarios> findByCampus(int campusId) {
        return usuariosQueries.findByCampus(campusId);
    }
}