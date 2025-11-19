package com.AchadosPerdidos.API.Infrastruture.DataBase;

import com.AchadosPerdidos.API.Domain.Entity.Usuario;
import com.AchadosPerdidos.API.Infrastruture.DataBase.Interfaces.IUsuariosQueries;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Queries para operações complexas de Usuarios que requerem JOINs
 * CRUD básico é feito via JPA no UsuariosRepository
 */
@Repository
public class UsuariosQueries implements IUsuariosQueries {

    private final JdbcTemplate jdbcTemplate;

    public UsuariosQueries(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @NonNull
    private final RowMapper<Usuario> rowMapper = (rs, rowNum) -> {
        Usuario usuario = new Usuario();
        usuario.setId(rs.getInt("id"));
        usuario.setNomeCompleto(rs.getString("nome_completo"));
        usuario.setCpf(rs.getString("cpf"));
        usuario.setEmail(rs.getString("email"));
        usuario.setHash_senha(rs.getString("hash_senha"));
        usuario.setMatricula(rs.getString("matricula"));
        usuario.setNumero_telefone(rs.getString("numero_telefone"));

        // Mapear relacionamentos - criar objetos mínimos com apenas ID
        Integer enderecoId = rs.getObject("endereco_id", Integer.class);
        if (enderecoId != null) {
            com.AchadosPerdidos.API.Domain.Entity.Endereco endereco = new com.AchadosPerdidos.API.Domain.Entity.Endereco();
            endereco.setId(enderecoId);
            usuario.setEndereco_id(endereco);
        }

        Integer roleId = rs.getObject("role_id", Integer.class);
        if (roleId != null) {
            com.AchadosPerdidos.API.Domain.Entity.Role role = new com.AchadosPerdidos.API.Domain.Entity.Role();
            role.setId(roleId);
            usuario.setRole_id(role);
        }

        java.sql.Timestamp dtaCriacao = rs.getTimestamp("Dta_Criacao");
        if (dtaCriacao != null) {
            usuario.setDta_Criacao(dtaCriacao.toLocalDateTime());
        }
        
        usuario.setFlg_Inativo(rs.getBoolean("Flg_Inativo"));
        
        java.sql.Timestamp dtaRemocao = rs.getTimestamp("Dta_Remocao");
        if (dtaRemocao != null) {
            usuario.setDta_Remocao(dtaRemocao.toLocalDateTime());
        }
        
        return usuario;
    };

    /**
     * Busca usuários por instituição usando JOIN com usuario_campus e campus
     */
    @Override
    public List<Usuario> findByInstitution(int instituicaoId) {
        String sql = "SELECT u.* FROM ap_achados_perdidos.usuarios u " +
                     "INNER JOIN ap_achados_perdidos.usuario_campus uc ON u.id = uc.usuario_id " +
                     "INNER JOIN ap_achados_perdidos.campus c ON uc.campus_id = c.id " +
                     "WHERE c.instituicao_id = ? AND uc.Flg_Inativo = false ORDER BY u.nome_completo";
        return jdbcTemplate.query(sql, rowMapper, instituicaoId);
    }

    /**
     * Busca usuários por campus usando JOIN com usuario_campus
     */
    @Override
    public List<Usuario> findByCampus(int campusId) {
        String sql = "SELECT u.* FROM ap_achados_perdidos.usuarios u " +
                     "INNER JOIN ap_achados_perdidos.usuario_campus uc ON u.id = uc.usuario_id " +
                     "WHERE uc.campus_id = ? AND uc.Flg_Inativo = false ORDER BY u.nome_completo";
        return jdbcTemplate.query(sql, rowMapper, campusId);
    }

    /**
     * Retorna o nome do campus ativo do usuário usando JOIN
     */
    @Override
    public String getCampusNomeAtivoByUsuarioId(int usuarioId) {
        String sql = "SELECT c.nome FROM ap_achados_perdidos.usuario_campus uc " +
                     "INNER JOIN ap_achados_perdidos.campus c ON uc.campus_id = c.id " +
                     "WHERE uc.usuario_id = ? AND uc.Flg_Inativo = false AND c.Flg_Inativo = false " +
                     "ORDER BY uc.Dta_Criacao ASC LIMIT 1";
        try {
            List<String> resultados = jdbcTemplate.query(sql, (rs, rowNum) -> {
                String nome = rs.getString("nome");
                return nome != null ? nome : "";
            }, usuarioId);
            return resultados.isEmpty() ? "" : resultados.get(0);
        } catch (Exception e) {
            return "";
        }
    }
}
