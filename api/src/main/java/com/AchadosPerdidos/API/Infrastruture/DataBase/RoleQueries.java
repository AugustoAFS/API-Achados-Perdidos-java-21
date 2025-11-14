package com.AchadosPerdidos.API.Infrastruture.DataBase;

import com.AchadosPerdidos.API.Domain.Entity.Role;
import com.AchadosPerdidos.API.Infrastruture.DataBase.Interfaces.IRoleQueries;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RoleQueries implements IRoleQueries {

    private final JdbcTemplate jdbcTemplate;

    public RoleQueries(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Role> rowMapper = (rs, rowNum) -> {
        Role role = new Role();
        role.setId(rs.getInt("id"));
        role.setNome(rs.getString("nome"));
        role.setDescricao(rs.getString("descricao"));
        role.setDtaCriacao(rs.getTimestamp("Dta_Criacao"));
        role.setFlgInativo(rs.getBoolean("Flg_Inativo"));
        role.setDtaRemocao(rs.getTimestamp("Dta_Remocao"));
        return role;
    };

    @Override
    public List<Role> findAll() {
        String sql = "SELECT * FROM ap_achados_perdidos.roles ORDER BY nome";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public Role findById(Integer id) {
        String sql = "SELECT * FROM ap_achados_perdidos.roles WHERE id = ?";
        List<Role> roles = jdbcTemplate.query(sql, rowMapper, id);
        return roles.isEmpty() ? null : roles.get(0);
    }

    @Override
    public Role findByNome(String nome) {
        String sql = "SELECT * FROM ap_achados_perdidos.roles WHERE nome = ?";
        List<Role> roles = jdbcTemplate.query(sql, rowMapper, nome);
        return roles.isEmpty() ? null : roles.get(0);
    }

    @Override
    public List<Role> findActive() {
        String sql = "SELECT * FROM ap_achados_perdidos.roles WHERE Flg_Inativo = false ORDER BY nome";
        return jdbcTemplate.query(sql, rowMapper);
    }
}

