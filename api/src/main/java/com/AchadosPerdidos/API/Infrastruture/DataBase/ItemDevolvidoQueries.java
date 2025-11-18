package com.AchadosPerdidos.API.Infrastruture.DataBase;

import com.AchadosPerdidos.API.Domain.Entity.ItemDevolvido;
import com.AchadosPerdidos.API.Infrastruture.DataBase.Interfaces.IItemDevolvidoQueries;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class ItemDevolvidoQueries implements IItemDevolvidoQueries {

    private final JdbcTemplate jdbcTemplate;
    
    public ItemDevolvidoQueries(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @NonNull
    private final RowMapper<ItemDevolvido> rowMapper = (rs, rowNum) -> {
        ItemDevolvido itemDevolvido = new ItemDevolvido();
        itemDevolvido.setId(rs.getInt("id"));
        itemDevolvido.setDetalhes_devolucao(rs.getString("detalhes_devolucao"));
        itemDevolvido.setItem_id(rs.getInt("item_id"));
        itemDevolvido.setUsuario_devolvedor_id(rs.getInt("usuario_devolvedor_id"));
        
        Integer usuarioAchouId = rs.getObject("usuario_achou_id", Integer.class);
        itemDevolvido.setUsuario_achou_id(usuarioAchouId);
        
        Timestamp dtaCriacao = rs.getTimestamp("Dta_Criacao");
        if (dtaCriacao != null) {
            itemDevolvido.setDtaCriacao(dtaCriacao.toLocalDateTime());
        }
        
        itemDevolvido.setFlgInativo(rs.getBoolean("Flg_Inativo"));
        
        Timestamp dtaRemocao = rs.getTimestamp("Dta_Remocao");
        if (dtaRemocao != null) {
            itemDevolvido.setDtaRemocao(dtaRemocao.toLocalDateTime());
        }
        
        return itemDevolvido;
    };

    @Override
    public List<ItemDevolvido> findAll() {
        String sql = "SELECT * FROM ap_achados_perdidos.itens_devolvido ORDER BY Dta_Criacao DESC";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public ItemDevolvido findById(Integer id) {
        String sql = "SELECT * FROM ap_achados_perdidos.itens_devolvido WHERE id = ?";
        List<ItemDevolvido> itens = jdbcTemplate.query(sql, rowMapper, id);
        return itens.isEmpty() ? null : itens.get(0);
    }

    @Override
    public ItemDevolvido insert(ItemDevolvido itemDevolvido) {
        String sql = "INSERT INTO ap_achados_perdidos.itens_devolvido (detalhes_devolucao, item_id, usuario_devolvedor_id, usuario_achou_id, Dta_Criacao, Flg_Inativo) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, 
            itemDevolvido.getDetalhes_devolucao(),
            itemDevolvido.getItem_id(),
            itemDevolvido.getUsuario_devolvedor_id(),
            itemDevolvido.getUsuario_achou_id(),
            itemDevolvido.getDtaCriacao() != null ? Timestamp.valueOf(itemDevolvido.getDtaCriacao()) : Timestamp.valueOf(LocalDateTime.now()),
            itemDevolvido.getFlgInativo() != null ? itemDevolvido.getFlgInativo() : false);
        
        // Buscar o registro inserido para retornar com o ID
        String selectSql = "SELECT * FROM ap_achados_perdidos.itens_devolvido WHERE item_id = ? AND usuario_devolvedor_id = ? AND Dta_Criacao = ? ORDER BY id DESC LIMIT 1";
        List<ItemDevolvido> inserted = jdbcTemplate.query(selectSql, rowMapper, 
            itemDevolvido.getItem_id(), 
            itemDevolvido.getUsuario_devolvedor_id(),
            itemDevolvido.getDtaCriacao() != null ? Timestamp.valueOf(itemDevolvido.getDtaCriacao()) : Timestamp.valueOf(LocalDateTime.now()));
        
        return inserted.isEmpty() ? null : inserted.get(0);
    }

    @Override
    public ItemDevolvido update(ItemDevolvido itemDevolvido) {
        String sql = "UPDATE ap_achados_perdidos.itens_devolvido SET detalhes_devolucao = ?, item_id = ?, usuario_devolvedor_id = ?, usuario_achou_id = ?, Flg_Inativo = ?, Dta_Remocao = ? WHERE id = ?";
        jdbcTemplate.update(sql, 
            itemDevolvido.getDetalhes_devolucao(),
            itemDevolvido.getItem_id(),
            itemDevolvido.getUsuario_devolvedor_id(),
            itemDevolvido.getUsuario_achou_id(),
            itemDevolvido.getFlgInativo(),
            itemDevolvido.getDtaRemocao() != null ? Timestamp.valueOf(itemDevolvido.getDtaRemocao()) : null,
            itemDevolvido.getId());
        
        return findById(itemDevolvido.getId());
    }

    @Override
    public boolean deleteById(Integer id) {
        String sql = "DELETE FROM ap_achados_perdidos.itens_devolvido WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, id);
        return rowsAffected > 0;
    }

    @Override
    public List<ItemDevolvido> findActive() {
        String sql = "SELECT * FROM ap_achados_perdidos.itens_devolvido WHERE Flg_Inativo = false ORDER BY Dta_Criacao DESC";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public List<ItemDevolvido> findByItemId(Integer itemId) {
        String sql = "SELECT * FROM ap_achados_perdidos.itens_devolvido WHERE item_id = ? ORDER BY Dta_Criacao DESC";
        return jdbcTemplate.query(sql, rowMapper, itemId);
    }

    @Override
    public List<ItemDevolvido> findByUsuarioDevolvedorId(Integer usuarioId) {
        String sql = "SELECT * FROM ap_achados_perdidos.itens_devolvido WHERE usuario_devolvedor_id = ? ORDER BY Dta_Criacao DESC";
        return jdbcTemplate.query(sql, rowMapper, usuarioId);
    }
}

