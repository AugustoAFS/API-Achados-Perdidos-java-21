package com.AchadosPerdidos.API.Infrastruture.DataBase;

import com.AchadosPerdidos.API.Domain.Entity.UsuarioRole;
import com.AchadosPerdidos.API.Infrastruture.DataBase.Interfaces.IUsuarioRoleQueries;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class UsuarioRoleQueries implements IUsuarioRoleQueries {

    private final JdbcTemplate jdbcTemplate;
    
    public UsuarioRoleQueries(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @NonNull
    private final RowMapper<UsuarioRole> rowMapper = (rs, rowNum) -> {
        UsuarioRole usuarioRole = new UsuarioRole();
        usuarioRole.setId(rs.getInt("id"));
        usuarioRole.setUsuario_id(rs.getInt("usuario_id"));
        usuarioRole.setRole_id(rs.getInt("role_id"));
        
        Timestamp dtaCriacao = rs.getTimestamp("Dta_Criacao");
        if (dtaCriacao != null) {
            usuarioRole.setDta_Criacao(dtaCriacao.toLocalDateTime());
        }
        
        usuarioRole.setFlg_Inativo(rs.getBoolean("Flg_Inativo"));
        
        Timestamp dtaRemocao = rs.getTimestamp("Dta_Remocao");
        if (dtaRemocao != null) {
            usuarioRole.setDta_Remocao(dtaRemocao.toLocalDateTime());
        }
        
        return usuarioRole;
    };

    @Override
    public List<UsuarioRole> findAll() {
        String sql = "SELECT * FROM ap_achados_perdidos.usuario_roles ORDER BY Dta_Criacao DESC";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public UsuarioRole findByUsuarioIdAndRoleId(Integer usuarioId, Integer roleId) {
        String sql = "SELECT * FROM ap_achados_perdidos.usuario_roles WHERE usuario_id = ? AND role_id = ?";
        List<UsuarioRole> usuarioRoles = jdbcTemplate.query(sql, rowMapper, usuarioId, roleId);
        return usuarioRoles.isEmpty() ? null : usuarioRoles.get(0);
    }

    @Override
    public List<UsuarioRole> findByUsuarioId(Integer usuarioId) {
        String sql = "SELECT * FROM ap_achados_perdidos.usuario_roles WHERE usuario_id = ? ORDER BY Dta_Criacao DESC";
        return jdbcTemplate.query(sql, rowMapper, usuarioId);
    }

    @Override
    public List<UsuarioRole> findByRoleId(Integer roleId) {
        String sql = "SELECT * FROM ap_achados_perdidos.usuario_roles WHERE role_id = ? ORDER BY Dta_Criacao DESC";
        return jdbcTemplate.query(sql, rowMapper, roleId);
    }

    @Override
    public UsuarioRole insert(UsuarioRole usuarioRole) {
        String sql = "INSERT INTO ap_achados_perdidos.usuario_roles (usuario_id, role_id, Dta_Criacao, Flg_Inativo) VALUES (?, ?, ?, ?)";
        
        org.springframework.jdbc.support.KeyHolder keyHolder = new org.springframework.jdbc.support.GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            java.sql.PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setInt(1, usuarioRole.getUsuario_id());
            ps.setInt(2, usuarioRole.getRole_id());
            ps.setTimestamp(3, usuarioRole.getDta_Criacao() != null ? Timestamp.valueOf(usuarioRole.getDta_Criacao()) : Timestamp.valueOf(LocalDateTime.now()));
            ps.setBoolean(4, usuarioRole.getFlg_Inativo() != null ? usuarioRole.getFlg_Inativo() : false);
            return ps;
        }, keyHolder);
        
        Number key = keyHolder.getKey();
        if (key != null) {
            usuarioRole.setId(key.intValue());
        }
        
        return usuarioRole;
    }

    @Override
    public UsuarioRole update(UsuarioRole usuarioRole) {
        String sql = "UPDATE ap_achados_perdidos.usuario_roles SET usuario_id = ?, role_id = ?, Flg_Inativo = ?, Dta_Remocao = ? WHERE id = ?";
        jdbcTemplate.update(sql, 
            usuarioRole.getUsuario_id(),
            usuarioRole.getRole_id(),
            usuarioRole.getFlg_Inativo(),
            usuarioRole.getDta_Remocao() != null ? Timestamp.valueOf(usuarioRole.getDta_Remocao()) : null,
            usuarioRole.getId());
        
        return findById(usuarioRole.getId());
    }
    
    public UsuarioRole findById(Integer id) {
        String sql = "SELECT * FROM ap_achados_perdidos.usuario_roles WHERE id = ?";
        List<UsuarioRole> usuarioRoles = jdbcTemplate.query(sql, rowMapper, id);
        return usuarioRoles.isEmpty() ? null : usuarioRoles.get(0);
    }

    @Override
    public boolean deleteByUsuarioIdAndRoleId(Integer usuarioId, Integer roleId) {
        String sql = "DELETE FROM ap_achados_perdidos.usuario_roles WHERE usuario_id = ? AND role_id = ?";
        int rowsAffected = jdbcTemplate.update(sql, usuarioId, roleId);
        return rowsAffected > 0;
    }

    @Override
    public List<UsuarioRole> findActive() {
        String sql = "SELECT * FROM ap_achados_perdidos.usuario_roles WHERE Flg_Inativo = false ORDER BY Dta_Criacao DESC";
        return jdbcTemplate.query(sql, rowMapper);
    }
}

