package com.AchadosPerdidos.API.Domain.Repository.Interfaces;

import com.AchadosPerdidos.API.Domain.Entity.FotoItem;
import java.util.List;

public interface IFotoItemRepository {
    List<FotoItem> findAll();
    FotoItem findByItemIdAndFotoId(Integer itemId, Integer fotoId);
    FotoItem save(FotoItem fotoItem);
    boolean deleteByItemIdAndFotoId(Integer itemId, Integer fotoId);
    List<FotoItem> findActive();
    List<FotoItem> findByItemId(Integer itemId);
    List<FotoItem> findByFotoId(Integer fotoId);
}

