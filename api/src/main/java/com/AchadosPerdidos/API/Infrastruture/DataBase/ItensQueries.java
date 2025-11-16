package com.AchadosPerdidos.API.Infrastruture.DataBase;

import com.AchadosPerdidos.API.Domain.Entity.Itens;
import com.AchadosPerdidos.API.Infrastruture.DataBase.Interfaces.IItensQueries;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class ItensQueries implements IItensQueries {

    private final JdbcTemplate jdbcTemplate;
    public ItensQueries(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @NonNull
    private final RowMapper<Itens> rowMapper = (rs, rowNum) -> {
        Itens itens = new Itens();
        itens.setId(rs.getInt("id"));
        itens.setNome(rs.getString("nome"));
        itens.setDescricao(rs.getString("descricao"));
        itens.setEncontradoEm(rs.getTimestamp("encontrado_em"));
        itens.setUsuarioRelatorId(rs.getInt("usuario_relator_id"));
        itens.setLocalId(rs.getInt("local_id"));
        itens.setStatusItemId(rs.getInt("status_item_id"));
        itens.setDtaCriacao(rs.getTimestamp("Dta_Criacao"));
        itens.setFlgInativo(rs.getBoolean("Flg_Inativo"));
        itens.setDtaRemocao(rs.getTimestamp("Dta_Remocao") != null ? rs.getTimestamp("Dta_Remocao") : null);
        return itens;
    };

    @Override
    public List<Itens> findAll() {
        String sql = "SELECT * FROM ap_achados_perdidos.itens_perdidos ORDER BY Dta_Criacao DESC";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public Itens findById(int id) {
        String sql = "SELECT * FROM ap_achados_perdidos.itens_perdidos WHERE id = ?";
        List<Itens> itens = jdbcTemplate.query(sql, rowMapper, id);
        return itens.isEmpty() ? null : itens.get(0);
    }

    @Override
    public Itens insert(Itens itens) {
        String sql = "INSERT INTO ap_achados_perdidos.itens_perdidos (nome, descricao, encontrado_em, usuario_relator_id, local_id, status_item_id, Dta_Criacao, Flg_Inativo) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, 
            itens.getNome(),
            itens.getDescricao(),
            itens.getEncontradoEm(),
            itens.getUsuarioRelatorId(),
            itens.getLocalId(),
            itens.getStatusItemId(),
            itens.getDtaCriacao(),
            itens.getFlgInativo());
        
        String selectSql = "SELECT * FROM ap_achados_perdidos.itens_perdidos WHERE nome = ? AND Dta_Criacao = ? ORDER BY id DESC LIMIT 1";
        List<Itens> inserted = jdbcTemplate.query(selectSql, rowMapper, 
            itens.getNome(), 
            itens.getDtaCriacao());
        
        return inserted.isEmpty() ? null : inserted.get(0);
    }

    @Override
    public Itens update(Itens itens) {
        String sql = "UPDATE ap_achados_perdidos.itens_perdidos SET nome = ?, descricao = ?, encontrado_em = ?, Flg_Inativo = ?, status_item_id = ?, usuario_relator_id = ?, local_id = ?, Dta_Remocao = ? WHERE id = ?";
        jdbcTemplate.update(sql, 
            itens.getNome(),
            itens.getDescricao(),
            itens.getEncontradoEm(),
            itens.getFlgInativo(),
            itens.getStatusItemId(),
            itens.getUsuarioRelatorId(),
            itens.getLocalId(),
            itens.getDtaRemocao(),
            itens.getId());
        
        return findById(itens.getId());
    }

    @Override
    public boolean deleteById(int id) {
        String sql = "DELETE FROM ap_achados_perdidos.itens_perdidos WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, id);
        return rowsAffected > 0;
    }

    @Override
    public List<Itens> findActive() {
        String sql = "SELECT * FROM ap_achados_perdidos.itens_perdidos WHERE Flg_Inativo = false ORDER BY Dta_Criacao DESC";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public List<Itens> findByStatus(int statusId) {
        String sql = "SELECT * FROM ap_achados_perdidos.itens_perdidos WHERE status_item_id = ? ORDER BY Dta_Criacao DESC";
        return jdbcTemplate.query(sql, rowMapper, statusId);
    }

    @Override
    public List<Itens> findByUser(int userId) {
        String sql = "SELECT * FROM ap_achados_perdidos.itens_perdidos WHERE usuario_relator_id = ? ORDER BY Dta_Criacao DESC";
        return jdbcTemplate.query(sql, rowMapper, userId);
    }

    @Override
    public List<Itens> findByCampus(int campusId) {
        String sql = "SELECT ip.* FROM ap_achados_perdidos.itens_perdidos ip " +
                     "INNER JOIN ap_achados_perdidos.locais l ON ip.local_id = l.id " +
                     "WHERE l.campus_id = ? ORDER BY ip.Dta_Criacao DESC";
        return jdbcTemplate.query(sql, rowMapper, campusId);
    }

    @Override
    public List<Itens> findByLocal(int localId) {
        String sql = "SELECT * FROM ap_achados_perdidos.itens_perdidos WHERE local_id = ? ORDER BY Dta_Criacao DESC";
        return jdbcTemplate.query(sql, rowMapper, localId);
    }

    @Override
    public List<Itens> findByEmpresa(int empresaId) {
        String sql = "SELECT ip.* FROM ap_achados_perdidos.itens_perdidos ip " +
                     "INNER JOIN ap_achados_perdidos.usuarios u ON ip.usuario_relator_id = u.id " +
                     "WHERE u.empresa_id = ? ORDER BY ip.Dta_Criacao DESC";
        return jdbcTemplate.query(sql, rowMapper, empresaId);
    }

    @Override
    public List<Itens> searchByTerm(String searchTerm) {
        String sql = "SELECT * FROM ap_achados_perdidos.itens_perdidos WHERE (nome LIKE ? OR descricao LIKE ?) AND Flg_Inativo = false ORDER BY Dta_Criacao DESC";
        String searchPattern = "%" + searchTerm + "%";
        return jdbcTemplate.query(sql, rowMapper, searchPattern, searchPattern);
    }

    @Override
    public List<Itens> findItemsNearDonationDeadline(int daysFromNow) {
        String sql = "SELECT * FROM ap_achados_perdidos.itens_perdidos WHERE " +
                     "Flg_Inativo = false AND " +
                     "status_item_id = 1 AND " +
                     "Dta_Criacao <= (CURRENT_DATE - INTERVAL '1 day' * ?) " +
                     "ORDER BY Dta_Criacao ASC";
        return jdbcTemplate.query(sql, rowMapper, daysFromNow);
    }

    @Override
    public List<Itens> findExpiredItems(int daysExpired) {
        String sql = "SELECT * FROM ap_achados_perdidos.itens_perdidos WHERE " +
                     "Flg_Inativo = false AND " +
                     "status_item_id = 1 AND " +
                     "Dta_Criacao <= (CURRENT_DATE - INTERVAL '1 day' * ?) " +
                     "ORDER BY Dta_Criacao ASC";
        return jdbcTemplate.query(sql, rowMapper, daysExpired);
    }
}
