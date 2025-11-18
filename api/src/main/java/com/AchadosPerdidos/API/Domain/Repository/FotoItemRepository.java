package com.AchadosPerdidos.API.Domain.Repository;

import com.AchadosPerdidos.API.Domain.Entity.FotoItem;
import com.AchadosPerdidos.API.Domain.Repository.Interfaces.IFotoItemRepository;
import com.AchadosPerdidos.API.Infrastruture.DataBase.FotoItemQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FotoItemRepository implements IFotoItemRepository {

    @Autowired
    private FotoItemQueries fotoItemQueries;

    @Override
    public List<FotoItem> findAll() {
        return fotoItemQueries.findAll();
    }

    @Override
    public FotoItem findByItemIdAndFotoId(Integer itemId, Integer fotoId) {
        return fotoItemQueries.findByItemIdAndFotoId(itemId, fotoId);
    }

    @Override
    public FotoItem save(FotoItem fotoItem) {
        FotoItem existing = fotoItemQueries.findByItemIdAndFotoId(fotoItem.getItemId(), fotoItem.getFotoId());
        if (existing == null) {
            return fotoItemQueries.insert(fotoItem);
        } else {
            return fotoItemQueries.update(fotoItem);
        }
    }

    @Override
    public boolean deleteByItemIdAndFotoId(Integer itemId, Integer fotoId) {
        return fotoItemQueries.deleteByItemIdAndFotoId(itemId, fotoId);
    }

    @Override
    public List<FotoItem> findActive() {
        return fotoItemQueries.findActive();
    }

    @Override
    public List<FotoItem> findByItemId(Integer itemId) {
        return fotoItemQueries.findByItemId(itemId);
    }

    @Override
    public List<FotoItem> findByFotoId(Integer fotoId) {
        return fotoItemQueries.findByFotoId(fotoId);
    }
}

