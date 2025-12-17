package com.AchadosPerdidos.API.Infrastruture.DataBase.Interfaces;

import com.AchadosPerdidos.API.Domain.Entity.Usuario;
import java.util.List;

public interface IUsuariosQueries {
    List<Usuario> findByInstitution(int instituicaoId);
    List<Usuario> findByCampus(int campusId);
    String getCampusNomeAtivoByUsuarioId(int usuarioId);
    List<Usuario> findByRole(int roleId);
    Usuario findByEmail(String email);
    Usuario findByCpf(String cpf);
    Usuario findByMatricula(String matricula);
}
