package com.AchadosPerdidos.API.Infrastruture.DataBase;

import com.AchadosPerdidos.API.Domain.Entity.UsuarioCampus;
import com.AchadosPerdidos.API.Infrastruture.DataBase.Interfaces.IUsuarioCampusQueries;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class UsuarioCampusQueries implements IUsuarioCampusQueries {

    private final JdbcTemplate jdbcTemplate;
    
    public UsuarioCampusQueries(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @NonNull
    private final RowMapper<UsuarioCampus> rowMapper = (rs, rowNum) -> {
        UsuarioCampus usuarioCampus = new UsuarioCampus();
        usuarioCampus.setId(rs.getInt("id"));
        usuarioCampus.setUsuario_id(rs.getInt("usuario_id"));
        usuarioCampus.setCampus_id(rs.getInt("campus_id"));
        
        Timestamp dtaCriacao = rs.getTimestamp("Dta_Criacao");
        if (dtaCriacao != null) {
            usuarioCampus.setDta_Criacao(dtaCriacao.toLocalDateTime());
        }
        
        usuarioCampus.setFlg_Inativo(rs.getBoolean("Flg_Inativo"));
        
        Timestamp dtaRemocao = rs.getTimestamp("Dta_Remocao");
        if (dtaRemocao != null) {
            usuarioCampus.setDtaRemocao(dtaRemocao.toLocalDateTime());
        }
        
        return usuarioCampus;
    };

    @Override
    public List<UsuarioCampus> findAll() {
        String sql = "SELECT * FROM ap_achados_perdidos.usuario_campus ORDER BY Dta_Criacao DESC";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public UsuarioCampus findByUsuarioIdAndCampusId(Integer usuarioId, Integer campusId) {
        String sql = "SELECT * FROM ap_achados_perdidos.usuario_campus WHERE usuario_id = ? AND campus_id = ?";
        List<UsuarioCampus> usuarioCampus = jdbcTemplate.query(sql, rowMapper, usuarioId, campusId);
        return usuarioCampus.isEmpty() ? null : usuarioCampus.get(0);
    }

    @Override
    public List<UsuarioCampus> findByUsuarioId(Integer usuarioId) {
        String sql = "SELECT * FROM ap_achados_perdidos.usuario_campus WHERE usuario_id = ? ORDER BY Dta_Criacao DESC";
        return jdbcTemplate.query(sql, rowMapper, usuarioId);
    }

    @Override
    public List<UsuarioCampus> findByCampusId(Integer campusId) {
        String sql = "SELECT * FROM ap_achados_perdidos.usuario_campus WHERE campus_id = ? ORDER BY Dta_Criacao DESC";
        return jdbcTemplate.query(sql, rowMapper, campusId);
    }

    @Override
    public UsuarioCampus insert(UsuarioCampus usuarioCampus) {
        String sql = "INSERT INTO ap_achados_perdidos.usuario_campus (usuario_id, campus_id, Dta_Criacao, Flg_Inativo) VALUES (?, ?, ?, ?)";
        
        org.springframework.jdbc.support.KeyHolder keyHolder = new org.springframework.jdbc.support.GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            java.sql.PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setInt(1, usuarioCampus.getUsuario_id());
            ps.setInt(2, usuarioCampus.getCampus_id());
            ps.setTimestamp(3, usuarioCampus.getDta_Criacao() != null ? Timestamp.valueOf(usuarioCampus.getDta_Criacao()) : Timestamp.valueOf(LocalDateTime.now()));
            ps.setBoolean(4, usuarioCampus.getFlg_Inativo() != null ? usuarioCampus.getFlg_Inativo() : false);
            return ps;
        }, keyHolder);
        
        Number key = keyHolder.getKey();
        if (key != null) {
            usuarioCampus.setId(key.intValue());
        }
        
        return usuarioCampus;
    }

    @Override
    public UsuarioCampus update(UsuarioCampus usuarioCampus) {
        String sql = "UPDATE ap_achados_perdidos.usuario_campus SET usuario_id = ?, campus_id = ?, Flg_Inativo = ?, Dta_Remocao = ? WHERE id = ?";
        jdbcTemplate.update(sql, 
            usuarioCampus.getUsuario_id(),
            usuarioCampus.getCampus_id(),
            usuarioCampus.getFlg_Inativo(),
            usuarioCampus.getDtaRemocao() != null ? Timestamp.valueOf(usuarioCampus.getDtaRemocao()) : null,
            usuarioCampus.getId());
        
        return findById(usuarioCampus.getId());
    }
    
    public UsuarioCampus findById(Integer id) {
        String sql = "SELECT * FROM ap_achados_perdidos.usuario_campus WHERE id = ?";
        List<UsuarioCampus> usuarioCampus = jdbcTemplate.query(sql, rowMapper, id);
        return usuarioCampus.isEmpty() ? null : usuarioCampus.get(0);
    }

    @Override
    public boolean deleteByUsuarioIdAndCampusId(Integer usuarioId, Integer campusId) {
        String sql = "DELETE FROM ap_achados_perdidos.usuario_campus WHERE usuario_id = ? AND campus_id = ?";
        int rowsAffected = jdbcTemplate.update(sql, usuarioId, campusId);
        return rowsAffected > 0;
    }

    @Override
    public List<UsuarioCampus> findActive() {
        String sql = "SELECT * FROM ap_achados_perdidos.usuario_campus WHERE Flg_Inativo = false ORDER BY Dta_Criacao DESC";
        return jdbcTemplate.query(sql, rowMapper);
    }
}

