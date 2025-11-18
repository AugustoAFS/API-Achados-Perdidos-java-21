package com.AchadosPerdidos.API.Infrastruture.DataBase;

import com.AchadosPerdidos.API.Domain.Entity.ItemPerdido;
import com.AchadosPerdidos.API.Infrastruture.DataBase.Interfaces.IItemPerdidoQueries;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class ItemPerdidoQueries implements IItemPerdidoQueries {

    private final JdbcTemplate jdbcTemplate;
    
    public ItemPerdidoQueries(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @NonNull
    private final RowMapper<ItemPerdido> rowMapper = (rs, rowNum) -> {
        ItemPerdido itemPerdido = new ItemPerdido();
        itemPerdido.setId(rs.getInt("id"));
        
        Timestamp perdidoEm = rs.getTimestamp("perdido_em");
        if (perdidoEm != null) {
            itemPerdido.setPerdido_em(perdidoEm.toLocalDateTime());
        }
        
        Timestamp dtaCriacao = rs.getTimestamp("Dta_Criacao");
        if (dtaCriacao != null) {
            itemPerdido.setDta_Criacao(dtaCriacao.toLocalDateTime());
        }
        
        itemPerdido.setFlg_Inativo(rs.getBoolean("Flg_Inativo"));
        
        Timestamp dtaRemocao = rs.getTimestamp("Dta_Remocao");
        if (dtaRemocao != null) {
            itemPerdido.setDta_Remocao(dtaRemocao.toLocalDateTime());
        }
        
        return itemPerdido;
    };

    @Override
    public List<ItemPerdido> findAll() {
        String sql = "SELECT * FROM ap_achados_perdidos.itens_perdidos ORDER BY perdido_em DESC";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public ItemPerdido findById(Integer id) {
        String sql = "SELECT * FROM ap_achados_perdidos.itens_perdidos WHERE id = ?";
        List<ItemPerdido> itens = jdbcTemplate.query(sql, rowMapper, id);
        return itens.isEmpty() ? null : itens.get(0);
    }

    @Override
    public ItemPerdido insert(ItemPerdido itemPerdido) {
        String sql = "INSERT INTO ap_achados_perdidos.itens_perdidos (id, perdido_em, Dta_Criacao, Flg_Inativo) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, 
            itemPerdido.getId(),
            itemPerdido.getPerdido_em() != null ? Timestamp.valueOf(itemPerdido.getPerdido_em()) : Timestamp.valueOf(LocalDateTime.now()),
            itemPerdido.getDta_Criacao() != null ? Timestamp.valueOf(itemPerdido.getDta_Criacao()) : Timestamp.valueOf(LocalDateTime.now()),
            itemPerdido.getFlg_Inativo() != null ? itemPerdido.getFlg_Inativo() : false);
        
        return findById(itemPerdido.getId());
    }

    @Override
    public ItemPerdido update(ItemPerdido itemPerdido) {
        String sql = "UPDATE ap_achados_perdidos.itens_perdidos SET perdido_em = ?, Flg_Inativo = ?, Dta_Remocao = ? WHERE id = ?";
        jdbcTemplate.update(sql, 
            itemPerdido.getPerdido_em() != null ? Timestamp.valueOf(itemPerdido.getPerdido_em()) : null,
            itemPerdido.getFlg_Inativo(),
            itemPerdido.getDta_Remocao() != null ? Timestamp.valueOf(itemPerdido.getDta_Remocao()) : null,
            itemPerdido.getId());
        
        return findById(itemPerdido.getId());
    }

    @Override
    public boolean deleteById(Integer id) {
        String sql = "DELETE FROM ap_achados_perdidos.itens_perdidos WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, id);
        return rowsAffected > 0;
    }

    @Override
    public List<ItemPerdido> findActive() {
        String sql = "SELECT * FROM ap_achados_perdidos.itens_perdidos WHERE Flg_Inativo = false ORDER BY perdido_em DESC";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public ItemPerdido findByItemId(Integer itemId) {
        String sql = "SELECT * FROM ap_achados_perdidos.itens_perdidos WHERE id = ?";
        List<ItemPerdido> itens = jdbcTemplate.query(sql, rowMapper, itemId);
        return itens.isEmpty() ? null : itens.get(0);
    }
}

