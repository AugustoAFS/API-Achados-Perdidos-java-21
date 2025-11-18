package com.AchadosPerdidos.API.Infrastruture.DataBase.Interfaces;

import com.AchadosPerdidos.API.Domain.Entity.ItemAchado;
import java.util.List;

public interface IItemAchadoQueries {
    List<ItemAchado> findAll();
    ItemAchado findById(Integer id);
    ItemAchado insert(ItemAchado itemAchado);
    ItemAchado update(ItemAchado itemAchado);
    boolean deleteById(Integer id);
    List<ItemAchado> findActive();
    ItemAchado findByItemId(Integer itemId);
}

