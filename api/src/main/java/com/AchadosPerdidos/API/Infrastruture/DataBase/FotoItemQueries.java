package com.AchadosPerdidos.API.Infrastruture.DataBase;

import com.AchadosPerdidos.API.Domain.Entity.FotoItem;
import com.AchadosPerdidos.API.Infrastruture.DataBase.Interfaces.IFotoItemQueries;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class FotoItemQueries implements IFotoItemQueries {

    private final JdbcTemplate jdbcTemplate;
    
    public FotoItemQueries(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @NonNull
    private final RowMapper<FotoItem> rowMapper = (rs, rowNum) -> {
        FotoItem fotoItem = new FotoItem();
        fotoItem.setId(rs.getInt("id"));
        fotoItem.setItemId(rs.getInt("item_id"));
        fotoItem.setFotoId(rs.getInt("foto_id"));
        
        Timestamp dtaCriacao = rs.getTimestamp("Dta_Criacao");
        if (dtaCriacao != null) {
            fotoItem.setDtaCriacao(dtaCriacao.toLocalDateTime());
        }
        
        fotoItem.setFlgInativo(rs.getBoolean("Flg_Inativo"));
        
        // FotoItem não tem setDtaRemocao na entidade, apenas getter
        // Dta_Remocao é lido mas não pode ser setado
        
        return fotoItem;
    };

    @Override
    public List<FotoItem> findAll() {
        String sql = "SELECT * FROM ap_achados_perdidos.fotos_item ORDER BY Dta_Criacao DESC";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public FotoItem findByItemIdAndFotoId(Integer itemId, Integer fotoId) {
        String sql = "SELECT * FROM ap_achados_perdidos.fotos_item WHERE item_id = ? AND foto_id = ?";
        List<FotoItem> fotos = jdbcTemplate.query(sql, rowMapper, itemId, fotoId);
        return fotos.isEmpty() ? null : fotos.get(0);
    }

    @Override
    public List<FotoItem> findByItemId(Integer itemId) {
        String sql = "SELECT * FROM ap_achados_perdidos.fotos_item WHERE item_id = ? ORDER BY Dta_Criacao DESC";
        return jdbcTemplate.query(sql, rowMapper, itemId);
    }

    @Override
    public List<FotoItem> findByFotoId(Integer fotoId) {
        String sql = "SELECT * FROM ap_achados_perdidos.fotos_item WHERE foto_id = ? ORDER BY Dta_Criacao DESC";
        return jdbcTemplate.query(sql, rowMapper, fotoId);
    }

    @Override
    public FotoItem insert(FotoItem fotoItem) {
        String sql = "INSERT INTO ap_achados_perdidos.fotos_item (item_id, foto_id, Dta_Criacao, Flg_Inativo) VALUES (?, ?, ?, ?)";
        
        org.springframework.jdbc.support.KeyHolder keyHolder = new org.springframework.jdbc.support.GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            java.sql.PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setInt(1, fotoItem.getItemId());
            ps.setInt(2, fotoItem.getFotoId());
            ps.setTimestamp(3, fotoItem.getDtaCriacao() != null ? Timestamp.valueOf(fotoItem.getDtaCriacao()) : Timestamp.valueOf(LocalDateTime.now()));
            ps.setBoolean(4, fotoItem.getFlgInativo() != null ? fotoItem.getFlgInativo() : false);
            return ps;
        }, keyHolder);
        
        Number key = keyHolder.getKey();
        if (key != null) {
            fotoItem.setId(key.intValue());
        }
        
        return fotoItem;
    }

    @Override
    public FotoItem update(FotoItem fotoItem) {
        String sql = "UPDATE ap_achados_perdidos.fotos_item SET item_id = ?, foto_id = ?, Flg_Inativo = ? WHERE id = ?";
        jdbcTemplate.update(sql, 
            fotoItem.getItemId(),
            fotoItem.getFotoId(),
            fotoItem.getFlgInativo(),
            fotoItem.getId());
        
        return findById(fotoItem.getId());
    }
    
    public FotoItem findById(Integer id) {
        String sql = "SELECT * FROM ap_achados_perdidos.fotos_item WHERE id = ?";
        List<FotoItem> fotos = jdbcTemplate.query(sql, rowMapper, id);
        return fotos.isEmpty() ? null : fotos.get(0);
    }

    @Override
    public boolean deleteByItemIdAndFotoId(Integer itemId, Integer fotoId) {
        String sql = "DELETE FROM ap_achados_perdidos.fotos_item WHERE item_id = ? AND foto_id = ?";
        int rowsAffected = jdbcTemplate.update(sql, itemId, fotoId);
        return rowsAffected > 0;
    }

    @Override
    public List<FotoItem> findActive() {
        String sql = "SELECT * FROM ap_achados_perdidos.fotos_item WHERE Flg_Inativo = false ORDER BY Dta_Criacao DESC";
        return jdbcTemplate.query(sql, rowMapper);
    }
}

