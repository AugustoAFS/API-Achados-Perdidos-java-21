package com.AchadosPerdidos.API.Infrastruture.DataBase;

import com.AchadosPerdidos.API.Domain.Entity.FotoUsuario;
import com.AchadosPerdidos.API.Infrastruture.DataBase.Interfaces.IFotoUsuarioQueries;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class FotoUsuarioQueries implements IFotoUsuarioQueries {

    private final JdbcTemplate jdbcTemplate;
    
    public FotoUsuarioQueries(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @NonNull
    private final RowMapper<FotoUsuario> rowMapper = (rs, rowNum) -> {
        FotoUsuario fotoUsuario = new FotoUsuario();
        fotoUsuario.setId(rs.getInt("id"));
        fotoUsuario.setUsuarioId(rs.getInt("usuario_id"));
        fotoUsuario.setFotoId(rs.getInt("foto_id"));
        
        Timestamp dtaCriacao = rs.getTimestamp("Dta_Criacao");
        if (dtaCriacao != null) {
            fotoUsuario.setDtaCriacao(dtaCriacao.toLocalDateTime());
        }
        
        fotoUsuario.setFlgInativo(rs.getBoolean("Flg_Inativo"));
        
        Timestamp dtaRemocao = rs.getTimestamp("Dta_Remocao");
        if (dtaRemocao != null) {
            fotoUsuario.setDtaRemocao(dtaRemocao.toLocalDateTime());
        }
        
        return fotoUsuario;
    };

    @Override
    public List<FotoUsuario> findAll() {
        String sql = "SELECT * FROM ap_achados_perdidos.fotos_usuario ORDER BY Dta_Criacao DESC";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public FotoUsuario findByUsuarioIdAndFotoId(Integer usuarioId, Integer fotoId) {
        String sql = "SELECT * FROM ap_achados_perdidos.fotos_usuario WHERE usuario_id = ? AND foto_id = ?";
        List<FotoUsuario> fotos = jdbcTemplate.query(sql, rowMapper, usuarioId, fotoId);
        return fotos.isEmpty() ? null : fotos.get(0);
    }

    @Override
    public List<FotoUsuario> findByUsuarioId(Integer usuarioId) {
        String sql = "SELECT * FROM ap_achados_perdidos.fotos_usuario WHERE usuario_id = ? ORDER BY Dta_Criacao DESC";
        return jdbcTemplate.query(sql, rowMapper, usuarioId);
    }

    @Override
    public List<FotoUsuario> findByFotoId(Integer fotoId) {
        String sql = "SELECT * FROM ap_achados_perdidos.fotos_usuario WHERE foto_id = ? ORDER BY Dta_Criacao DESC";
        return jdbcTemplate.query(sql, rowMapper, fotoId);
    }

    @Override
    public FotoUsuario insert(FotoUsuario fotoUsuario) {
        String sql = "INSERT INTO ap_achados_perdidos.fotos_usuario (usuario_id, foto_id, Dta_Criacao, Flg_Inativo) VALUES (?, ?, ?, ?)";
        
        org.springframework.jdbc.support.KeyHolder keyHolder = new org.springframework.jdbc.support.GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            java.sql.PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setInt(1, fotoUsuario.getUsuarioId());
            ps.setInt(2, fotoUsuario.getFotoId());
            ps.setTimestamp(3, fotoUsuario.getDtaCriacao() != null ? Timestamp.valueOf(fotoUsuario.getDtaCriacao()) : Timestamp.valueOf(LocalDateTime.now()));
            ps.setBoolean(4, fotoUsuario.getFlgInativo() != null ? fotoUsuario.getFlgInativo() : false);
            return ps;
        }, keyHolder);
        
        Number key = keyHolder.getKey();
        if (key != null) {
            fotoUsuario.setId(key.intValue());
        }
        
        return fotoUsuario;
    }

    @Override
    public FotoUsuario update(FotoUsuario fotoUsuario) {
        String sql = "UPDATE ap_achados_perdidos.fotos_usuario SET usuario_id = ?, foto_id = ?, Flg_Inativo = ?, Dta_Remocao = ? WHERE id = ?";
        jdbcTemplate.update(sql, 
            fotoUsuario.getUsuarioId(),
            fotoUsuario.getFotoId(),
            fotoUsuario.getFlgInativo(),
            fotoUsuario.getDtaRemocao() != null ? Timestamp.valueOf(fotoUsuario.getDtaRemocao()) : null,
            fotoUsuario.getId());
        
        return findById(fotoUsuario.getId());
    }
    
    public FotoUsuario findById(Integer id) {
        String sql = "SELECT * FROM ap_achados_perdidos.fotos_usuario WHERE id = ?";
        List<FotoUsuario> fotos = jdbcTemplate.query(sql, rowMapper, id);
        return fotos.isEmpty() ? null : fotos.get(0);
    }

    @Override
    public boolean deleteByUsuarioIdAndFotoId(Integer usuarioId, Integer fotoId) {
        String sql = "DELETE FROM ap_achados_perdidos.fotos_usuario WHERE usuario_id = ? AND foto_id = ?";
        int rowsAffected = jdbcTemplate.update(sql, usuarioId, fotoId);
        return rowsAffected > 0;
    }

    @Override
    public List<FotoUsuario> findActive() {
        String sql = "SELECT * FROM ap_achados_perdidos.fotos_usuario WHERE Flg_Inativo = false ORDER BY Dta_Criacao DESC";
        return jdbcTemplate.query(sql, rowMapper);
    }
}

