package com.AchadosPerdidos.API.Infrastruture.DataBase.Interfaces;

import com.AchadosPerdidos.API.Domain.Entity.ItemPerdido;
import java.util.List;

public interface IItemPerdidoQueries {
    List<ItemPerdido> findAll();
    ItemPerdido findById(Integer id);
    ItemPerdido insert(ItemPerdido itemPerdido);
    ItemPerdido update(ItemPerdido itemPerdido);
    boolean deleteById(Integer id);
    List<ItemPerdido> findActive();
    ItemPerdido findByItemId(Integer itemId);
}

