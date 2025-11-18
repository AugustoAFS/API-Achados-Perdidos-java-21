package com.AchadosPerdidos.API.Infrastruture.DataBase;

import com.AchadosPerdidos.API.Domain.Entity.Itens;
import com.AchadosPerdidos.API.Domain.Enum.Tipo_ItemEnum;
import com.AchadosPerdidos.API.Infrastruture.DataBase.Interfaces.IItensQueries;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
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
        
        // Mapear tipo_item (VARCHAR no banco, Enum no código)
        String tipoItemStr = rs.getString("tipo_item");
        if (tipoItemStr != null) {
            try {
                itens.setTipoItem(Tipo_ItemEnum.valueOf(tipoItemStr.toUpperCase()));
            } catch (IllegalArgumentException e) {
                itens.setTipoItem(null);
            }
        }
        
        itens.setLocal_id(rs.getInt("local_id"));
        itens.setUsuario_relator_id(rs.getInt("usuario_relator_id"));
        
        Timestamp dtaCriacao = rs.getTimestamp("Dta_Criacao");
        if (dtaCriacao != null) {
            itens.setDtaCriacao(dtaCriacao.toLocalDateTime());
        }
        
        itens.setFlgInativo(rs.getBoolean("Flg_Inativo"));
        
        Timestamp dtaRemocao = rs.getTimestamp("Dta_Remocao");
        if (dtaRemocao != null) {
            itens.setDtaRemocao(dtaRemocao.toLocalDateTime());
        }
        
        return itens;
    };

    @Override
    public List<Itens> findAll() {
        String sql = "SELECT * FROM ap_achados_perdidos.itens ORDER BY Dta_Criacao DESC";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public Itens findById(int id) {
        String sql = "SELECT * FROM ap_achados_perdidos.itens WHERE id = ?";
        List<Itens> itens = jdbcTemplate.query(sql, rowMapper, id);
        return itens.isEmpty() ? null : itens.get(0);
    }

    @Override
    public Itens insert(Itens itens) {
        String sql = "INSERT INTO ap_achados_perdidos.itens (nome, descricao, tipo_item, local_id, usuario_relator_id, Dta_Criacao, Flg_Inativo) VALUES (?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, 
            itens.getNome(),
            itens.getDescricao(),
            itens.getTipoItem() != null ? itens.getTipoItem().name() : null,
            itens.getLocal_id(),
            itens.getUsuario_relator_id(),
            itens.getDtaCriacao() != null ? Timestamp.valueOf(itens.getDtaCriacao()) : Timestamp.valueOf(LocalDateTime.now()),
            itens.getFlgInativo() != null ? itens.getFlgInativo() : false);
        
        // Buscar o registro inserido para retornar com o ID
        String selectSql = "SELECT * FROM ap_achados_perdidos.itens WHERE nome = ? AND usuario_relator_id = ? AND Dta_Criacao = ? ORDER BY id DESC LIMIT 1";
        List<Itens> inserted = jdbcTemplate.query(selectSql, rowMapper, 
            itens.getNome(), 
            itens.getUsuario_relator_id(),
            itens.getDtaCriacao() != null ? Timestamp.valueOf(itens.getDtaCriacao()) : Timestamp.valueOf(LocalDateTime.now()));
        
        return inserted.isEmpty() ? null : inserted.get(0);
    }

    @Override
    public Itens update(Itens itens) {
        String sql = "UPDATE ap_achados_perdidos.itens SET nome = ?, descricao = ?, tipo_item = ?, local_id = ?, usuario_relator_id = ?, Flg_Inativo = ?, Dta_Remocao = ? WHERE id = ?";
        jdbcTemplate.update(sql, 
            itens.getNome(),
            itens.getDescricao(),
            itens.getTipoItem() != null ? itens.getTipoItem().name() : null,
            itens.getLocal_id(),
            itens.getUsuario_relator_id(),
            itens.getFlgInativo(),
            itens.getDtaRemocao() != null ? Timestamp.valueOf(itens.getDtaRemocao()) : null,
            itens.getId());
        
        return findById(itens.getId());
    }

    @Override
    public boolean deleteById(int id) {
        String sql = "DELETE FROM ap_achados_perdidos.itens WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, id);
        return rowsAffected > 0;
    }

    @Override
    public List<Itens> findActive() {
        String sql = "SELECT * FROM ap_achados_perdidos.itens WHERE Flg_Inativo = false ORDER BY Dta_Criacao DESC";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public List<Itens> findByUser(int userId) {
        String sql = "SELECT * FROM ap_achados_perdidos.itens WHERE usuario_relator_id = ? ORDER BY Dta_Criacao DESC";
        return jdbcTemplate.query(sql, rowMapper, userId);
    }

    @Override
    public List<Itens> findByCampus(int campusId) {
        String sql = "SELECT i.* FROM ap_achados_perdidos.itens i " +
                     "INNER JOIN ap_achados_perdidos.locais l ON i.local_id = l.id " +
                     "WHERE l.campus_id = ? AND i.Flg_Inativo = false ORDER BY i.Dta_Criacao DESC";
        return jdbcTemplate.query(sql, rowMapper, campusId);
    }

    @Override
    public List<Itens> findByLocal(int localId) {
        String sql = "SELECT * FROM ap_achados_perdidos.itens WHERE local_id = ? AND Flg_Inativo = false ORDER BY Dta_Criacao DESC";
        return jdbcTemplate.query(sql, rowMapper, localId);
    }

    @Override
    public List<Itens> searchByTerm(String searchTerm) {
        String sql = "SELECT * FROM ap_achados_perdidos.itens WHERE (nome LIKE ? OR descricao LIKE ?) AND Flg_Inativo = false ORDER BY Dta_Criacao DESC";
        String searchPattern = "%" + searchTerm + "%";
        return jdbcTemplate.query(sql, rowMapper, searchPattern, searchPattern);
    }

    @Override
    public List<Itens> findByTipo(String tipo) {
        String sql = "SELECT * FROM ap_achados_perdidos.itens WHERE UPPER(tipo_item) = UPPER(?) AND Flg_Inativo = false ORDER BY Dta_Criacao DESC";
        return jdbcTemplate.query(sql, rowMapper, tipo);
    }
}
