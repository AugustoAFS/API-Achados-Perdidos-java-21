package com.AchadosPerdidos.API.Domain.Repository;

import com.AchadosPerdidos.API.Domain.Entity.ItemPerdido;
import com.AchadosPerdidos.API.Domain.Repository.Interfaces.IItemPerdidoRepository;
import com.AchadosPerdidos.API.Infrastruture.DataBase.ItemPerdidoQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ItemPerdidoRepository implements IItemPerdidoRepository {

    @Autowired
    private ItemPerdidoQueries itemPerdidoQueries;

    @Override
    public List<ItemPerdido> findAll() {
        return itemPerdidoQueries.findAll();
    }

    @Override
    public ItemPerdido findById(Integer id) {
        return itemPerdidoQueries.findById(id);
    }

    @Override
    public ItemPerdido save(ItemPerdido itemPerdido) {
        if (itemPerdido.getId() == null || itemPerdido.getId() == 0) {
            return itemPerdidoQueries.insert(itemPerdido);
        } else {
            return itemPerdidoQueries.update(itemPerdido);
        }
    }

    @Override
    public boolean deleteById(Integer id) {
        return itemPerdidoQueries.deleteById(id);
    }

    @Override
    public List<ItemPerdido> findActive() {
        return itemPerdidoQueries.findActive();
    }

    @Override
    public ItemPerdido findByItemId(Integer itemId) {
        return itemPerdidoQueries.findByItemId(itemId);
    }
}

