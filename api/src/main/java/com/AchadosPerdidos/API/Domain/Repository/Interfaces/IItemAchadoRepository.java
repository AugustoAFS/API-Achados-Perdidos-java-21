package com.AchadosPerdidos.API.Domain.Repository.Interfaces;

import com.AchadosPerdidos.API.Domain.Entity.ItemAchado;
import java.util.List;

public interface IItemAchadoRepository {
    List<ItemAchado> findAll();
    ItemAchado findById(Integer id);
    ItemAchado save(ItemAchado itemAchado);
    boolean deleteById(Integer id);
    List<ItemAchado> findActive();
    ItemAchado findByItemId(Integer itemId);
}

