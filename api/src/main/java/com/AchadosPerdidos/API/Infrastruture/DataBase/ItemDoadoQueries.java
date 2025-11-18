package com.AchadosPerdidos.API.Infrastruture.DataBase;

import com.AchadosPerdidos.API.Domain.Entity.ItemDoado;
import com.AchadosPerdidos.API.Infrastruture.DataBase.Interfaces.IItemDoadoQueries;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class ItemDoadoQueries implements IItemDoadoQueries {

    private final JdbcTemplate jdbcTemplate;
    
    public ItemDoadoQueries(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @NonNull
    private final RowMapper<ItemDoado> rowMapper = (rs, rowNum) -> {
        ItemDoado itemDoado = new ItemDoado();
        itemDoado.setId(rs.getInt("id"));
        
        Timestamp doadoEm = rs.getTimestamp("doado_em");
        if (doadoEm != null) {
            itemDoado.setDoado_em(doadoEm.toLocalDateTime());
        }
        
        Timestamp dtaCriacao = rs.getTimestamp("Dta_Criacao");
        if (dtaCriacao != null) {
            itemDoado.setDta_Criacao(dtaCriacao.toLocalDateTime());
        }
        
        itemDoado.setFlg_Inativo(rs.getBoolean("Flg_Inativo"));
        
        Timestamp dtaRemocao = rs.getTimestamp("Dta_Remocao");
        if (dtaRemocao != null) {
            itemDoado.setDta_Remocao(dtaRemocao.toLocalDateTime());
        }
        
        return itemDoado;
    };

    @Override
    public List<ItemDoado> findAll() {
        String sql = "SELECT * FROM ap_achados_perdidos.itens_doados ORDER BY doado_em DESC";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public ItemDoado findById(Integer id) {
        String sql = "SELECT * FROM ap_achados_perdidos.itens_doados WHERE id = ?";
        List<ItemDoado> itens = jdbcTemplate.query(sql, rowMapper, id);
        return itens.isEmpty() ? null : itens.get(0);
    }

    @Override
    public ItemDoado insert(ItemDoado itemDoado) {
        String sql = "INSERT INTO ap_achados_perdidos.itens_doados (id, doado_em, Dta_Criacao, Flg_Inativo) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, 
            itemDoado.getId(),
            itemDoado.getDoado_em() != null ? Timestamp.valueOf(itemDoado.getDoado_em()) : Timestamp.valueOf(LocalDateTime.now()),
            itemDoado.getDta_Criacao() != null ? Timestamp.valueOf(itemDoado.getDta_Criacao()) : Timestamp.valueOf(LocalDateTime.now()),
            itemDoado.getFlg_Inativo() != null ? itemDoado.getFlg_Inativo() : false);
        
        return findById(itemDoado.getId());
    }

    @Override
    public ItemDoado update(ItemDoado itemDoado) {
        String sql = "UPDATE ap_achados_perdidos.itens_doados SET doado_em = ?, Flg_Inativo = ?, Dta_Remocao = ? WHERE id = ?";
        jdbcTemplate.update(sql, 
            itemDoado.getDoado_em() != null ? Timestamp.valueOf(itemDoado.getDoado_em()) : null,
            itemDoado.getFlg_Inativo(),
            itemDoado.getDta_Remocao() != null ? Timestamp.valueOf(itemDoado.getDta_Remocao()) : null,
            itemDoado.getId());
        
        return findById(itemDoado.getId());
    }

    @Override
    public boolean deleteById(Integer id) {
        String sql = "DELETE FROM ap_achados_perdidos.itens_doados WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, id);
        return rowsAffected > 0;
    }

    @Override
    public List<ItemDoado> findActive() {
        String sql = "SELECT * FROM ap_achados_perdidos.itens_doados WHERE Flg_Inativo = false ORDER BY doado_em DESC";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public ItemDoado findByItemId(Integer itemId) {
        String sql = "SELECT * FROM ap_achados_perdidos.itens_doados WHERE id = ?";
        List<ItemDoado> itens = jdbcTemplate.query(sql, rowMapper, itemId);
        return itens.isEmpty() ? null : itens.get(0);
    }
}

