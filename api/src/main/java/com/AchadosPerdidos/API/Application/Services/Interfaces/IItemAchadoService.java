package com.AchadosPerdidos.API.Application.Services.Interfaces;

import com.AchadosPerdidos.API.Application.DTOs.ItemAchado.ItemAchadoDTO;
import com.AchadosPerdidos.API.Application.DTOs.ItemAchado.ItemAchadoListDTO;
import com.AchadosPerdidos.API.Application.DTOs.ItemAchado.ItemAchadoCreateDTO;
import com.AchadosPerdidos.API.Application.DTOs.ItemAchado.ItemAchadoUpdateDTO;

public interface IItemAchadoService {
    ItemAchadoListDTO getAllItensAchados();
    ItemAchadoDTO getItemAchadoById(Integer id);
    ItemAchadoDTO createItemAchado(ItemAchadoCreateDTO createDTO);
    ItemAchadoDTO updateItemAchado(Integer id, ItemAchadoUpdateDTO updateDTO);
    boolean deleteItemAchado(Integer id);
    ItemAchadoListDTO getActiveItensAchados();
    ItemAchadoDTO getItemAchadoByItemId(Integer itemId);
}

