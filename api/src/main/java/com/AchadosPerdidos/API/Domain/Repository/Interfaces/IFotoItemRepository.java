package com.AchadosPerdidos.API.Domain.Repository.Interfaces;

import com.AchadosPerdidos.API.Domain.Entity.FotoItem;
import java.util.List;

/**
 * Interface do Repository para FotoItem
 * Gerencia associação N:N entre Itens e Fotos
 */
public interface IFotoItemRepository {
    FotoItem findByItemIdAndFotoId(Integer itemId, Integer fotoId);
    boolean deleteByItemIdAndFotoId(Integer itemId, Integer fotoId);
    List<FotoItem> findActive();
    List<FotoItem> findByItemId(Integer itemId);
    List<FotoItem> findByFotoId(Integer fotoId);
}

