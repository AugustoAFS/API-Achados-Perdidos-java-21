package com.AchadosPerdidos.API.Application.Services.Interfaces;

import com.AchadosPerdidos.API.Application.DTOs.ItemDevolvido.ItemDevolvidoCreateDTO;
import com.AchadosPerdidos.API.Application.DTOs.ItemDevolvido.ItemDevolvidoDTO;
import com.AchadosPerdidos.API.Application.DTOs.ItemDevolvido.ItemDevolvidoListDTO;
import com.AchadosPerdidos.API.Application.DTOs.ItemDevolvido.ItemDevolvidoUpdateDTO;

public interface IItemDevolvidoService {
    ItemDevolvidoListDTO getAllItensDevolvidos();
    ItemDevolvidoDTO getItemDevolvidoById(Integer id);
    ItemDevolvidoDTO createItemDevolvido(ItemDevolvidoCreateDTO createDTO);
    ItemDevolvidoDTO updateItemDevolvido(Integer id, ItemDevolvidoUpdateDTO updateDTO);
    boolean deleteItemDevolvido(Integer id);
    ItemDevolvidoListDTO getActiveItensDevolvidos();
    ItemDevolvidoListDTO findByItemId(Integer itemId);
    ItemDevolvidoListDTO findByUsuarioDevolvedorId(Integer usuarioId);
}

