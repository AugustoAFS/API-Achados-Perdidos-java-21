package com.AchadosPerdidos.API.Infrastruture.DataBase;

import com.AchadosPerdidos.API.Domain.Entity.Estado;
import com.AchadosPerdidos.API.Infrastruture.DataBase.Interfaces.IEstadoQueries;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class EstadoQueries implements IEstadoQueries {

    private final JdbcTemplate jdbcTemplate;
    public EstadoQueries(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @NonNull
    private final RowMapper<Estado> rowMapper = (rs, rowNum) -> {
        Estado estado = new Estado();
        estado.setId(rs.getInt("id"));
        estado.setNome(rs.getString("nome"));
        estado.setUf(rs.getString("uf"));
        
        Timestamp dtaCriacao = rs.getTimestamp("Dta_Criacao");
        if (dtaCriacao != null) {
            estado.setDtaCriacao(dtaCriacao.toLocalDateTime());
        }
        
        estado.setFlgInativo(rs.getBoolean("Flg_Inativo"));
        
        Timestamp dtaRemocao = rs.getTimestamp("Dta_Remocao");
        if (dtaRemocao != null) {
            estado.setDtaRemocao(dtaRemocao.toLocalDateTime());
        }
        
        return estado;
    };

    @Override
    public List<Estado> findAll() {
        String sql = "SELECT * FROM ap_achados_perdidos.estados ORDER BY nome";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public Estado findById(Integer id) {
        String sql = "SELECT * FROM ap_achados_perdidos.estados WHERE id = ?";
        List<Estado> estados = jdbcTemplate.query(sql, rowMapper, id);
        return estados.isEmpty() ? null : estados.get(0);
    }

    @Override
    public Estado findByUf(String uf) {
        String sql = "SELECT * FROM ap_achados_perdidos.estados WHERE uf = ?";
        List<Estado> estados = jdbcTemplate.query(sql, rowMapper, uf);
        return estados.isEmpty() ? null : estados.get(0);
    }

    @Override
    public Estado insert(Estado estado) {
        String sql = "INSERT INTO ap_achados_perdidos.estados (nome, uf, Dta_Criacao, Flg_Inativo) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql,
            estado.getNome(),
            estado.getUf(),
            estado.getDtaCriacao() != null ? Timestamp.valueOf(estado.getDtaCriacao()) : Timestamp.valueOf(LocalDateTime.now()),
            estado.getFlgInativo() != null ? estado.getFlgInativo() : false);

        String selectSql = "SELECT * FROM ap_achados_perdidos.estados WHERE uf = ? AND Dta_Criacao = ? ORDER BY id DESC LIMIT 1";
        List<Estado> inserted = jdbcTemplate.query(selectSql, rowMapper,
            estado.getUf(),
            estado.getDtaCriacao() != null ? Timestamp.valueOf(estado.getDtaCriacao()) : Timestamp.valueOf(LocalDateTime.now()));

        return inserted.isEmpty() ? null : inserted.get(0);
    }

    @Override
    public Estado update(Estado estado) {
        String sql = "UPDATE ap_achados_perdidos.estados SET nome = ?, uf = ?, Flg_Inativo = ?, Dta_Remocao = ? WHERE id = ?";
        jdbcTemplate.update(sql,
            estado.getNome(),
            estado.getUf(),
            estado.getFlgInativo(),
            estado.getDtaRemocao() != null ? Timestamp.valueOf(estado.getDtaRemocao()) : null,
            estado.getId());

        return findById(estado.getId());
    }

    @Override
    public boolean deleteById(Integer id) {
        String sql = "DELETE FROM ap_achados_perdidos.estados WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, id);
        return rowsAffected > 0;
    }

    @Override
    public List<Estado> findActive() {
        String sql = "SELECT * FROM ap_achados_perdidos.estados WHERE Flg_Inativo = false ORDER BY nome";
        return jdbcTemplate.query(sql, rowMapper);
    }
}

