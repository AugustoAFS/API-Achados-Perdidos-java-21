package com.AchadosPerdidos.API.Infrastruture.DataBase;

import com.AchadosPerdidos.API.Domain.Entity.Cidade;
import com.AchadosPerdidos.API.Infrastruture.DataBase.Interfaces.ICidadeQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CidadeQueries implements ICidadeQueries {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Cidade> rowMapper = (rs, rowNum) -> {
        Cidade cidade = new Cidade();
        cidade.setId(rs.getInt("id"));
        cidade.setNome(rs.getString("nome"));
        cidade.setEstadoId(rs.getInt("estado_id"));
        cidade.setDtaCriacao(rs.getTimestamp("Dta_Criacao"));
        cidade.setFlgInativo(rs.getBoolean("Flg_Inativo"));
        cidade.setDtaRemocao(rs.getTimestamp("Dta_Remocao"));
        return cidade;
    };

    @Override
    public List<Cidade> findAll() {
        String sql = "SELECT * FROM ap_achados_perdidos.cidades ORDER BY nome";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public Cidade findById(Integer id) {
        String sql = "SELECT * FROM ap_achados_perdidos.cidades WHERE id = ?";
        List<Cidade> cidades = jdbcTemplate.query(sql, rowMapper, id);
        return cidades.isEmpty() ? null : cidades.get(0);
    }

    @Override
    public Cidade insert(Cidade cidade) {
        String sql = "INSERT INTO ap_achados_perdidos.cidades (nome, estado_id, Dta_Criacao, Flg_Inativo) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql,
            cidade.getNome(),
            cidade.getEstadoId(),
            cidade.getDtaCriacao(),
            cidade.getFlgInativo());

        String selectSql = "SELECT * FROM ap_achados_perdidos.cidades WHERE nome = ? AND estado_id = ? AND Dta_Criacao = ? ORDER BY id DESC LIMIT 1";
        List<Cidade> inserted = jdbcTemplate.query(selectSql, rowMapper,
            cidade.getNome(),
            cidade.getEstadoId(),
            cidade.getDtaCriacao());

        return inserted.isEmpty() ? null : inserted.get(0);
    }

    @Override
    public Cidade update(Cidade cidade) {
        String sql = "UPDATE ap_achados_perdidos.cidades SET nome = ?, estado_id = ?, Flg_Inativo = ?, Dta_Remocao = ? WHERE id = ?";
        jdbcTemplate.update(sql,
            cidade.getNome(),
            cidade.getEstadoId(),
            cidade.getFlgInativo(),
            cidade.getDtaRemocao(),
            cidade.getId());

        return findById(cidade.getId());
    }

    @Override
    public boolean deleteById(Integer id) {
        String sql = "DELETE FROM ap_achados_perdidos.cidades WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, id);
        return rowsAffected > 0;
    }

    @Override
    public List<Cidade> findActive() {
        String sql = "SELECT * FROM ap_achados_perdidos.cidades WHERE Flg_Inativo = false ORDER BY nome";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public List<Cidade> findByEstado(Integer estadoId) {
        String sql = "SELECT * FROM ap_achados_perdidos.cidades WHERE estado_id = ? AND Flg_Inativo = false ORDER BY nome";
        return jdbcTemplate.query(sql, rowMapper, estadoId);
    }
}

