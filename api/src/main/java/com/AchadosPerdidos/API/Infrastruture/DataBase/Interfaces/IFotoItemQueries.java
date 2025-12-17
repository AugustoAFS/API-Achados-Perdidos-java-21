package com.AchadosPerdidos.API.Infrastruture.DataBase.Interfaces;

import com.AchadosPerdidos.API.Domain.Entity.FotoItem;
import java.util.List;

public interface IFotoItemQueries {
    List<FotoItem> findActive();
    FotoItem findByItemIdAndFotoId(Integer itemId, Integer fotoId);
    List<FotoItem> findByItemId(Integer itemId);
    List<FotoItem> findByFotoId(Integer fotoId);
}

