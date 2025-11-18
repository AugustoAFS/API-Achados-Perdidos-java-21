package com.AchadosPerdidos.API.Domain.Repository;

import com.AchadosPerdidos.API.Domain.Entity.ItemAchado;
import com.AchadosPerdidos.API.Domain.Repository.Interfaces.IItemAchadoRepository;
import com.AchadosPerdidos.API.Infrastruture.DataBase.ItemAchadoQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ItemAchadoRepository implements IItemAchadoRepository {

    @Autowired
    private ItemAchadoQueries itemAchadoQueries;

    @Override
    public List<ItemAchado> findAll() {
        return itemAchadoQueries.findAll();
    }

    @Override
    public ItemAchado findById(Integer id) {
        return itemAchadoQueries.findById(id);
    }

    @Override
    public ItemAchado save(ItemAchado itemAchado) {
        if (itemAchado.getId() == null || itemAchado.getId() == 0) {
            return itemAchadoQueries.insert(itemAchado);
        } else {
            return itemAchadoQueries.update(itemAchado);
        }
    }

    @Override
    public boolean deleteById(Integer id) {
        return itemAchadoQueries.deleteById(id);
    }

    @Override
    public List<ItemAchado> findActive() {
        return itemAchadoQueries.findActive();
    }

    @Override
    public ItemAchado findByItemId(Integer itemId) {
        return itemAchadoQueries.findByItemId(itemId);
    }
}

