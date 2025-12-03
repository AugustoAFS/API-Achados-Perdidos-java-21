package com.AchadosPerdidos.API.Domain.Repository.Interfaces;

import com.AchadosPerdidos.API.Domain.Entity.FotoItem;
import java.util.List;
import java.util.Optional;

/**
 * Interface do Repository para FotoItem
 * Gerencia associação N:N entre Itens e Fotos
 */
public interface IFotoItemRepository {
    // Operações CRUD básicas
    List<FotoItem> findAll();
    Optional<FotoItem> findById(Integer id);
    FotoItem save(FotoItem fotoItem);
    void deleteById(Integer id);
    
    // Buscas específicas
    FotoItem findByItemIdAndFotoId(Integer itemId, Integer fotoId);
    boolean deleteByItemIdAndFotoId(Integer itemId, Integer fotoId);
    List<FotoItem> findActive();
    List<FotoItem> findByItemId(Integer itemId);
    List<FotoItem> findByFotoId(Integer fotoId);
}

