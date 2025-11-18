package com.AchadosPerdidos.API.Infrastruture.DataBase;

import com.AchadosPerdidos.API.Domain.Entity.ItemAchado;
import com.AchadosPerdidos.API.Infrastruture.DataBase.Interfaces.IItemAchadoQueries;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class ItemAchadoQueries implements IItemAchadoQueries {

    private final JdbcTemplate jdbcTemplate;
    
    public ItemAchadoQueries(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @NonNull
    private final RowMapper<ItemAchado> rowMapper = (rs, rowNum) -> {
        ItemAchado itemAchado = new ItemAchado();
        itemAchado.setId(rs.getInt("id"));
        
        Timestamp encontradoEm = rs.getTimestamp("encontrado_em");
        if (encontradoEm != null) {
            itemAchado.setEncontrado_em(encontradoEm.toLocalDateTime());
        }
        
        Timestamp dtaCriacao = rs.getTimestamp("Dta_Criacao");
        if (dtaCriacao != null) {
            itemAchado.setDtaCriacao(dtaCriacao.toLocalDateTime());
        }
        
        itemAchado.setFlgInativo(rs.getBoolean("Flg_Inativo"));
        
        Timestamp dtaRemocao = rs.getTimestamp("Dta_Remocao");
        if (dtaRemocao != null) {
            itemAchado.setDtaRemocao(dtaRemocao.toLocalDateTime());
        }
        
        return itemAchado;
    };

    @Override
    public List<ItemAchado> findAll() {
        String sql = "SELECT * FROM ap_achados_perdidos.itens_achados ORDER BY encontrado_em DESC";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public ItemAchado findById(Integer id) {
        String sql = "SELECT * FROM ap_achados_perdidos.itens_achados WHERE id = ?";
        List<ItemAchado> itens = jdbcTemplate.query(sql, rowMapper, id);
        return itens.isEmpty() ? null : itens.get(0);
    }

    @Override
    public ItemAchado insert(ItemAchado itemAchado) {
        String sql = "INSERT INTO ap_achados_perdidos.itens_achados (id, encontrado_em, Dta_Criacao, Flg_Inativo) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, 
            itemAchado.getId(),
            itemAchado.getEncontrado_em() != null ? Timestamp.valueOf(itemAchado.getEncontrado_em()) : Timestamp.valueOf(LocalDateTime.now()),
            itemAchado.getDtaCriacao() != null ? Timestamp.valueOf(itemAchado.getDtaCriacao()) : Timestamp.valueOf(LocalDateTime.now()),
            itemAchado.getFlgInativo() != null ? itemAchado.getFlgInativo() : false);
        
        return findById(itemAchado.getId());
    }

    @Override
    public ItemAchado update(ItemAchado itemAchado) {
        String sql = "UPDATE ap_achados_perdidos.itens_achados SET encontrado_em = ?, Flg_Inativo = ?, Dta_Remocao = ? WHERE id = ?";
        jdbcTemplate.update(sql, 
            itemAchado.getEncontrado_em() != null ? Timestamp.valueOf(itemAchado.getEncontrado_em()) : null,
            itemAchado.getFlgInativo(),
            itemAchado.getDtaRemocao() != null ? Timestamp.valueOf(itemAchado.getDtaRemocao()) : null,
            itemAchado.getId());
        
        return findById(itemAchado.getId());
    }

    @Override
    public boolean deleteById(Integer id) {
        String sql = "DELETE FROM ap_achados_perdidos.itens_achados WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, id);
        return rowsAffected > 0;
    }

    @Override
    public List<ItemAchado> findActive() {
        String sql = "SELECT * FROM ap_achados_perdidos.itens_achados WHERE Flg_Inativo = false ORDER BY encontrado_em DESC";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public ItemAchado findByItemId(Integer itemId) {
        String sql = "SELECT * FROM ap_achados_perdidos.itens_achados WHERE id = ?";
        List<ItemAchado> itens = jdbcTemplate.query(sql, rowMapper, itemId);
        return itens.isEmpty() ? null : itens.get(0);
    }
}

