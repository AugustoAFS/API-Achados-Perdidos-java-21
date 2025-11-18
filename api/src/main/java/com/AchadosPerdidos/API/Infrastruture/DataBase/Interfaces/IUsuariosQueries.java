package com.AchadosPerdidos.API.Infrastruture.DataBase.Interfaces;

import com.AchadosPerdidos.API.Domain.Entity.Usuario;
import java.util.List;

public interface IUsuariosQueries {
    List<Usuario> findAll();
    Usuario findById(int id);
    Usuario findByEmail(String email);
    Usuario findByCpf(String cpf);
    Usuario findByMatricula(String matricula);
    Usuario insert(Usuario usuario);
    Usuario update(Usuario usuario);
    boolean deleteById(int id);
    List<Usuario> findActive();
    List<Usuario> findByRole(int tipoRoleId);
    List<Usuario> findByInstitution(int instituicaoId);
    List<Usuario> findByCampus(int campusId);
    String getCampusNomeAtivoByUsuarioId(int usuarioId);
    boolean associarUsuarioCampus(int usuarioId, int campusId);
}
