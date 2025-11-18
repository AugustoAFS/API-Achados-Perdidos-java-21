package com.AchadosPerdidos.API.Infrastruture.DataBase;

import com.AchadosPerdidos.API.Domain.Entity.Endereco;
import com.AchadosPerdidos.API.Infrastruture.DataBase.Interfaces.IEnderecoQueries;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class EnderecoQueries implements IEnderecoQueries {

    private final JdbcTemplate jdbcTemplate;
    public EnderecoQueries(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @NonNull
    private final RowMapper<Endereco> rowMapper = (rs, rowNum) -> {
        Endereco endereco = new Endereco();
        endereco.setId(rs.getInt("id"));
        endereco.setLogradouro(rs.getString("logradouro"));
        endereco.setNumero(rs.getString("numero"));
        endereco.setComplemento(rs.getString("complemento"));
        endereco.setBairro(rs.getString("bairro"));
        endereco.setCep(rs.getString("cep"));
        endereco.setCidadeId(rs.getInt("cidade_id"));
        
        Timestamp dtaCriacao = rs.getTimestamp("Dta_Criacao");
        if (dtaCriacao != null) {
            endereco.setDtaCriacao(dtaCriacao.toLocalDateTime());
        }
        
        endereco.setFlgInativo(rs.getBoolean("Flg_Inativo"));
        
        // Endereco não tem setDtaRemocao, apenas getter
        // Dta_Remocao é lido mas não pode ser setado
        
        return endereco;
    };

    @Override
    public List<Endereco> findAll() {
        String sql = "SELECT * FROM ap_achados_perdidos.enderecos ORDER BY logradouro";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public Endereco findById(Integer id) {
        String sql = "SELECT * FROM ap_achados_perdidos.enderecos WHERE id = ?";
        List<Endereco> enderecos = jdbcTemplate.query(sql, rowMapper, id);
        return enderecos.isEmpty() ? null : enderecos.get(0);
    }

    @Override
    public Endereco insert(Endereco endereco) {
        String sql = "INSERT INTO ap_achados_perdidos.enderecos (logradouro, numero, complemento, bairro, cep, cidade_id, Dta_Criacao, Flg_Inativo) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
            endereco.getLogradouro(),
            endereco.getNumero(),
            endereco.getComplemento(),
            endereco.getBairro(),
            endereco.getCep(),
            endereco.getCidadeId(),
            endereco.getDtaCriacao() != null ? Timestamp.valueOf(endereco.getDtaCriacao()) : Timestamp.valueOf(LocalDateTime.now()),
            endereco.getFlgInativo() != null ? endereco.getFlgInativo() : false);

        String selectSql = "SELECT * FROM ap_achados_perdidos.enderecos WHERE logradouro = ? AND numero = ? AND cidade_id = ? AND Dta_Criacao = ? ORDER BY id DESC LIMIT 1";
        List<Endereco> inserted = jdbcTemplate.query(selectSql, rowMapper,
            endereco.getLogradouro(),
            endereco.getNumero(),
            endereco.getCidadeId(),
            endereco.getDtaCriacao() != null ? Timestamp.valueOf(endereco.getDtaCriacao()) : Timestamp.valueOf(LocalDateTime.now()));

        return inserted.isEmpty() ? null : inserted.get(0);
    }

    @Override
    public Endereco update(Endereco endereco) {
        String sql = "UPDATE ap_achados_perdidos.enderecos SET logradouro = ?, numero = ?, complemento = ?, bairro = ?, cep = ?, cidade_id = ?, Flg_Inativo = ? WHERE id = ?";
        jdbcTemplate.update(sql,
            endereco.getLogradouro(),
            endereco.getNumero(),
            endereco.getComplemento(),
            endereco.getBairro(),
            endereco.getCep(),
            endereco.getCidadeId(),
            endereco.getFlgInativo(),
            endereco.getId());

        return findById(endereco.getId());
    }

    @Override
    public boolean deleteById(Integer id) {
        String sql = "DELETE FROM ap_achados_perdidos.enderecos WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, id);
        return rowsAffected > 0;
    }

    @Override
    public List<Endereco> findActive() {
        String sql = "SELECT * FROM ap_achados_perdidos.enderecos WHERE Flg_Inativo = false ORDER BY logradouro";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public List<Endereco> findByCidade(Integer cidadeId) {
        String sql = "SELECT * FROM ap_achados_perdidos.enderecos WHERE cidade_id = ? AND Flg_Inativo = false ORDER BY logradouro";
        return jdbcTemplate.query(sql, rowMapper, cidadeId);
    }
}
