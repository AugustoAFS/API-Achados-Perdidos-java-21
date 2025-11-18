package com.AchadosPerdidos.API.Application.Services.Interfaces;

import com.AchadosPerdidos.API.Application.DTOs.ItemPerdido.ItemPerdidoDTO;
import com.AchadosPerdidos.API.Application.DTOs.ItemPerdido.ItemPerdidoListDTO;
import com.AchadosPerdidos.API.Application.DTOs.ItemPerdido.ItemPerdidoCreateDTO;
import com.AchadosPerdidos.API.Application.DTOs.ItemPerdido.ItemPerdidoUpdateDTO;

public interface IItemPerdidoService {
    ItemPerdidoListDTO getAllItensPerdidos();
    ItemPerdidoDTO getItemPerdidoById(Integer id);
    ItemPerdidoDTO createItemPerdido(ItemPerdidoCreateDTO createDTO);
    ItemPerdidoDTO updateItemPerdido(Integer id, ItemPerdidoUpdateDTO updateDTO);
    boolean deleteItemPerdido(Integer id);
    ItemPerdidoListDTO getActiveItensPerdidos();
    ItemPerdidoDTO getItemPerdidoByItemId(Integer itemId);
}

