package com.AchadosPerdidos.API.Infrastruture.DataBase;

import com.AchadosPerdidos.API.Domain.Entity.Instituicoes;
import com.AchadosPerdidos.API.Infrastruture.DataBase.Interfaces.IInstituicaoQueries;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class InstituicaoQueries implements IInstituicaoQueries {

    private final JdbcTemplate jdbcTemplate;
    public InstituicaoQueries(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @NonNull
    private final RowMapper<Instituicoes> rowMapper = (rs, rowNum) -> {
        Instituicoes instituicao = new Instituicoes();
        instituicao.setId(rs.getInt("id"));
        instituicao.setNome(rs.getString("nome"));
        instituicao.setCodigo(rs.getString("codigo"));
        instituicao.setTipo(rs.getString("tipo"));
        instituicao.setCnpj(rs.getString("cnpj"));
        
        Timestamp dtaCriacao = rs.getTimestamp("Dta_Criacao");
        if (dtaCriacao != null) {
            instituicao.setDtaCriacao(dtaCriacao.toLocalDateTime());
        }
        
        instituicao.setFlgInativo(rs.getBoolean("Flg_Inativo"));
        
        Timestamp dtaRemocao = rs.getTimestamp("Dta_Remocao");
        if (dtaRemocao != null) {
            instituicao.setDtaRemocao(dtaRemocao.toLocalDateTime());
        }
        
        return instituicao;
    };

    @Override
    public List<Instituicoes> findAll() {
        String sql = "SELECT * FROM ap_achados_perdidos.instituicoes ORDER BY nome";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public Instituicoes findById(int id) {
        String sql = "SELECT * FROM ap_achados_perdidos.instituicoes WHERE id = ?";
        List<Instituicoes> instituicoes = jdbcTemplate.query(sql, rowMapper, id);
        return instituicoes.isEmpty() ? null : instituicoes.get(0);
    }

    @Override
    public Instituicoes insert(Instituicoes instituicao) {
        String sql = "INSERT INTO ap_achados_perdidos.instituicoes (nome, codigo, tipo, cnpj, Dta_Criacao, Flg_Inativo) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, 
            instituicao.getNome(),
            instituicao.getCodigo(),
            instituicao.getTipo(),
            instituicao.getCnpj(),
            instituicao.getDtaCriacao() != null ? Timestamp.valueOf(instituicao.getDtaCriacao()) : Timestamp.valueOf(LocalDateTime.now()),
            instituicao.getFlgInativo() != null ? instituicao.getFlgInativo() : false);
        
        // Buscar o registro inserido para retornar com o ID
        String selectSql = "SELECT * FROM ap_achados_perdidos.instituicoes WHERE nome = ? AND Dta_Criacao = ? ORDER BY id DESC LIMIT 1";
        List<Instituicoes> inserted = jdbcTemplate.query(selectSql, rowMapper, 
            instituicao.getNome(), 
            instituicao.getDtaCriacao() != null ? Timestamp.valueOf(instituicao.getDtaCriacao()) : Timestamp.valueOf(LocalDateTime.now()));
        
        return inserted.isEmpty() ? null : inserted.get(0);
    }

    @Override
    public Instituicoes update(Instituicoes instituicao) {
        String sql = "UPDATE ap_achados_perdidos.instituicoes SET nome = ?, codigo = ?, tipo = ?, cnpj = ?, Flg_Inativo = ?, Dta_Remocao = ? WHERE id = ?";
        jdbcTemplate.update(sql, 
            instituicao.getNome(),
            instituicao.getCodigo(),
            instituicao.getTipo(),
            instituicao.getCnpj(),
            instituicao.getFlgInativo(),
            instituicao.getDtaRemocao() != null ? Timestamp.valueOf(instituicao.getDtaRemocao()) : null,
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
    public List<Instituicoes> findActive() {
        String sql = "SELECT * FROM ap_achados_perdidos.instituicoes WHERE Flg_Inativo = false ORDER BY nome";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public List<Instituicoes> findByType(String tipoInstituicao) {
        String sql = "SELECT * FROM ap_achados_perdidos.instituicoes WHERE tipo = ? ORDER BY nome";
        return jdbcTemplate.query(sql, rowMapper, tipoInstituicao);
    }
}
