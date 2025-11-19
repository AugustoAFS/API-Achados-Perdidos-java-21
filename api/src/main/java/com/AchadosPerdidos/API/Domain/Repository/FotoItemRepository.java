package com.AchadosPerdidos.API.Domain.Repository;

import com.AchadosPerdidos.API.Domain.Entity.FotoItem;
import com.AchadosPerdidos.API.Domain.Repository.Interfaces.IFotoItemRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository para gerenciar associação entre Itens e suas Fotos
 * Usa JPA para CRUD básico
 */
@Repository
public interface FotoItemRepository extends JpaRepository<FotoItem, Integer>, IFotoItemRepository {
    
    // CRUD básico já vem do JpaRepository: save, findById, findAll, deleteById
    
    // Queries customizadas (necessário porque o campo é Flg_Inativo com underscore)
    @Query("SELECT fi FROM FotoItem fi WHERE fi.Flg_Inativo = false")
    List<FotoItem> findByFlgInativoFalse();
    
    @Query("SELECT fi FROM FotoItem fi WHERE fi.Id = :id AND fi.Flg_Inativo = false")
    Optional<FotoItem> findByIdAndFlgInativoFalse(@Param("id") Integer id);
    
    // Queries customizadas simples
    @Query("SELECT fi FROM FotoItem fi WHERE fi.Item_id = :itemId AND fi.Foto_id.Id = :fotoId")
    Optional<FotoItem> findByItemIdAndFotoIdOptional(@Param("itemId") Integer itemId, @Param("fotoId") Integer fotoId);
    
    @Query("SELECT fi FROM FotoItem fi WHERE fi.Item_id = :itemId AND fi.Flg_Inativo = false")
    List<FotoItem> findByItemId(@Param("itemId") Integer itemId);
    
    @Query("SELECT fi FROM FotoItem fi WHERE fi.Foto_id.Id = :fotoId AND fi.Flg_Inativo = false")
    List<FotoItem> findByFotoId(@Param("fotoId") Integer fotoId);
    
    // Implementação padrão dos métodos da interface
    @Override
    default List<FotoItem> findActive() {
        return findByFlgInativoFalse();
    }
    
    @Override
    default boolean deleteByItemIdAndFotoId(Integer itemId, Integer fotoId) {
        Optional<FotoItem> fotoItem = findByItemIdAndFotoIdOptional(itemId, fotoId);
        if (fotoItem.isPresent()) {
            delete(fotoItem.get());
            return true;
        }
        return false;
    }
    
    // Conversão para compatibilidade com interface
    default FotoItem findByItemIdAndFotoId(Integer itemId, Integer fotoId) {
        return findByItemIdAndFotoIdOptional(itemId, fotoId).orElse(null);
    }
}

