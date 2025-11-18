package com.AchadosPerdidos.API.Infrastruture.DataBase.Interfaces;

import com.AchadosPerdidos.API.Domain.Entity.ItemDevolvido;
import java.util.List;

public interface IItemDevolvidoQueries {
    List<ItemDevolvido> findAll();
    ItemDevolvido findById(Integer id);
    ItemDevolvido insert(ItemDevolvido itemDevolvido);
    ItemDevolvido update(ItemDevolvido itemDevolvido);
    boolean deleteById(Integer id);
    List<ItemDevolvido> findActive();
    List<ItemDevolvido> findByItemId(Integer itemId);
    List<ItemDevolvido> findByUsuarioDevolvedorId(Integer usuarioId);
}

