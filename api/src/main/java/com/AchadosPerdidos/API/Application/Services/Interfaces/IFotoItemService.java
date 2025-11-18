package com.AchadosPerdidos.API.Application.Services.Interfaces;

import com.AchadosPerdidos.API.Application.DTOs.FotoItem.FotoItemCreateDTO;
import com.AchadosPerdidos.API.Application.DTOs.FotoItem.FotoItemDTO;
import com.AchadosPerdidos.API.Application.DTOs.FotoItem.FotoItemListDTO;
import com.AchadosPerdidos.API.Application.DTOs.FotoItem.FotoItemUpdateDTO;

public interface IFotoItemService {
    FotoItemListDTO getAllFotosItem();
    FotoItemDTO getFotoItemByItemIdAndFotoId(Integer itemId, Integer fotoId);
    FotoItemDTO createFotoItem(FotoItemCreateDTO createDTO);
    FotoItemDTO updateFotoItem(Integer itemId, Integer fotoId, FotoItemUpdateDTO updateDTO);
    boolean deleteFotoItem(Integer itemId, Integer fotoId);
    FotoItemListDTO getActiveFotosItem();
    FotoItemListDTO findByItemId(Integer itemId);
    FotoItemListDTO findByFotoId(Integer fotoId);
}

