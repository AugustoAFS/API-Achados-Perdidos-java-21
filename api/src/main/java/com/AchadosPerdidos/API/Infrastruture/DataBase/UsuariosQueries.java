package com.AchadosPerdidos.API.Infrastruture.DataBase;

import com.AchadosPerdidos.API.Domain.Entity.Usuario;
import com.AchadosPerdidos.API.Infrastruture.DataBase.Interfaces.IUsuariosQueries;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class UsuariosQueries implements IUsuariosQueries {

    private final JdbcTemplate jdbcTemplate;

    public UsuariosQueries(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @NonNull
    private final RowMapper<Usuario> rowMapper = (rs, rowNum) -> {
        Usuario usuario = new Usuario();
        usuario.setId(rs.getInt("id"));
        usuario.setNomeCompleto(rs.getString("nome_completo"));
        usuario.setCpf(rs.getString("cpf"));
        usuario.setEmail(rs.getString("email"));
        usuario.setHash_senha(rs.getString("hash_senha"));
        usuario.setMatricula(rs.getString("matricula"));
        usuario.setNumero_telefone(rs.getString("numero_telefone"));
        
        Integer enderecoId = rs.getObject("endereco_id", Integer.class);
        usuario.setEndereco_id(enderecoId);
        
        Timestamp dtaCriacao = rs.getTimestamp("Dta_Criacao");
        if (dtaCriacao != null) {
            usuario.setDta_Criacao(dtaCriacao.toLocalDateTime());
        }
        
        usuario.setFlg_Inativo(rs.getBoolean("Flg_Inativo"));
        
        Timestamp dtaRemocao = rs.getTimestamp("Dta_Remocao");
        if (dtaRemocao != null) {
            usuario.setDta_Remocao(dtaRemocao.toLocalDateTime());
        }
        
        return usuario;
    };

    @Override
    public List<Usuario> findAll() {
        String sql = "SELECT * FROM ap_achados_perdidos.usuarios ORDER BY nome_completo";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public Usuario findById(int id) {
        String sql = "SELECT * FROM ap_achados_perdidos.usuarios WHERE id = ?";
        List<Usuario> usuarios = jdbcTemplate.query(sql, rowMapper, id);
        return usuarios.isEmpty() ? null : usuarios.get(0);
    }

    @Override
    public Usuario findByEmail(String email) {
        String sql = "SELECT * FROM ap_achados_perdidos.usuarios WHERE email = ?";
        List<Usuario> usuarios = jdbcTemplate.query(sql, rowMapper, email);
        return usuarios.isEmpty() ? null : usuarios.get(0);
    }

    @Override
    public Usuario findByCpf(String cpf) {
        String sql = "SELECT * FROM ap_achados_perdidos.usuarios WHERE cpf = ?";
        List<Usuario> usuarios = jdbcTemplate.query(sql, rowMapper, cpf);
        return usuarios.isEmpty() ? null : usuarios.get(0);
    }

    @Override
    public Usuario findByMatricula(String matricula) {
        String sql = "SELECT * FROM ap_achados_perdidos.usuarios WHERE matricula = ?";
        List<Usuario> usuarios = jdbcTemplate.query(sql, rowMapper, matricula);
        return usuarios.isEmpty() ? null : usuarios.get(0);
    }

    @Override
    public Usuario insert(Usuario usuario) {
        String sql = "INSERT INTO ap_achados_perdidos.usuarios (nome_completo, cpf, email, hash_senha, matricula, numero_telefone, endereco_id, Dta_Criacao, Flg_Inativo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, 
            usuario.getNomeCompleto(),
            usuario.getCpf(),
            usuario.getEmail(),
            usuario.getHash_senha(),
            usuario.getMatricula(),
            usuario.getNumero_telefone(),
            usuario.getEndereco_id(),
            usuario.getDta_Criacao() != null ? Timestamp.valueOf(usuario.getDta_Criacao()) : Timestamp.valueOf(LocalDateTime.now()),
            usuario.getFlg_Inativo() != null ? usuario.getFlg_Inativo() : false);
        
        String selectSql = "SELECT * FROM ap_achados_perdidos.usuarios WHERE email = ? AND Dta_Criacao = ? ORDER BY id DESC LIMIT 1";
        List<Usuario> inserted = jdbcTemplate.query(selectSql, rowMapper, 
            usuario.getEmail(), 
            usuario.getDta_Criacao() != null ? Timestamp.valueOf(usuario.getDta_Criacao()) : Timestamp.valueOf(LocalDateTime.now()));
        
        return inserted.isEmpty() ? null : inserted.get(0);
    }

    @Override
    public Usuario update(Usuario usuario) {
        String sql = "UPDATE ap_achados_perdidos.usuarios SET nome_completo = ?, cpf = ?, email = ?, hash_senha = ?, matricula = ?, numero_telefone = ?, endereco_id = ?, Flg_Inativo = ?, Dta_Remocao = ? WHERE id = ?";
        jdbcTemplate.update(sql, 
            usuario.getNomeCompleto(),
            usuario.getCpf(),
            usuario.getEmail(),
            usuario.getHash_senha(),
            usuario.getMatricula(),
            usuario.getNumero_telefone(),
            usuario.getEndereco_id(),
            usuario.getFlg_Inativo(),
            usuario.getDta_Remocao() != null ? Timestamp.valueOf(usuario.getDta_Remocao()) : null,
            usuario.getId());
        
        return findById(usuario.getId());
    }

    @Override
    public boolean deleteById(int id) {
        String sql = "DELETE FROM ap_achados_perdidos.usuarios WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, id);
        return rowsAffected > 0;
    }

    @Override
    public List<Usuario> findActive() {
        String sql = "SELECT * FROM ap_achados_perdidos.usuarios WHERE Flg_Inativo = false ORDER BY nome_completo";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public List<Usuario> findByRole(int tipoRoleId) {
        String sql = "SELECT u.* FROM ap_achados_perdidos.usuarios u " +
                     "INNER JOIN ap_achados_perdidos.usuario_roles ur ON u.id = ur.usuario_id " +
                     "WHERE ur.role_id = ? AND ur.Flg_Inativo = false ORDER BY u.nome_completo";
        return jdbcTemplate.query(sql, rowMapper, tipoRoleId);
    }

    @Override
    public List<Usuario> findByInstitution(int instituicaoId) {
        String sql = "SELECT u.* FROM ap_achados_perdidos.usuarios u " +
                     "INNER JOIN ap_achados_perdidos.usuario_campus uc ON u.id = uc.usuario_id " +
                     "INNER JOIN ap_achados_perdidos.campus c ON uc.campus_id = c.id " +
                     "WHERE c.instituicao_id = ? AND uc.Flg_Inativo = false ORDER BY u.nome_completo";
        return jdbcTemplate.query(sql, rowMapper, instituicaoId);
    }

    @Override
    public List<Usuario> findByCampus(int campusId) {
        String sql = "SELECT u.* FROM ap_achados_perdidos.usuarios u " +
                     "INNER JOIN ap_achados_perdidos.usuario_campus uc ON u.id = uc.usuario_id " +
                     "WHERE uc.campus_id = ? AND uc.Flg_Inativo = false ORDER BY u.nome_completo";
        return jdbcTemplate.query(sql, rowMapper, campusId);
    }

    @Override
    public String getCampusNomeAtivoByUsuarioId(int usuarioId) {
        String sql = "SELECT c.nome FROM ap_achados_perdidos.usuario_campus uc " +
                     "INNER JOIN ap_achados_perdidos.campus c ON uc.campus_id = c.id " +
                     "WHERE uc.usuario_id = ? AND uc.Flg_Inativo = false AND c.Flg_Inativo = false " +
                     "ORDER BY uc.Dta_Criacao ASC LIMIT 1";
        try {
            List<String> resultados = jdbcTemplate.query(sql, (rs, rowNum) -> {
                String nome = rs.getString("nome");
                return nome != null ? nome : "";
            }, usuarioId);
            return resultados.isEmpty() ? "" : resultados.get(0);
        } catch (Exception e) {
            return "";
        }
    }

    @Override
    public boolean associarUsuarioCampus(int usuarioId, int campusId) {
        String sql = "INSERT INTO ap_achados_perdidos.usuario_campus (usuario_id, campus_id, Dta_Criacao, Flg_Inativo) " +
                     "VALUES (?, ?, ?, false) " +
                     "ON CONFLICT (usuario_id, campus_id) DO UPDATE SET Flg_Inativo = false, Dta_Remocao = NULL";
        int rowsAffected = jdbcTemplate.update(sql, usuarioId, campusId, Timestamp.valueOf(LocalDateTime.now()));
        return rowsAffected > 0;
    }
}
