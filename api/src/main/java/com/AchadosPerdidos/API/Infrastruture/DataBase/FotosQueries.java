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

    @Override
    public List<Foto> findAll() {
        String sql = "SELECT * FROM ap_achados_perdidos.fotos ORDER BY Dta_Criacao DESC";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public Foto findById(int id) {
        String sql = "SELECT * FROM ap_achados_perdidos.fotos WHERE id = ?";
        List<Foto> fotos = jdbcTemplate.query(sql, rowMapper, id);
        return fotos.isEmpty() ? null : fotos.get(0);
    }

    @Override
    public Foto insert(Foto foto) {
        String sql = "INSERT INTO ap_achados_perdidos.fotos (url, provedor_armazenamento, chave_armazenamento, nome_arquivo_original, tamanho_arquivo_bytes, Dta_Criacao, Flg_Inativo) VALUES (?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, 
            foto.getUrl(),
            foto.getProvedorArmazenamento() != null ? foto.getProvedorArmazenamento() : "local",
            foto.getChaveArmazenamento(),
            foto.getNomeArquivoOriginal(),
            foto.getTamanhoArquivoBytes(),
            foto.getDtaCriacao() != null ? Timestamp.valueOf(foto.getDtaCriacao()) : Timestamp.valueOf(LocalDateTime.now()),
            foto.getFlgInativo() != null ? foto.getFlgInativo() : false);
        
        // Buscar o registro inserido para retornar com o ID
        String selectSql = "SELECT * FROM ap_achados_perdidos.fotos WHERE url = ? AND Dta_Criacao = ? ORDER BY id DESC LIMIT 1";
        List<Foto> inserted = jdbcTemplate.query(selectSql, rowMapper, 
            foto.getUrl(), 
            foto.getDtaCriacao() != null ? Timestamp.valueOf(foto.getDtaCriacao()) : Timestamp.valueOf(LocalDateTime.now()));
        
        return inserted.isEmpty() ? null : inserted.get(0);
    }

    @Override
    public Foto update(Foto foto) {
        String sql = "UPDATE ap_achados_perdidos.fotos SET url = ?, provedor_armazenamento = ?, chave_armazenamento = ?, nome_arquivo_original = ?, tamanho_arquivo_bytes = ?, Flg_Inativo = ?, Dta_Remocao = ? WHERE id = ?";
        jdbcTemplate.update(sql, 
            foto.getUrl(),
            foto.getProvedorArmazenamento() != null ? foto.getProvedorArmazenamento() : "local",
            foto.getChaveArmazenamento(),
            foto.getNomeArquivoOriginal(),
            foto.getTamanhoArquivoBytes(),
            foto.getFlgInativo(),
            foto.getDtaRemocao() != null ? Timestamp.valueOf(foto.getDtaRemocao()) : null,
            foto.getId());
        
        return findById(foto.getId());
    }

    @Override
    public boolean deleteById(int id) {
        String sql = "DELETE FROM ap_achados_perdidos.fotos WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, id);
        return rowsAffected > 0;
    }

    @Override
    public List<Foto> findActive() {
        String sql = "SELECT * FROM ap_achados_perdidos.fotos WHERE Flg_Inativo = false ORDER BY Dta_Criacao DESC";
        return jdbcTemplate.query(sql, rowMapper);
    }

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
