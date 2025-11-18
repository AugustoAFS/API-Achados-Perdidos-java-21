package com.AchadosPerdidos.API.Infrastruture.DataBase.Interfaces;

import com.AchadosPerdidos.API.Domain.Entity.ItemDoado;
import java.util.List;

public interface IItemDoadoQueries {
    List<ItemDoado> findAll();
    ItemDoado findById(Integer id);
    ItemDoado insert(ItemDoado itemDoado);
    ItemDoado update(ItemDoado itemDoado);
    boolean deleteById(Integer id);
    List<ItemDoado> findActive();
    ItemDoado findByItemId(Integer itemId);
}

