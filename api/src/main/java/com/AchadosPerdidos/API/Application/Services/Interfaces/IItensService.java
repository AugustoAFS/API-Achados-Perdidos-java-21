package com.AchadosPerdidos.API.Application.Services.Interfaces;

import com.AchadosPerdidos.API.Application.DTOs.Item.ItemDTO;
import com.AchadosPerdidos.API.Application.DTOs.Item.ItemListDTO;
import com.AchadosPerdidos.API.Application.DTOs.Item.ItemCreateDTO;
import com.AchadosPerdidos.API.Application.DTOs.Item.ItemUpdateDTO;

public interface IItensService {
    ItemListDTO getAllItens();
    ItemDTO getItemById(int id);
    ItemDTO createItem(ItemCreateDTO createDTO);
    ItemDTO updateItem(int id, ItemUpdateDTO updateDTO);
    boolean deleteItem(int id);
    ItemListDTO getActiveItens();
    ItemListDTO getItensByUser(int userId);
    ItemListDTO getItensByCampus(int campusId);
    ItemListDTO getItensByLocal(int localId);
    ItemListDTO searchItens(String searchTerm);
    ItemListDTO getItensByTipo(String tipo);
}
