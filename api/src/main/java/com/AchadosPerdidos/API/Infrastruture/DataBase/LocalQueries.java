package com.AchadosPerdidos.API.Infrastruture.DataBase;

import com.AchadosPerdidos.API.Domain.Entity.Local;
import com.AchadosPerdidos.API.Infrastruture.DataBase.Interfaces.ILocalQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LocalQueries implements ILocalQueries {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Local> rowMapper = (rs, rowNum) -> {
        Local local = new Local();
        local.setId(rs.getInt("id"));
        local.setNome(rs.getString("nome"));
        local.setDescricao(rs.getString("descricao"));
        local.setCampusId(rs.getInt("campus_id"));
        local.setDtaCriacao(rs.getTimestamp("Dta_Criacao"));
        local.setFlgInativo(rs.getBoolean("Flg_Inativo"));
        local.setDtaRemocao(rs.getTimestamp("Dta_Remocao"));
        return local;
    };

    @Override
    public List<Local> findAll() {
        String sql = "SELECT * FROM ap_achados_perdidos.locais ORDER BY nome";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public Local findById(Integer id) {
        String sql = "SELECT * FROM ap_achados_perdidos.locais WHERE id = ?";
        List<Local> locais = jdbcTemplate.query(sql, rowMapper, id);
        return locais.isEmpty() ? null : locais.get(0);
    }

    @Override
    public Local insert(Local local) {
        String sql = "INSERT INTO ap_achados_perdidos.locais (nome, descricao, campus_id, Dta_Criacao, Flg_Inativo) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
            local.getNome(),
            local.getDescricao(),
            local.getCampusId(),
            local.getDtaCriacao(),
            local.getFlgInativo());

        String selectSql = "SELECT * FROM ap_achados_perdidos.locais WHERE nome = ? AND Dta_Criacao = ? ORDER BY id DESC LIMIT 1";
        List<Local> inserted = jdbcTemplate.query(selectSql, rowMapper,
            local.getNome(),
            local.getDtaCriacao());

        return inserted.isEmpty() ? null : inserted.get(0);
    }

    @Override
    public Local update(Local local) {
        String sql = "UPDATE ap_achados_perdidos.locais SET nome = ?, descricao = ?, campus_id = ?, Flg_Inativo = ?, Dta_Remocao = ? WHERE id = ?";
        jdbcTemplate.update(sql,
            local.getNome(),
            local.getDescricao(),
            local.getCampusId(),
            local.getFlgInativo(),
            local.getDtaRemocao(),
            local.getId());

        return findById(local.getId());
    }

    @Override
    public boolean deleteById(Integer id) {
        String sql = "DELETE FROM ap_achados_perdidos.locais WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, id);
        return rowsAffected > 0;
    }

    @Override
    public List<Local> findActive() {
        String sql = "SELECT * FROM ap_achados_perdidos.locais WHERE Flg_Inativo = false ORDER BY nome";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public List<Local> findByCampus(Integer campusId) {
        String sql = "SELECT * FROM ap_achados_perdidos.locais WHERE campus_id = ? AND Flg_Inativo = false ORDER BY nome";
        return jdbcTemplate.query(sql, rowMapper, campusId);
    }
}

