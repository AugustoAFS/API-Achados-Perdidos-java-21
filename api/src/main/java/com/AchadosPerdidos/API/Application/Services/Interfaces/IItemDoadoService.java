package com.AchadosPerdidos.API.Application.Services.Interfaces;

import com.AchadosPerdidos.API.Application.DTOs.ItemDoado.ItemDoadoDTO;
import com.AchadosPerdidos.API.Application.DTOs.ItemDoado.ItemDoadoListDTO;
import com.AchadosPerdidos.API.Application.DTOs.ItemDoado.ItemDoadoCreateDTO;
import com.AchadosPerdidos.API.Application.DTOs.ItemDoado.ItemDoadoUpdateDTO;

public interface IItemDoadoService {
    ItemDoadoListDTO getAllItensDoados();
    ItemDoadoDTO getItemDoadoById(Integer id);
    ItemDoadoDTO createItemDoado(ItemDoadoCreateDTO createDTO);
    ItemDoadoDTO updateItemDoado(Integer id, ItemDoadoUpdateDTO updateDTO);
    boolean deleteItemDoado(Integer id);
    ItemDoadoListDTO getActiveItensDoados();
    ItemDoadoDTO findByItemId(Integer itemId);
}

