package com.AchadosPerdidos.API.Infrastruture.DataBase;

import com.AchadosPerdidos.API.Domain.Entity.Foto;
import com.AchadosPerdidos.API.Infrastruture.DataBase.Interfaces.IFotosQueries;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class FotosQueries implements IFotosQueries {

    private final JdbcTemplate jdbcTemplate;
    public FotosQueries(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @NonNull
    private final RowMapper<Foto> rowMapper = (rs, rowNum) -> {
        Foto foto = new Foto();
        foto.setId(rs.getInt("id"));
        foto.setUrl(rs.getString("url"));
        foto.setProvedorArmazenamento(rs.getString("provedor_armazenamento"));
        foto.setChaveArmazenamento(rs.getString("chave_armazenamento"));
        foto.setNomeArquivoOriginal(rs.getString("nome_arquivo_original"));
        foto.setTamanhoArquivoBytes(rs.getObject("tamanho_arquivo_bytes", Long.class));
        
        Timestamp dtaCriacao = rs.getTimestamp("Dta_Criacao");
        if (dtaCriacao != null) {
            foto.setDtaCriacao(dtaCriacao.toLocalDateTime());
        }
        
        foto.setFlgInativo(rs.getBoolean("Flg_Inativo"));
        
        Timestamp dtaRemocao = rs.getTimestamp("Dta_Remocao");
        if (dtaRemocao != null) {
            foto.setDtaRemocao(dtaRemocao.toLocalDateTime());
        }
        
        return foto;
    };

    /**
     * Busca fotos por usuário usando JOIN com fotos_usuario
     */
    @Override
    public List<Foto> findByUser(int userId) {
        String sql = "SELECT f.* FROM ap_achados_perdidos.fotos f " +
                     "INNER JOIN ap_achados_perdidos.fotos_usuario fu ON f.id = fu.foto_id " +
                     "WHERE fu.usuario_id = ? AND fu.Flg_Inativo = false ORDER BY f.Dta_Criacao DESC";
        return jdbcTemplate.query(sql, rowMapper, userId);
    }

    @Override
    public List<Foto> findByItem(int itemId) {
        String sql = "SELECT f.* FROM ap_achados_perdidos.fotos f " +
                     "INNER JOIN ap_achados_perdidos.fotos_item fi ON f.id = fi.foto_id " +
                     "WHERE fi.item_id = ? AND fi.Flg_Inativo = false ORDER BY f.Dta_Criacao DESC";
        return jdbcTemplate.query(sql, rowMapper, itemId);
    }

    @Override
    public List<Foto> findProfilePhotos(int userId) {
        String sql = "SELECT f.* FROM ap_achados_perdidos.fotos f " +
                     "INNER JOIN ap_achados_perdidos.fotos_usuario fu ON f.id = fu.foto_id " +
                     "WHERE fu.usuario_id = ? AND f.Flg_Inativo = false AND fu.Flg_Inativo = false ORDER BY f.Dta_Criacao DESC";
        return jdbcTemplate.query(sql, rowMapper, userId);
    }

    @Override
    public List<Foto> findItemPhotos(int itemId) {
        String sql = "SELECT f.* FROM ap_achados_perdidos.fotos f " +
                     "INNER JOIN ap_achados_perdidos.fotos_item fi ON f.id = fi.foto_id " +
                     "WHERE fi.item_id = ? AND f.Flg_Inativo = false AND fi.Flg_Inativo = false ORDER BY f.Dta_Criacao DESC";
        return jdbcTemplate.query(sql, rowMapper, itemId);
    }

    @Override
    public Foto findMainItemPhoto(int itemId) {
        String sql = "SELECT f.* FROM ap_achados_perdidos.fotos f " +
                     "INNER JOIN ap_achados_perdidos.fotos_item fi ON f.id = fi.foto_id " +
                     "WHERE fi.item_id = ? AND f.Flg_Inativo = false AND fi.Flg_Inativo = false ORDER BY f.Dta_Criacao ASC LIMIT 1";
        List<Foto> fotos = jdbcTemplate.query(sql, rowMapper, itemId);
        return fotos.isEmpty() ? null : fotos.get(0);
    }

    @Override
    public Foto findProfilePhoto(int userId) {
        String sql = "SELECT f.* FROM ap_achados_perdidos.fotos f " +
                     "INNER JOIN ap_achados_perdidos.fotos_usuario fu ON f.id = fu.foto_id " +
                     "WHERE fu.usuario_id = ? AND f.Flg_Inativo = false AND fu.Flg_Inativo = false ORDER BY f.Dta_Criacao ASC LIMIT 1";
        List<Foto> fotos = jdbcTemplate.query(sql, rowMapper, userId);
        return fotos.isEmpty() ? null : fotos.get(0);
    }
}
