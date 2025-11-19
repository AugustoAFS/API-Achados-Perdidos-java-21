package com.AchadosPerdidos.API.Domain.Repository;

import com.AchadosPerdidos.API.Domain.Entity.Usuario;
import com.AchadosPerdidos.API.Domain.Repository.Interfaces.IUsuariosRepository;
import com.AchadosPerdidos.API.Infrastruture.DataBase.UsuariosQueries;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Repository para gerenciar Usuários
 * Usa JPA para CRUD básico e Queries apenas para operações complexas com JOINs
 */
@Repository
public class UsuariosRepository implements IUsuariosRepository {

    @PersistenceContext
    private EntityManager entityManager;
    
    @Autowired
    private UsuariosQueries usuariosQueries; // Apenas para operações com JOINs

    // CRUD básico usando JPA
    @Override
    public List<Usuario> findAll() {
        TypedQuery<Usuario> query = entityManager.createQuery("SELECT u FROM Usuario u ORDER BY u.Nome_completo", Usuario.class);
        return query.getResultList();
    }

    @Override
    public Usuario findById(int id) {
        return entityManager.find(Usuario.class, id);
    }

    @Override
    @Transactional
    public Usuario save(Usuario usuario) {
        if (usuario.getId() == null || usuario.getId() == 0) {
            entityManager.persist(usuario);
            return usuario;
        } else {
            return entityManager.merge(usuario);
        }
    }

    @Override
    @Transactional
    public boolean deleteById(int id) {
        Usuario usuario = entityManager.find(Usuario.class, id);
        if (usuario != null) {
            entityManager.remove(usuario);
            return true;
        }
        return false;
    }

    @Override
    public List<Usuario> findActive() {
        TypedQuery<Usuario> query = entityManager.createQuery(
            "SELECT u FROM Usuario u WHERE u.Flg_Inativo = false ORDER BY u.Nome_completo", Usuario.class);
        return query.getResultList();
    }

    // Queries simples usando JPA
    @Override
    public Usuario findByEmail(String email) {
        TypedQuery<Usuario> query = entityManager.createQuery(
            "SELECT u FROM Usuario u WHERE u.Email = :email", Usuario.class);
        query.setParameter("email", email);
        List<Usuario> resultados = query.getResultList();
        return resultados.isEmpty() ? null : resultados.get(0);
    }

    @Override
    public Usuario findByCpf(String cpf) {
        TypedQuery<Usuario> query = entityManager.createQuery(
            "SELECT u FROM Usuario u WHERE u.CPF = :cpf", Usuario.class);
        query.setParameter("cpf", cpf);
        List<Usuario> resultados = query.getResultList();
        return resultados.isEmpty() ? null : resultados.get(0);
    }

    @Override
    public Usuario findByMatricula(String matricula) {
        TypedQuery<Usuario> query = entityManager.createQuery(
            "SELECT u FROM Usuario u WHERE u.Matricula = :matricula", Usuario.class);
        query.setParameter("matricula", matricula);
        List<Usuario> resultados = query.getResultList();
        return resultados.isEmpty() ? null : resultados.get(0);
    }

    @Override
    public Usuario findByGoogleId(String googleId) {
        // Nota: Google_id não existe mais na entidade Usuario (foi removido na refatoração)
        // Se precisar, adicione de volta ou use uma query nativa
        return null;
    }

    @Override
    public List<Usuario> findByRole(int roleId) {
        TypedQuery<Usuario> query = entityManager.createQuery(
            "SELECT u FROM Usuario u WHERE u.Role_id.Id = :roleId AND u.Flg_Inativo = false ORDER BY u.Nome_completo", 
            Usuario.class);
        query.setParameter("roleId", roleId);
        return query.getResultList();
    }

    @Override
    @Transactional
    public boolean updatePassword(int id, String newHashedPassword) {
        Usuario usuario = entityManager.find(Usuario.class, id);
        if (usuario != null) {
            usuario.setHash_senha(newHashedPassword);
            entityManager.merge(usuario);
            return true;
        }
        return false;
    }

    // Operações complexas com JOINs - usam UsuariosQueries
    @Override
    public List<Usuario> findByInstitution(int instituicaoId) {
        return usuariosQueries.findByInstitution(instituicaoId); // Usa JOIN
    }

    @Override
    public List<Usuario> findByCampus(int campusId) {
        return usuariosQueries.findByCampus(campusId); // Usa JOIN
    }

    @Override
    public String getCampusNomeAtivoByUsuarioId(int usuarioId) {
        return usuariosQueries.getCampusNomeAtivoByUsuarioId(usuarioId); // Usa JOIN
    }

    @Override
    @Transactional
    public boolean associarUsuarioCampus(int usuarioId, int campusId) {
        // Esta operação pode ser feita via JPA também, mas mantendo por enquanto
        // Pode ser refatorada para usar UsuarioCampusRepository
        com.AchadosPerdidos.API.Domain.Entity.UsuarioCampus usuarioCampus = new com.AchadosPerdidos.API.Domain.Entity.UsuarioCampus();
        Usuario usuario = entityManager.find(Usuario.class, usuarioId);
        com.AchadosPerdidos.API.Domain.Entity.Campus campus = entityManager.find(com.AchadosPerdidos.API.Domain.Entity.Campus.class, campusId);
        
        if (usuario != null && campus != null) {
            usuarioCampus.setUsuario_id(usuario);
            usuarioCampus.setCampus_id(campus);
            usuarioCampus.setFlg_Inativo(false);
            usuarioCampus.setDta_Criacao(java.time.LocalDateTime.now());
            entityManager.persist(usuarioCampus);
            return true;
        }
        return false;
    }
}
