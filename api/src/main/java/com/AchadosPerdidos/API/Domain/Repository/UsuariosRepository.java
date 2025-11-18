package com.AchadosPerdidos.API.Domain.Repository;

import com.AchadosPerdidos.API.Domain.Entity.Usuario;
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
    public List<Usuario> findAll() {
        return usuariosQueries.findAll();
    }

    @Override
    public Usuario findById(int id) {
        return usuariosQueries.findById(id);
    }

    @Override
    public Usuario findByEmail(String email) {
        return usuariosQueries.findByEmail(email);
    }

    @Override
    public Usuario findByCpf(String cpf) {
        return usuariosQueries.findByCpf(cpf);
    }

    @Override
    public Usuario findByMatricula(String matricula) {
        return usuariosQueries.findByMatricula(matricula);
    }

    @Override
    public Usuario save(Usuario usuario) {
        if (usuario.getId() == null || usuario.getId() == 0) {
            return usuariosQueries.insert(usuario);
        } else {
            return usuariosQueries.update(usuario);
        }
    }

    @Override
    public boolean deleteById(int id) {
        return usuariosQueries.deleteById(id);
    }

    @Override
    public List<Usuario> findActive() {
        return usuariosQueries.findActive();
    }

    @Override
    public List<Usuario> findByRole(int tipoRoleId) {
        return usuariosQueries.findByRole(tipoRoleId);
    }

    @Override
    public List<Usuario> findByInstitution(int instituicaoId) {
        return usuariosQueries.findByInstitution(instituicaoId);
    }

    @Override
    public List<Usuario> findByCampus(int campusId) {
        return usuariosQueries.findByCampus(campusId);
    }

    @Override
    public String getCampusNomeAtivoByUsuarioId(int usuarioId) {
        return usuariosQueries.getCampusNomeAtivoByUsuarioId(usuarioId);
    }

    @Override
    public boolean associarUsuarioCampus(int usuarioId, int campusId) {
        return usuariosQueries.associarUsuarioCampus(usuarioId, campusId);
    }
}
