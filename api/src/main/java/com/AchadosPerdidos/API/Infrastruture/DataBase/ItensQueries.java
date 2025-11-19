package com.AchadosPerdidos.API.Infrastruture.DataBase;

import com.AchadosPerdidos.API.Domain.Entity.Itens;
import com.AchadosPerdidos.API.Domain.Enum.Tipo_Item;
import com.AchadosPerdidos.API.Domain.Enum.Status_Item;
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
        
        // Mapear tipo_item
        String tipoItemStr = rs.getString("tipo_item");
        if (tipoItemStr != null) {
            try {
                itens.setTipoItem(Tipo_Item.valueOf(tipoItemStr.toUpperCase()));
            } catch (IllegalArgumentException e) {
                itens.setTipoItem(null);
            }
        }
        
        // Mapear status_item
        String statusItemStr = rs.getString("status_item");
        if (statusItemStr != null) {
            try {
                itens.setStatus_item(Status_Item.valueOf(statusItemStr.toUpperCase()));
            } catch (IllegalArgumentException e) {
                itens.setStatus_item(Status_Item.ATIVO);
            }
        }

        // Mapear relacionamentos - criar objetos mínimos com apenas ID
        Integer localId = rs.getObject("local_id", Integer.class);
        if (localId != null) {
            com.AchadosPerdidos.API.Domain.Entity.Local local = new com.AchadosPerdidos.API.Domain.Entity.Local();
            local.setId(localId);
            itens.setLocal_id(local);
        }
        
        Integer usuarioRelatorId = rs.getObject("usuario_relator_id", Integer.class);
        if (usuarioRelatorId != null) {
            com.AchadosPerdidos.API.Domain.Entity.Usuario usuario = new com.AchadosPerdidos.API.Domain.Entity.Usuario();
            usuario.setId(usuarioRelatorId);
            itens.setUsuario_relator_id(usuario);
        }
        
        Integer usuarioReivindicadorId = rs.getObject("usuario_reivindicador_id", Integer.class);
        if (usuarioReivindicadorId != null) {
            com.AchadosPerdidos.API.Domain.Entity.Usuario usuario = new com.AchadosPerdidos.API.Domain.Entity.Usuario();
            usuario.setId(usuarioReivindicadorId);
            itens.setUsuario_reivindicador_id(usuario);
        }

        Timestamp dtaReivindicacao = rs.getTimestamp("dta_reivindicacao");
        if (dtaReivindicacao != null) {
            itens.setDta_Reivindicacao(dtaReivindicacao.toLocalDateTime());
        }

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

    /**
     * Busca itens por campus usando JOIN com locais
     * Esta operação requer JOIN, por isso fica nas Queries
     */
    @Override
    public List<Itens> findByCampus(int campusId) {
        String sql = "SELECT i.* FROM ap_achados_perdidos.itens i " +
                     "INNER JOIN ap_achados_perdidos.locais l ON i.local_id = l.id " +
                     "WHERE l.campus_id = ? AND i.Flg_Inativo = false ORDER BY i.Dta_Criacao DESC";
        return jdbcTemplate.query(sql, rowMapper, campusId);
    }

    /**
     * Marca item ACHADO como devolvido (regra de negócio complexa)
     */
    @Override
    public boolean marcarComoDevolvido(int itemId, int usuarioReivindicadorId) {
        String sql = "UPDATE ap_achados_perdidos.itens SET status_item = 'DEVOLVIDO', usuario_reivindicador_id = ?, dta_reivindicacao = ? WHERE id = ? AND tipo_item = 'ACHADO' AND status_item = 'ATIVO'";
        int rowsAffected = jdbcTemplate.update(sql, usuarioReivindicadorId, Timestamp.valueOf(LocalDateTime.now()), itemId);
        return rowsAffected > 0;
    }

    /**
     * Marca item PERDIDO como resgatado (regra de negócio complexa)
     */
    @Override
    public boolean marcarComoResgatado(int itemId, int usuarioReivindicadorId) {
        String sql = "UPDATE ap_achados_perdidos.itens SET status_item = 'RESGATADO', usuario_reivindicador_id = ?, dta_reivindicacao = ? WHERE id = ? AND tipo_item = 'PERDIDO' AND status_item = 'ATIVO'";
        int rowsAffected = jdbcTemplate.update(sql, usuarioReivindicadorId, Timestamp.valueOf(LocalDateTime.now()), itemId);
        return rowsAffected > 0;
    }
}
