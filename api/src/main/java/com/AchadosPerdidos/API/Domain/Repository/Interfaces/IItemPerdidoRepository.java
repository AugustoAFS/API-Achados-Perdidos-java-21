package com.AchadosPerdidos.API.Domain.Repository.Interfaces;

import com.AchadosPerdidos.API.Domain.Entity.ItemPerdido;
import java.util.List;

public interface IItemPerdidoRepository {
    List<ItemPerdido> findAll();
    ItemPerdido findById(Integer id);
    ItemPerdido save(ItemPerdido itemPerdido);
    boolean deleteById(Integer id);
    List<ItemPerdido> findActive();
    ItemPerdido findByItemId(Integer itemId);
}

