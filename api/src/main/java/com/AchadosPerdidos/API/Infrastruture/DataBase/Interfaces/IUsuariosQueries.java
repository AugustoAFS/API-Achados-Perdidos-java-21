package com.AchadosPerdidos.API.Infrastruture.DataBase.Interfaces;

import com.AchadosPerdidos.API.Domain.Entity.Usuario;
import java.util.List;

/**
 * Interface para operações complexas de Usuarios que requerem JOINs
 * CRUD básico é feito via JPA no UsuariosRepository
 */
public interface IUsuariosQueries {
    // Operações com JOINs
    List<Usuario> findByInstitution(int instituicaoId);
    List<Usuario> findByCampus(int campusId);
    String getCampusNomeAtivoByUsuarioId(int usuarioId);
}
