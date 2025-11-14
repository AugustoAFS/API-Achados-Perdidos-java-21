package com.AchadosPerdidos.API.Infrastruture.DataBase;

import com.AchadosPerdidos.API.Domain.Entity.Campus;
import com.AchadosPerdidos.API.Infrastruture.DataBase.Interfaces.ICampusQueries;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CampusQueries implements ICampusQueries {

    private final JdbcTemplate jdbcTemplate;
    public CampusQueries(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @NonNull
    private final RowMapper<Campus> rowMapper = (rs, rowNum) -> {
        Campus campus = new Campus();
        campus.setId(rs.getInt("id"));
        campus.setNome(rs.getString("nome"));
        campus.setInstituicaoId(rs.getInt("instituicao_id"));
        campus.setEnderecoId(rs.getInt("endereco_id"));
        campus.setDtaCriacao(rs.getTimestamp("Dta_Criacao"));
        campus.setFlgInativo(rs.getBoolean("Flg_Inativo"));
        campus.setDtaRemocao(rs.getTimestamp("Dta_Remocao"));
        return campus;
    };

    @Override
    public List<Campus> findAll() {
        String sql = "SELECT * FROM ap_achados_perdidos.campus ORDER BY nome";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public Campus findById(int id) {
        String sql = "SELECT * FROM ap_achados_perdidos.campus WHERE id = ?";
        List<Campus> campus = jdbcTemplate.query(sql, rowMapper, id);
        return campus.isEmpty() ? null : campus.get(0);
    }

    @Override
    public Campus insert(Campus campus) {
        String sql = "INSERT INTO ap_achados_perdidos.campus (nome, instituicao_id, endereco_id, Dta_Criacao, Flg_Inativo) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, 
            campus.getNome(),
            campus.getInstituicaoId(),
            campus.getEnderecoId(),
            campus.getDtaCriacao(),
            campus.getFlgInativo());
        
        // Buscar o registro inserido para retornar com o ID
        String selectSql = "SELECT * FROM ap_achados_perdidos.campus WHERE nome = ? AND Dta_Criacao = ? ORDER BY id DESC LIMIT 1";
        List<Campus> inserted = jdbcTemplate.query(selectSql, rowMapper, 
            campus.getNome(), 
            campus.getDtaCriacao());
        
        return inserted.isEmpty() ? null : inserted.get(0);
    }

    @Override
    public Campus update(Campus campus) {
        String sql = "UPDATE ap_achados_perdidos.campus SET nome = ?, instituicao_id = ?, endereco_id = ?, Flg_Inativo = ?, Dta_Remocao = ? WHERE id = ?";
        jdbcTemplate.update(sql, 
            campus.getNome(),
            campus.getInstituicaoId(),
            campus.getEnderecoId(),
            campus.getFlgInativo(),
            campus.getDtaRemocao(),
            campus.getId());
        
        return findById(campus.getId());
    }

    @Override
    public boolean deleteById(int id) {
        String sql = "DELETE FROM ap_achados_perdidos.campus WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, id);
        return rowsAffected > 0;
    }

    @Override
    public List<Campus> findActive() {
        String sql = "SELECT * FROM ap_achados_perdidos.campus WHERE Flg_Inativo = false ORDER BY nome";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public List<Campus> findByInstitution(int institutionId) {
        String sql = "SELECT * FROM ap_achados_perdidos.campus WHERE instituicao_id = ? ORDER BY nome";
        return jdbcTemplate.query(sql, rowMapper, institutionId);
    }
}
