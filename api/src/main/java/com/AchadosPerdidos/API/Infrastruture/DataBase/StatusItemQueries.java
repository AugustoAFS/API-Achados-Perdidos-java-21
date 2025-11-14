package com.AchadosPerdidos.API.Infrastruture.DataBase;

import com.AchadosPerdidos.API.Domain.Entity.StatusItem;
import com.AchadosPerdidos.API.Infrastruture.DataBase.Interfaces.IStatusItemQueries;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StatusItemQueries implements IStatusItemQueries {

    private final JdbcTemplate jdbcTemplate;
    public StatusItemQueries(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @NonNull
    private final RowMapper<StatusItem> rowMapper = (rs, rowNum) -> {
        var entity = new StatusItem();
        entity.setId(rs.getInt("id"));
        entity.setNome(rs.getString("nome"));
        entity.setDescricao(rs.getString("descricao"));
        entity.setStatusItem(rs.getString("status_item"));
        entity.setDtaCriacao(rs.getTimestamp("Dta_Criacao"));
        entity.setFlgInativo(rs.getBoolean("Flg_Inativo"));
        entity.setDtaRemocao(rs.getTimestamp("Dta_Remocao"));
        return entity;
    };

    @Override
    public List<StatusItem> findAll() {
        String sql = "SELECT * FROM ap_achados_perdidos.status_item ORDER BY nome";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public StatusItem findById(Integer id) {
        String sql = "SELECT * FROM ap_achados_perdidos.status_item WHERE id = ?";
        List<StatusItem> statusItems = jdbcTemplate.query(sql, rowMapper, id);
        return statusItems.isEmpty() ? null : statusItems.get(0);
    }

    @Override
    public StatusItem findByNome(String nome) {
        String sql = "SELECT * FROM ap_achados_perdidos.status_item WHERE nome = ?";
        List<StatusItem> statusItems = jdbcTemplate.query(sql, rowMapper, nome);
        return statusItems.isEmpty() ? null : statusItems.get(0);
    }

    @Override
    public StatusItem insert(StatusItem statusItem) {
        String sql = "INSERT INTO ap_achados_perdidos.status_item (nome, descricao, status_item, Dta_Criacao, Flg_Inativo) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
            statusItem.getNome(),
            statusItem.getDescricao(),
            statusItem.getStatusItem(),
            statusItem.getDtaCriacao(),
            statusItem.getFlgInativo());

        String selectSql = "SELECT * FROM ap_achados_perdidos.status_item WHERE nome = ? AND Dta_Criacao = ? ORDER BY id DESC LIMIT 1";
        List<StatusItem> inserted = jdbcTemplate.query(selectSql, rowMapper,
            statusItem.getNome(),
            statusItem.getDtaCriacao());

        return inserted.isEmpty() ? null : inserted.get(0);
    }

    @Override
    public StatusItem update(StatusItem statusItem) {
        String sql = "UPDATE ap_achados_perdidos.status_item SET nome = ?, descricao = ?, status_item = ?, Flg_Inativo = ?, Dta_Remocao = ? WHERE id = ?";
        jdbcTemplate.update(sql,
            statusItem.getNome(),
            statusItem.getDescricao(),
            statusItem.getStatusItem(),
            statusItem.getFlgInativo(),
            statusItem.getDtaRemocao(),
            statusItem.getId());

        return findById(statusItem.getId());
    }

    @Override
    public boolean deleteById(Integer id) {
        String sql = "DELETE FROM ap_achados_perdidos.status_item WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, id);
        return rowsAffected > 0;
    }

    @Override
    public List<StatusItem> findActive() {
        String sql = "SELECT * FROM ap_achados_perdidos.status_item WHERE Flg_Inativo = false ORDER BY nome";
        return jdbcTemplate.query(sql, rowMapper);
    }
}

