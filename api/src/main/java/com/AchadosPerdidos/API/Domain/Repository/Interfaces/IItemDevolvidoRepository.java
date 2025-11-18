package com.AchadosPerdidos.API.Domain.Repository.Interfaces;

import com.AchadosPerdidos.API.Domain.Entity.ItemDevolvido;
import java.util.List;

public interface IItemDevolvidoRepository {
    List<ItemDevolvido> findAll();
    ItemDevolvido findById(Integer id);
    ItemDevolvido save(ItemDevolvido itemDevolvido);
    boolean deleteById(Integer id);
    List<ItemDevolvido> findActive();
    List<ItemDevolvido> findByItemId(Integer itemId);
    List<ItemDevolvido> findByUsuarioDevolvedorId(Integer usuarioId);
}

