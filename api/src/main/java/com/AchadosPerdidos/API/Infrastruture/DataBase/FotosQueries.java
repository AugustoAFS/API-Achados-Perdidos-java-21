package com.AchadosPerdidos.API.Infrastruture.DataBase;

import com.AchadosPerdidos.API.Domain.Entity.Fotos;
import com.AchadosPerdidos.API.Domain.Enum.Provedor_Armazenamento;
import com.AchadosPerdidos.API.Infrastruture.DataBase.Interfaces.IFotosQueries;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FotosQueries implements IFotosQueries {

    private final JdbcTemplate jdbcTemplate;
    public FotosQueries(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @NonNull
    private final RowMapper<Fotos> rowMapper = (rs, rowNum) -> {
        Fotos fotos = new Fotos();
        fotos.setId(rs.getInt("id"));
        fotos.setUrl(rs.getString("url"));
        String provedorStr = rs.getString("provedor_armazenamento");
        if (provedorStr != null) {
            fotos.setProvedorArmazenamento(Provedor_Armazenamento.valueOf(provedorStr));
        }
        fotos.setChaveArmazenamento(rs.getString("chave_armazenamento"));
        fotos.setNomeArquivoOriginal(rs.getString("nome_arquivo_original"));
        fotos.setTamanhoArquivoBytes(rs.getLong("tamanho_arquivo_bytes"));
        fotos.setDtaCriacao(rs.getTimestamp("Dta_Criacao"));
        fotos.setFlgInativo(rs.getBoolean("Flg_Inativo"));
        fotos.setDtaRemocao(rs.getTimestamp("Dta_Remocao"));
        return fotos;
    };

    @Override
    public List<Fotos> findAll() {
        String sql = "SELECT * FROM ap_achados_perdidos.fotos ORDER BY Dta_Criacao DESC";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public Fotos findById(int id) {
        String sql = "SELECT * FROM ap_achados_perdidos.fotos WHERE id = ?";
        List<Fotos> fotos = jdbcTemplate.query(sql, rowMapper, id);
        return fotos.isEmpty() ? null : fotos.get(0);
    }

    @Override
    public Fotos insert(Fotos fotos) {
        String sql = "INSERT INTO ap_achados_perdidos.fotos (url, provedor_armazenamento, chave_armazenamento, nome_arquivo_original, tamanho_arquivo_bytes, Dta_Criacao, Flg_Inativo) VALUES (?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, 
            fotos.getUrl(),
            fotos.getProvedorArmazenamento() != null ? fotos.getProvedorArmazenamento().toString() : "local",
            fotos.getChaveArmazenamento(),
            fotos.getNomeArquivoOriginal(),
            fotos.getTamanhoArquivoBytes(),
            fotos.getDtaCriacao(),
            fotos.getFlgInativo());
        
        // Buscar o registro inserido para retornar com o ID
        String selectSql = "SELECT * FROM ap_achados_perdidos.fotos WHERE url = ? AND Dta_Criacao = ? ORDER BY id DESC LIMIT 1";
        List<Fotos> inserted = jdbcTemplate.query(selectSql, rowMapper, 
            fotos.getUrl(), 
            fotos.getDtaCriacao());
        
        return inserted.isEmpty() ? null : inserted.get(0);
    }

    @Override
    public Fotos update(Fotos fotos) {
        String sql = "UPDATE ap_achados_perdidos.fotos SET url = ?, provedor_armazenamento = ?, chave_armazenamento = ?, nome_arquivo_original = ?, tamanho_arquivo_bytes = ?, Flg_Inativo = ?, Dta_Remocao = ? WHERE id = ?";
        jdbcTemplate.update(sql, 
            fotos.getUrl(),
            fotos.getProvedorArmazenamento() != null ? fotos.getProvedorArmazenamento().toString() : "local",
            fotos.getChaveArmazenamento(),
            fotos.getNomeArquivoOriginal(),
            fotos.getTamanhoArquivoBytes(),
            fotos.getFlgInativo(),
            fotos.getDtaRemocao(),
            fotos.getId());
        
        return findById(fotos.getId());
    }

    @Override
    public boolean deleteById(int id) {
        String sql = "DELETE FROM ap_achados_perdidos.fotos WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, id);
        return rowsAffected > 0;
    }

    @Override
    public List<Fotos> findActive() {
        String sql = "SELECT * FROM ap_achados_perdidos.fotos WHERE Flg_Inativo = false ORDER BY Dta_Criacao DESC";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public List<Fotos> findByUser(int userId) {
        // TODO: Implementar join com fotos_usuario
        String sql = "SELECT f.* FROM ap_achados_perdidos.fotos f " +
                     "INNER JOIN ap_achados_perdidos.fotos_usuario fu ON f.id = fu.foto_id " +
                     "WHERE fu.usuario_id = ? ORDER BY f.Dta_Criacao DESC";
        return jdbcTemplate.query(sql, rowMapper, userId);
    }

    @Override
    public List<Fotos> findByItem(int itemId) {
        // TODO: Implementar join com fotos_item
        String sql = "SELECT f.* FROM ap_achados_perdidos.fotos f " +
                     "INNER JOIN ap_achados_perdidos.fotos_item fi ON f.id = fi.foto_id " +
                     "WHERE fi.item_id = ? ORDER BY f.Dta_Criacao DESC";
        return jdbcTemplate.query(sql, rowMapper, itemId);
    }

    @Override
    public List<Fotos> findProfilePhotos(int userId) {
        // TODO: Implementar join com fotos_usuario
        String sql = "SELECT f.* FROM ap_achados_perdidos.fotos f " +
                     "INNER JOIN ap_achados_perdidos.fotos_usuario fu ON f.id = fu.foto_id " +
                     "WHERE fu.usuario_id = ? AND f.Flg_Inativo = false ORDER BY f.Dta_Criacao DESC";
        return jdbcTemplate.query(sql, rowMapper, userId);
    }

    @Override
    public List<Fotos> findItemPhotos(int itemId) {
        // TODO: Implementar join com fotos_item
        String sql = "SELECT f.* FROM ap_achados_perdidos.fotos f " +
                     "INNER JOIN ap_achados_perdidos.fotos_item fi ON f.id = fi.foto_id " +
                     "WHERE fi.item_id = ? AND f.Flg_Inativo = false ORDER BY f.Dta_Criacao DESC";
        return jdbcTemplate.query(sql, rowMapper, itemId);
    }

    @Override
    public Fotos findMainItemPhoto(int itemId) {
        // TODO: Implementar join com fotos_item
        String sql = "SELECT f.* FROM ap_achados_perdidos.fotos f " +
                     "INNER JOIN ap_achados_perdidos.fotos_item fi ON f.id = fi.foto_id " +
                     "WHERE fi.item_id = ? AND f.Flg_Inativo = false ORDER BY f.Dta_Criacao DESC LIMIT 1";
        List<Fotos> fotos = jdbcTemplate.query(sql, rowMapper, itemId);
        return fotos.isEmpty() ? null : fotos.get(0);
    }

    @Override
    public Fotos findProfilePhoto(int userId) {
        // TODO: Implementar join com fotos_usuario
        String sql = "SELECT f.* FROM ap_achados_perdidos.fotos f " +
                     "INNER JOIN ap_achados_perdidos.fotos_usuario fu ON f.id = fu.foto_id " +
                     "WHERE fu.usuario_id = ? AND f.Flg_Inativo = false ORDER BY f.Dta_Criacao DESC LIMIT 1";
        List<Fotos> fotos = jdbcTemplate.query(sql, rowMapper, userId);
        return fotos.isEmpty() ? null : fotos.get(0);
    }
}
