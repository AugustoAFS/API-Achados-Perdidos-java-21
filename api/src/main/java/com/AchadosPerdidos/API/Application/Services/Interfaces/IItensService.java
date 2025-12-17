package com.AchadosPerdidos.API.Application.Services.Interfaces;

import com.AchadosPerdidos.API.Application.DTOs.Item.ItemDTO;
import com.AchadosPerdidos.API.Application.DTOs.Item.ItemListDTO;
import com.AchadosPerdidos.API.Application.DTOs.Item.ItemCreateDTO;
import com.AchadosPerdidos.API.Application.DTOs.Item.ItemUpdateDTO;
import org.springframework.web.multipart.MultipartFile;

public interface IItensService {
    ItemListDTO getAllItens();
    ItemDTO getItemById(int id);
    ItemDTO createItem(ItemCreateDTO createDTO);
    ItemDTO updateItem(int id, ItemUpdateDTO updateDTO);
    boolean deleteItem(int id);
    ItemListDTO getItensByUser(int userId);
    ItemListDTO getItensByCampus(int campusId);

    // Novos métodos para criar itens com fotos
    ItemDTO createItemAchadoComFotos(ItemCreateDTO itemCreateDTO, MultipartFile[] files, String token);
    ItemDTO createItemPerdidoComFotos(ItemCreateDTO itemCreateDTO, MultipartFile[] files, String token);
}
