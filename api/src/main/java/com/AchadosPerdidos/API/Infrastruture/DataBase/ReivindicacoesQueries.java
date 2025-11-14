package com.AchadosPerdidos.API.Infrastruture.DataBase;

import com.AchadosPerdidos.API.Domain.Entity.Reivindicacoes;
import com.AchadosPerdidos.API.Infrastruture.DataBase.Interfaces.IReivindicacoesQueries;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReivindicacoesQueries implements IReivindicacoesQueries {

    private final JdbcTemplate jdbcTemplate;
    public ReivindicacoesQueries(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @NonNull
    private final RowMapper<Reivindicacoes> rowMapper = (rs, rowNum) -> {
        Reivindicacoes reivindicacoes = new Reivindicacoes();
        reivindicacoes.setId(rs.getInt("id"));
        reivindicacoes.setDetalhesReivindicacao(rs.getString("detalhes_reivindicacao"));
        reivindicacoes.setItemId(rs.getInt("item_id"));
        reivindicacoes.setUsuarioReivindicadorId(rs.getInt("usuario_reivindicador_id"));
        reivindicacoes.setUsuarioAchouId(rs.getObject("usuario_achou_id", Integer.class));
        reivindicacoes.setDtaCriacao(rs.getTimestamp("Dta_Criacao"));
        reivindicacoes.setFlgInativo(rs.getBoolean("Flg_Inativo"));
        reivindicacoes.setDtaRemocao(rs.getTimestamp("Dta_Remocao"));
        return reivindicacoes;
    };

    @Override
    public List<Reivindicacoes> findAll() {
        String sql = "SELECT * FROM ap_achados_perdidos.itens_reivindicados ORDER BY Dta_Criacao DESC";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public Reivindicacoes findById(int id) {
        String sql = "SELECT * FROM ap_achados_perdidos.itens_reivindicados WHERE id = ?";
        List<Reivindicacoes> reivindicacoes = jdbcTemplate.query(sql, rowMapper, id);
        return reivindicacoes.isEmpty() ? null : reivindicacoes.get(0);
    }

    @Override
    public Reivindicacoes insert(Reivindicacoes reivindicacoes) {
        String sql = "INSERT INTO ap_achados_perdidos.itens_reivindicados (detalhes_reivindicacao, item_id, usuario_reivindicador_id, usuario_achou_id, Dta_Criacao, Flg_Inativo) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, 
            reivindicacoes.getDetalhesReivindicacao(),
            reivindicacoes.getItemId(),
            reivindicacoes.getUsuarioReivindicadorId(),
            reivindicacoes.getUsuarioAchouId(),
            reivindicacoes.getDtaCriacao(),
            reivindicacoes.getFlgInativo());
        
        // Buscar o registro inserido para retornar com o ID
        String selectSql = "SELECT * FROM ap_achados_perdidos.itens_reivindicados WHERE item_id = ? AND usuario_reivindicador_id = ? AND Dta_Criacao = ? ORDER BY id DESC LIMIT 1";
        List<Reivindicacoes> inserted = jdbcTemplate.query(selectSql, rowMapper, 
            reivindicacoes.getItemId(),
            reivindicacoes.getUsuarioReivindicadorId(),
            reivindicacoes.getDtaCriacao());
        
        return inserted.isEmpty() ? null : inserted.get(0);
    }

    @Override
    public Reivindicacoes update(Reivindicacoes reivindicacoes) {
        String sql = "UPDATE ap_achados_perdidos.itens_reivindicados SET detalhes_reivindicacao = ?, item_id = ?, usuario_reivindicador_id = ?, usuario_achou_id = ?, Flg_Inativo = ?, Dta_Remocao = ? WHERE id = ?";
        jdbcTemplate.update(sql, 
            reivindicacoes.getDetalhesReivindicacao(),
            reivindicacoes.getItemId(),
            reivindicacoes.getUsuarioReivindicadorId(),
            reivindicacoes.getUsuarioAchouId(),
            reivindicacoes.getFlgInativo(),
            reivindicacoes.getDtaRemocao(),
            reivindicacoes.getId());
        
        return findById(reivindicacoes.getId());
    }

    @Override
    public boolean deleteById(int id) {
        String sql = "DELETE FROM ap_achados_perdidos.itens_reivindicados WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, id);
        return rowsAffected > 0;
    }

    @Override
    public List<Reivindicacoes> findByItem(int itemId) {
        String sql = "SELECT * FROM ap_achados_perdidos.itens_reivindicados WHERE item_id = ? AND Flg_Inativo = false ORDER BY Dta_Criacao DESC";
        return jdbcTemplate.query(sql, rowMapper, itemId);
    }

    @Override
    public List<Reivindicacoes> findByUser(int userId) {
        String sql = "SELECT * FROM ap_achados_perdidos.itens_reivindicados WHERE usuario_reivindicador_id = ? AND Flg_Inativo = false ORDER BY Dta_Criacao DESC";
        return jdbcTemplate.query(sql, rowMapper, userId);
    }

    @Override
    public List<Reivindicacoes> findByProprietario(int proprietarioId) {
        // TODO: Verificar se usuario_achou_id é o proprietário ou se precisa de outra lógica
        String sql = "SELECT * FROM ap_achados_perdidos.itens_reivindicados WHERE usuario_achou_id = ? AND Flg_Inativo = false ORDER BY Dta_Criacao DESC";
        return jdbcTemplate.query(sql, rowMapper, proprietarioId);
    }

    @Override
    public Reivindicacoes findByItemAndUser(int itemId, int userId) {
        String sql = "SELECT * FROM ap_achados_perdidos.itens_reivindicados WHERE item_id = ? AND usuario_reivindicador_id = ? AND Flg_Inativo = false AND Dta_Remocao IS NULL ORDER BY Dta_Criacao DESC LIMIT 1";
        List<Reivindicacoes> reivindicacoes = jdbcTemplate.query(sql, rowMapper, itemId, userId);
        return reivindicacoes.isEmpty() ? null : reivindicacoes.get(0);
    }
}
