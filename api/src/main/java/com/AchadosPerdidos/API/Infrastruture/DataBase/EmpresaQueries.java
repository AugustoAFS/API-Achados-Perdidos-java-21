package com.AchadosPerdidos.API.Infrastruture.DataBase;

import com.AchadosPerdidos.API.Domain.Entity.Empresa;
import com.AchadosPerdidos.API.Infrastruture.DataBase.Interfaces.IEmpresaQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmpresaQueries implements IEmpresaQueries {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Empresa> rowMapper = (rs, rowNum) -> {
        Empresa empresa = new Empresa();
        empresa.setId(rs.getInt("id"));
        empresa.setNome(rs.getString("nome"));
        empresa.setNomeFantasia(rs.getString("nome_fantasia"));
        empresa.setCnpj(rs.getString("cnpj"));
        empresa.setEnderecoId(rs.getObject("endereco_id", Integer.class));
        empresa.setDtaCriacao(rs.getTimestamp("Dta_Criacao"));
        empresa.setFlgInativo(rs.getBoolean("Flg_Inativo"));
        empresa.setDtaRemocao(rs.getTimestamp("Dta_Remocao"));
        return empresa;
    };

    @Override
    public List<Empresa> findAll() {
        String sql = "SELECT * FROM ap_achados_perdidos.empresas ORDER BY nome";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public Empresa findById(int id) {
        String sql = "SELECT * FROM ap_achados_perdidos.empresas WHERE id = ?";
        List<Empresa> empresas = jdbcTemplate.query(sql, rowMapper, id);
        return empresas.isEmpty() ? null : empresas.get(0);
    }

    @Override
    public Empresa insert(Empresa empresa) {
        String sql = "INSERT INTO ap_achados_perdidos.empresas (nome, nome_fantasia, cnpj, endereco_id, Dta_Criacao, Flg_Inativo) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, 
            empresa.getNome(),
            empresa.getNomeFantasia(),
            empresa.getCnpj(),
            empresa.getEnderecoId(),
            empresa.getDtaCriacao(),
            empresa.getFlgInativo());
        
        // Buscar o registro inserido para retornar com o ID
        String selectSql = "SELECT * FROM ap_achados_perdidos.empresas WHERE nome = ? AND Dta_Criacao = ? ORDER BY id DESC LIMIT 1";
        List<Empresa> inserted = jdbcTemplate.query(selectSql, rowMapper, 
            empresa.getNome(), 
            empresa.getDtaCriacao());
        
        return inserted.isEmpty() ? null : inserted.get(0);
    }

    @Override
    public Empresa update(Empresa empresa) {
        String sql = "UPDATE ap_achados_perdidos.empresas SET nome = ?, nome_fantasia = ?, cnpj = ?, endereco_id = ?, Flg_Inativo = ?, Dta_Remocao = ? WHERE id = ?";
        jdbcTemplate.update(sql, 
            empresa.getNome(),
            empresa.getNomeFantasia(),
            empresa.getCnpj(),
            empresa.getEnderecoId(),
            empresa.getFlgInativo(),
            empresa.getDtaRemocao(),
            empresa.getId());
        
        return findById(empresa.getId());
    }

    @Override
    public boolean deleteById(int id) {
        String sql = "DELETE FROM ap_achados_perdidos.empresas WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, id);
        return rowsAffected > 0;
    }

    @Override
    public List<Empresa> findActive() {
        String sql = "SELECT * FROM ap_achados_perdidos.empresas WHERE Flg_Inativo = false ORDER BY nome";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public List<Empresa> findByCountry(String paisSede) {
        // TODO: Este método não faz mais sentido com o novo schema - remover ou adaptar
        return new java.util.ArrayList<>();
    }
}
