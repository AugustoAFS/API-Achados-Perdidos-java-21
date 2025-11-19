package com.AchadosPerdidos.API.Domain.Repository.Interfaces;

import com.AchadosPerdidos.API.Domain.Entity.Usuario;
import java.util.List;

/**
 * Interface do Repository para Usuários
 * Define operações de persistência para entidade Usuario
 */
public interface IUsuariosRepository {
    // Operações CRUD básicas
    List<Usuario> findAll();
    Usuario findById(int id);
    Usuario findByEmail(String email);
    Usuario findByCpf(String cpf);
    Usuario findByMatricula(String matricula);
    Usuario save(Usuario usuario);
    boolean deleteById(int id);

    // Buscas específicas
    List<Usuario> findActive();
    List<Usuario> findByRole(int tipoRoleId);
    List<Usuario> findByInstitution(int instituicaoId);
    List<Usuario> findByCampus(int campusId);

    // Operações de Campus
    String getCampusNomeAtivoByUsuarioId(int usuarioId);
    boolean associarUsuarioCampus(int usuarioId, int campusId);

    // Métodos para Google Login (OAuth)
    Usuario findByGoogleId(String googleId);
    boolean updatePassword(int id, String newHashedPassword);
}
