package com.AchadosPerdidos.API.Infrastruture.DataBase;

import com.AchadosPerdidos.API.Domain.Entity.DeviceToken;
import com.AchadosPerdidos.API.Infrastruture.DataBase.Interfaces.IDeviceTokenQueries;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class DeviceTokenQueries implements IDeviceTokenQueries {

    private final JdbcTemplate jdbcTemplate;
    
    public DeviceTokenQueries(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @NonNull
    private final RowMapper<DeviceToken> rowMapper = (rs, rowNum) -> {
        DeviceToken deviceToken = new DeviceToken();
        deviceToken.setId(rs.getInt("id"));
        deviceToken.setUsuario_id(rs.getInt("usuario_id"));
        deviceToken.setToken(rs.getString("token"));
        deviceToken.setPlataforma(rs.getString("plataforma"));
        
        Timestamp dtaCriacao = rs.getTimestamp("Dta_Criacao");
        if (dtaCriacao != null) {
            deviceToken.setDta_Criacao(dtaCriacao.toLocalDateTime());
        }
        
        Timestamp dtaAtualizacao = rs.getTimestamp("Dta_Atualizacao");
        if (dtaAtualizacao != null) {
            deviceToken.setDta_Atualizacao(dtaAtualizacao.toLocalDateTime());
        }
        
        deviceToken.setFlg_Inativo(rs.getBoolean("Flg_Inativo"));
        
        Timestamp dtaRemocao = rs.getTimestamp("Dta_Remocao");
        if (dtaRemocao != null) {
            deviceToken.setDta_Remocao(dtaRemocao.toLocalDateTime());
        }
        
        return deviceToken;
    };

    @Override
    public List<DeviceToken> findAll() {
        String sql = "SELECT * FROM ap_achados_perdidos.device_tokens ORDER BY Dta_Criacao DESC";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public DeviceToken findById(Integer id) {
        String sql = "SELECT * FROM ap_achados_perdidos.device_tokens WHERE id = ?";
        List<DeviceToken> deviceTokens = jdbcTemplate.query(sql, rowMapper, id);
        return deviceTokens.isEmpty() ? null : deviceTokens.get(0);
    }

    @Override
    public List<DeviceToken> findByUsuarioId(Integer usuarioId) {
        String sql = "SELECT * FROM ap_achados_perdidos.device_tokens WHERE usuario_id = ? ORDER BY Dta_Criacao DESC";
        return jdbcTemplate.query(sql, rowMapper, usuarioId);
    }

    @Override
    public DeviceToken findByUsuarioIdAndToken(Integer usuarioId, String token) {
        String sql = "SELECT * FROM ap_achados_perdidos.device_tokens WHERE usuario_id = ? AND token = ?";
        List<DeviceToken> deviceTokens = jdbcTemplate.query(sql, rowMapper, usuarioId, token);
        return deviceTokens.isEmpty() ? null : deviceTokens.get(0);
    }

    @Override
    public List<DeviceToken> findActiveTokensByUsuarioId(Integer usuarioId) {
        String sql = "SELECT * FROM ap_achados_perdidos.device_tokens WHERE usuario_id = ? AND Flg_Inativo = false ORDER BY Dta_Criacao DESC";
        return jdbcTemplate.query(sql, rowMapper, usuarioId);
    }

    @Override
    public DeviceToken insert(DeviceToken deviceToken) {
        String sql = "INSERT INTO ap_achados_perdidos.device_tokens (usuario_id, token, plataforma, Dta_Criacao, Flg_Inativo) VALUES (?, ?, ?, ?, ?)";
        
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, deviceToken.getUsuario_id());
            ps.setString(2, deviceToken.getToken());
            ps.setString(3, deviceToken.getPlataforma());
            ps.setTimestamp(4, deviceToken.getDta_Criacao() != null ? Timestamp.valueOf(deviceToken.getDta_Criacao()) : Timestamp.valueOf(LocalDateTime.now()));
            ps.setBoolean(5, deviceToken.getFlg_Inativo() != null ? deviceToken.getFlg_Inativo() : false);
            return ps;
        }, keyHolder);
        
        Number key = keyHolder.getKey();
        if (key != null) {
            deviceToken.setId(key.intValue());
        }
        
        return deviceToken;
    }

    @Override
    public DeviceToken update(DeviceToken deviceToken) {
        String sql = "UPDATE ap_achados_perdidos.device_tokens SET token = ?, plataforma = ?, Dta_Atualizacao = ?, Flg_Inativo = ?, Dta_Remocao = ? WHERE id = ?";
        jdbcTemplate.update(sql, 
            deviceToken.getToken(),
            deviceToken.getPlataforma(),
            deviceToken.getDta_Atualizacao() != null ? Timestamp.valueOf(deviceToken.getDta_Atualizacao()) : null,
            deviceToken.getFlg_Inativo(),
            deviceToken.getDta_Remocao() != null ? Timestamp.valueOf(deviceToken.getDta_Remocao()) : null,
            deviceToken.getId());
        
        return findById(deviceToken.getId());
    }

    @Override
    public boolean deleteById(Integer id) {
        String sql = "DELETE FROM ap_achados_perdidos.device_tokens WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, id);
        return rowsAffected > 0;
    }

    @Override
    public List<DeviceToken> findActive() {
        String sql = "SELECT * FROM ap_achados_perdidos.device_tokens WHERE Flg_Inativo = false ORDER BY Dta_Criacao DESC";
        return jdbcTemplate.query(sql, rowMapper);
    }
}

