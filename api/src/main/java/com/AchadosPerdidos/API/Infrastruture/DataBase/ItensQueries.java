package com.AchadosPerdidos.API.Infrastruture.DataBase;

import com.AchadosPerdidos.API.Domain.Entity.Itens;
import com.AchadosPerdidos.API.Domain.Enum.Tipo_Item;
import com.AchadosPerdidos.API.Domain.Enum.Status_Item;
import com.AchadosPerdidos.API.Infrastruture.DataBase.Interfaces.IItensQueries;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
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
    
    @PersistenceContext
    private EntityManager entityManager;
    
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
        String descLocalItem = rs.getString("Desc_Local_Item");
        if (descLocalItem != null) {
            itens.setDesc_Local_Item(descLocalItem);
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
     * Busca itens por campus - REMOVIDO: não é mais possível buscar por campus sem a tabela locais
     * Esta funcionalidade foi removida com a remoção da tabela locais
     */
    @Override
    public List<Itens> findByCampus(int campusId) {
        // Retorna lista vazia pois não há mais relação com campus através de locais
        return java.util.Collections.emptyList();
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

    @Override
    public List<Itens> findByUser(int userId) {
        TypedQuery<Itens> query = entityManager.createQuery(
            "SELECT i FROM Itens i WHERE i.Usuario_relator_id.Id = :userId AND i.Flg_Inativo = false ORDER BY i.Dta_Criacao DESC", 
            Itens.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    @Override
    public List<Itens> searchByTerm(String searchTerm) {
        String pattern = "%" + searchTerm + "%";
        TypedQuery<Itens> query = entityManager.createQuery(
            "SELECT i FROM Itens i WHERE (i.Nome LIKE :pattern OR i.Descricao LIKE :pattern) AND i.Flg_Inativo = false ORDER BY i.Dta_Criacao DESC", 
            Itens.class);
        query.setParameter("pattern", pattern);
        return query.getResultList();
    }

    @Override
    public List<Itens> findByTipo(String tipo) {
        Tipo_Item tipoItem = Tipo_Item.valueOf(tipo.toUpperCase());
        TypedQuery<Itens> query = entityManager.createQuery(
            "SELECT i FROM Itens i WHERE i.Tipo_item = :tipo AND i.Flg_Inativo = false ORDER BY i.Dta_Criacao DESC", 
            Itens.class);
        query.setParameter("tipo", tipoItem);
        return query.getResultList();
    }

    @Override
    public List<Itens> findByStatus(Status_Item status) {
        TypedQuery<Itens> query = entityManager.createQuery(
            "SELECT i FROM Itens i WHERE i.Status_item = :status AND i.Flg_Inativo = false ORDER BY i.Dta_Criacao DESC", 
            Itens.class);
        query.setParameter("status", status);
        return query.getResultList();
    }
}
