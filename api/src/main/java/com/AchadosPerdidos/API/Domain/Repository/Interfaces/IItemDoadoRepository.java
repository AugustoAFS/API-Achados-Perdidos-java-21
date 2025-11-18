package com.AchadosPerdidos.API.Domain.Repository.Interfaces;

import com.AchadosPerdidos.API.Domain.Entity.ItemDoado;
import java.util.List;

public interface IItemDoadoRepository {
    List<ItemDoado> findAll();
    ItemDoado findById(Integer id);
    ItemDoado save(ItemDoado itemDoado);
    boolean deleteById(Integer id);
    List<ItemDoado> findActive();
    ItemDoado findByItemId(Integer itemId);
}

