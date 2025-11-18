package com.AchadosPerdidos.API.Domain.Repository;

import com.AchadosPerdidos.API.Domain.Entity.ItemDevolvido;
import com.AchadosPerdidos.API.Domain.Repository.Interfaces.IItemDevolvidoRepository;
import com.AchadosPerdidos.API.Infrastruture.DataBase.ItemDevolvidoQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ItemDevolvidoRepository implements IItemDevolvidoRepository {

    @Autowired
    private ItemDevolvidoQueries itemDevolvidoQueries;

    @Override
    public List<ItemDevolvido> findAll() {
        return itemDevolvidoQueries.findAll();
    }

    @Override
    public ItemDevolvido findById(Integer id) {
        return itemDevolvidoQueries.findById(id);
    }

    @Override
    public ItemDevolvido save(ItemDevolvido itemDevolvido) {
        if (itemDevolvido.getId() == null || itemDevolvido.getId() == 0) {
            return itemDevolvidoQueries.insert(itemDevolvido);
        } else {
            return itemDevolvidoQueries.update(itemDevolvido);
        }
    }

    @Override
    public boolean deleteById(Integer id) {
        return itemDevolvidoQueries.deleteById(id);
    }

    @Override
    public List<ItemDevolvido> findActive() {
        return itemDevolvidoQueries.findActive();
    }

    @Override
    public List<ItemDevolvido> findByItemId(Integer itemId) {
        return itemDevolvidoQueries.findByItemId(itemId);
    }

    @Override
    public List<ItemDevolvido> findByUsuarioDevolvedorId(Integer usuarioId) {
        return itemDevolvidoQueries.findByUsuarioDevolvedorId(usuarioId);
    }
}

