package com.AchadosPerdidos.API.Domain.Repository;

import com.AchadosPerdidos.API.Domain.Entity.UsuarioCampus;
import com.AchadosPerdidos.API.Domain.Repository.Interfaces.IUsuarioCampusRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository para gerenciar associação entre Usuários e Campus
 * Usa JPA para CRUD básico
 */
@Repository
public interface UsuarioCampusRepository extends JpaRepository<UsuarioCampus, Integer>, IUsuarioCampusRepository {
    
    // CRUD básico já vem do JpaRepository: save, findById, findAll, deleteById
    
    // Queries customizadas (necessário porque o campo é Flg_Inativo com underscore)
    @Query("SELECT uc FROM UsuarioCampus uc WHERE uc.Flg_Inativo = false")
    List<UsuarioCampus> findByFlgInativoFalse();
    
    @Query("SELECT uc FROM UsuarioCampus uc WHERE uc.Id = :id AND uc.Flg_Inativo = false")
    Optional<UsuarioCampus> findByIdAndFlgInativoFalse(@Param("id") Integer id);
    
    // Queries customizadas simples
    @Query("SELECT uc FROM UsuarioCampus uc WHERE uc.Usuario_id.Id = :usuarioId AND uc.Campus_id.Id = :campusId")
    Optional<UsuarioCampus> findByUsuarioIdAndCampusIdOptional(@Param("usuarioId") Integer usuarioId, @Param("campusId") Integer campusId);
    
    @Query("SELECT uc FROM UsuarioCampus uc WHERE uc.Usuario_id.Id = :usuarioId AND uc.Flg_Inativo = false")
    List<UsuarioCampus> findByUsuarioId(@Param("usuarioId") Integer usuarioId);
    
    @Query("SELECT uc FROM UsuarioCampus uc WHERE uc.Campus_id.Id = :campusId AND uc.Flg_Inativo = false")
    List<UsuarioCampus> findByCampusId(@Param("campusId") Integer campusId);
    
    // Implementação padrão dos métodos da interface
    @Override
    default List<UsuarioCampus> findActive() {
        return findByFlgInativoFalse();
    }
    
    @Override
    default boolean deleteByUsuarioIdAndCampusId(Integer usuarioId, Integer campusId) {
        Optional<UsuarioCampus> usuarioCampus = findByUsuarioIdAndCampusIdOptional(usuarioId, campusId);
        if (usuarioCampus.isPresent()) {
            delete(usuarioCampus.get());
            return true;
        }
        return false;
    }
    
    // Conversão para compatibilidade com interface
    default UsuarioCampus findByUsuarioIdAndCampusId(Integer usuarioId, Integer campusId) {
        return findByUsuarioIdAndCampusIdOptional(usuarioId, campusId).orElse(null);
    }
}

