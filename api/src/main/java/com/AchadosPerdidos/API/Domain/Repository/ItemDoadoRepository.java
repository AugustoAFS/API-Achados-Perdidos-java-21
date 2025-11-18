package com.AchadosPerdidos.API.Domain.Repository;

import com.AchadosPerdidos.API.Domain.Entity.ItemDoado;
import com.AchadosPerdidos.API.Domain.Repository.Interfaces.IItemDoadoRepository;
import com.AchadosPerdidos.API.Infrastruture.DataBase.ItemDoadoQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ItemDoadoRepository implements IItemDoadoRepository {

    @Autowired
    private ItemDoadoQueries itemDoadoQueries;

    @Override
    public List<ItemDoado> findAll() {
        return itemDoadoQueries.findAll();
    }

    @Override
    public ItemDoado findById(Integer id) {
        return itemDoadoQueries.findById(id);
    }

    @Override
    public ItemDoado save(ItemDoado itemDoado) {
        if (itemDoado.getId() == null || itemDoado.getId() == 0) {
            return itemDoadoQueries.insert(itemDoado);
        } else {
            return itemDoadoQueries.update(itemDoado);
        }
    }

    @Override
    public boolean deleteById(Integer id) {
        return itemDoadoQueries.deleteById(id);
    }

    @Override
    public List<ItemDoado> findActive() {
        return itemDoadoQueries.findActive();
    }

    @Override
    public ItemDoado findByItemId(Integer itemId) {
        return itemDoadoQueries.findByItemId(itemId);
    }
}

