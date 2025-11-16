package com.AchadosPerdidos.API.Infrastruture.DataBase;

import com.AchadosPerdidos.API.Domain.Entity.Usuarios;
import com.AchadosPerdidos.API.Infrastruture.DataBase.Interfaces.IUsuariosQueries;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UsuariosQueries implements IUsuariosQueries {

    private final JdbcTemplate jdbcTemplate;

    public UsuariosQueries(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Usuarios> rowMapper = (rs, rowNum) -> {
        Usuarios usuarios = new Usuarios();
        usuarios.setId(rs.getInt("id"));
        usuarios.setNomeCompleto(rs.getString("nome_completo"));
        usuarios.setCpf(rs.getString("cpf"));
        usuarios.setEmail(rs.getString("email"));
        usuarios.setHashSenha(rs.getString("hash_senha"));
        usuarios.setMatricula(rs.getString("matricula"));
        usuarios.setNumeroTelefone(rs.getString("numero_telefone"));
        
        // Tratamento correto para campos nullable
        Integer empresaId = rs.getObject("empresa_id", Integer.class);
        usuarios.setEmpresaId(empresaId);
        
        Integer enderecoId = rs.getObject("endereco_id", Integer.class);
        usuarios.setEnderecoId(enderecoId);
        
        usuarios.setDtaCriacao(rs.getTimestamp("Dta_Criacao"));
        usuarios.setFlgInativo(rs.getBoolean("Flg_Inativo"));
        usuarios.setDtaRemocao(rs.getTimestamp("Dta_Remocao"));
        return usuarios;
    };

    @Override
    public List<Usuarios> findAll() {
        String sql = "SELECT * FROM ap_achados_perdidos.usuarios ORDER BY nome_completo";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public Usuarios findById(int id) {
        String sql = "SELECT * FROM ap_achados_perdidos.usuarios WHERE id = ?";
        List<Usuarios> usuarios = jdbcTemplate.query(sql, rowMapper, id);
        return usuarios.isEmpty() ? null : usuarios.get(0);
    }

    @Override
    public Usuarios findByEmail(String email) {
        String sql = "SELECT * FROM ap_achados_perdidos.usuarios WHERE email = ?";
        List<Usuarios> usuarios = jdbcTemplate.query(sql, rowMapper, email);
        return usuarios.isEmpty() ? null : usuarios.get(0);
    }

    @Override
    public Usuarios findByEmailAndPassword(String email, String senha) {
        String sql = "SELECT * FROM ap_achados_perdidos.usuarios WHERE email = ? AND hash_senha = ?";
        List<Usuarios> usuarios = jdbcTemplate.query(sql, rowMapper, email, senha);
        return usuarios.isEmpty() ? null : usuarios.get(0);
    }

    @Override
    public Usuarios findByCpf(String cpf) {
        String sql = "SELECT * FROM ap_achados_perdidos.usuarios WHERE cpf = ?";
        List<Usuarios> usuarios = jdbcTemplate.query(sql, rowMapper, cpf);
        return usuarios.isEmpty() ? null : usuarios.get(0);
    }

    @Override
    public Usuarios findByMatricula(String matricula) {
        String sql = "SELECT * FROM ap_achados_perdidos.usuarios WHERE matricula = ?";
        List<Usuarios> usuarios = jdbcTemplate.query(sql, rowMapper, matricula);
        return usuarios.isEmpty() ? null : usuarios.get(0);
    }

    @Override
    public Usuarios insert(Usuarios usuarios) {
        String sql = "INSERT INTO ap_achados_perdidos.usuarios (nome_completo, cpf, email, hash_senha, matricula, numero_telefone, empresa_id, endereco_id, Dta_Criacao, Flg_Inativo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, 
            usuarios.getNomeCompleto(),
            usuarios.getCpf(),
            usuarios.getEmail(),
            usuarios.getHashSenha(),
            usuarios.getMatricula(),
            usuarios.getNumeroTelefone(),
            usuarios.getEmpresaId(),
            usuarios.getEnderecoId(),
            usuarios.getDtaCriacao(),
            usuarios.getFlgInativo());
        
        // Buscar o registro inserido para retornar com o ID
        String selectSql = "SELECT * FROM ap_achados_perdidos.usuarios WHERE email = ? AND Dta_Criacao = ? ORDER BY id DESC LIMIT 1";
        List<Usuarios> inserted = jdbcTemplate.query(selectSql, rowMapper, 
            usuarios.getEmail(), 
            usuarios.getDtaCriacao());
        
        return inserted.isEmpty() ? null : inserted.get(0);
    }

    @Override
    public Usuarios update(Usuarios usuarios) {
        String sql = "UPDATE ap_achados_perdidos.usuarios SET nome_completo = ?, cpf = ?, email = ?, hash_senha = ?, matricula = ?, numero_telefone = ?, empresa_id = ?, endereco_id = ?, Flg_Inativo = ?, Dta_Remocao = ? WHERE id = ?";
        jdbcTemplate.update(sql, 
            usuarios.getNomeCompleto(),
            usuarios.getCpf(),
            usuarios.getEmail(),
            usuarios.getHashSenha(),
            usuarios.getMatricula(),
            usuarios.getNumeroTelefone(),
            usuarios.getEmpresaId(),
            usuarios.getEnderecoId(),
            usuarios.getFlgInativo(),
            usuarios.getDtaRemocao(),
            usuarios.getId());
        
        return findById(usuarios.getId());
    }

    @Override
    public boolean deleteById(int id) {
        String sql = "DELETE FROM ap_achados_perdidos.usuarios WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, id);
        return rowsAffected > 0;
    }

    @Override
    public List<Usuarios> findActive() {
        String sql = "SELECT * FROM ap_achados_perdidos.usuarios WHERE Flg_Inativo = false ORDER BY nome_completo";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public List<Usuarios> findByRole(int tipoRoleId) {
        String sql = "SELECT u.* FROM ap_achados_perdidos.usuarios u " +
                     "INNER JOIN ap_achados_perdidos.usuario_roles ur ON u.id = ur.usuario_id " +
                     "WHERE ur.role_id = ? AND ur.Flg_Inativo = false ORDER BY u.nome_completo";
        return jdbcTemplate.query(sql, rowMapper, tipoRoleId);
    }

    @Override
    public List<Usuarios> findByInstitution(int instituicaoId) {
        String sql = "SELECT u.* FROM ap_achados_perdidos.usuarios u " +
                     "INNER JOIN ap_achados_perdidos.usuario_campus uc ON u.id = uc.usuario_id " +
                     "INNER JOIN ap_achados_perdidos.campus c ON uc.campus_id = c.id " +
                     "WHERE c.instituicao_id = ? AND uc.Flg_Inativo = false ORDER BY u.nome_completo";
        return jdbcTemplate.query(sql, rowMapper, instituicaoId);
    }

    @Override
    public List<Usuarios> findByCampus(int campusId) {
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
            // Se houver erro na query, retorna string vazia
            return "";
        }
    }

    @Override
    public boolean associarUsuarioCampus(int usuarioId, int campusId) {
        String sql = "INSERT INTO ap_achados_perdidos.usuario_campus (usuario_id, campus_id, Flg_Inativo) " +
                     "VALUES (?, ?, false) " +
                     "ON CONFLICT (usuario_id, campus_id) DO UPDATE SET Flg_Inativo = false, Dta_Remocao = NULL";
        int rowsAffected = jdbcTemplate.update(sql, usuarioId, campusId);
        return rowsAffected > 0;
    }
}