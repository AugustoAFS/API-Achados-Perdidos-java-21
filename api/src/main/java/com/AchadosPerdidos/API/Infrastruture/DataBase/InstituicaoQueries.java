package com.AchadosPerdidos.API.Infrastruture.DataBase;

import com.AchadosPerdidos.API.Domain.Entity.Instituicao;
import com.AchadosPerdidos.API.Infrastruture.DataBase.Interfaces.IInstituicaoQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InstituicaoQueries implements IInstituicaoQueries {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Instituicao> rowMapper = (rs, rowNum) -> {
        Instituicao instituicao = new Instituicao();
        instituicao.setId(rs.getInt("id"));
        instituicao.setNome(rs.getString("nome"));
        instituicao.setCodigo(rs.getString("codigo"));
        instituicao.setTipo(rs.getString("tipo"));
        instituicao.setCnpj(rs.getString("cnpj"));
        instituicao.setDtaCriacao(rs.getTimestamp("Dta_Criacao"));
        instituicao.setFlgInativo(rs.getBoolean("Flg_Inativo"));
        instituicao.setDtaRemocao(rs.getTimestamp("Dta_Remocao"));
        return instituicao;
    };

    @Override
    public List<Instituicao> findAll() {
        String sql = "SELECT * FROM ap_achados_perdidos.instituicoes ORDER BY nome";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public Instituicao findById(int id) {
        String sql = "SELECT * FROM ap_achados_perdidos.instituicoes WHERE id = ?";
        List<Instituicao> instituicoes = jdbcTemplate.query(sql, rowMapper, id);
        return instituicoes.isEmpty() ? null : instituicoes.get(0);
    }

    @Override
    public Instituicao insert(Instituicao instituicao) {
        String sql = "INSERT INTO ap_achados_perdidos.instituicoes (nome, codigo, tipo, cnpj, Dta_Criacao, Flg_Inativo) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, 
            instituicao.getNome(),
            instituicao.getCodigo(),
            instituicao.getTipo(),
            instituicao.getCnpj(),
            instituicao.getDtaCriacao(),
            instituicao.getFlgInativo());
        
        // Buscar o registro inserido para retornar com o ID
        String selectSql = "SELECT * FROM ap_achados_perdidos.instituicoes WHERE nome = ? AND Dta_Criacao = ? ORDER BY id DESC LIMIT 1";
        List<Instituicao> inserted = jdbcTemplate.query(selectSql, rowMapper, 
            instituicao.getNome(), 
            instituicao.getDtaCriacao());
        
        return inserted.isEmpty() ? null : inserted.get(0);
    }

    @Override
    public Instituicao update(Instituicao instituicao) {
        String sql = "UPDATE ap_achados_perdidos.instituicoes SET nome = ?, codigo = ?, tipo = ?, cnpj = ?, Flg_Inativo = ?, Dta_Remocao = ? WHERE id = ?";
        jdbcTemplate.update(sql, 
            instituicao.getNome(),
            instituicao.getCodigo(),
            instituicao.getTipo(),
            instituicao.getCnpj(),
            instituicao.getFlgInativo(),
            instituicao.getDtaRemocao(),
            instituicao.getId());
        
        return findById(instituicao.getId());
    }

    @Override
    public boolean deleteById(int id) {
        String sql = "DELETE FROM ap_achados_perdidos.instituicoes WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, id);
        return rowsAffected > 0;
    }

    @Override
    public List<Instituicao> findActive() {
        String sql = "SELECT * FROM ap_achados_perdidos.instituicoes WHERE Flg_Inativo = false ORDER BY nome";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public List<Instituicao> findByType(String tipoInstituicao) {
        String sql = "SELECT * FROM ap_achados_perdidos.instituicoes WHERE tipo = ? ORDER BY nome";
        return jdbcTemplate.query(sql, rowMapper, tipoInstituicao);
    }
}
