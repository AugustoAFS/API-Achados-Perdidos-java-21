package com.AchadosPerdidos.API.Application.Services.Interfaces;

import com.AchadosPerdidos.API.Application.DTOs.FotoItem.FotoItemCreateDTO;
import com.AchadosPerdidos.API.Application.DTOs.FotoItem.FotoItemDTO;
import com.AchadosPerdidos.API.Application.DTOs.FotoItem.FotoItemListDTO;
import com.AchadosPerdidos.API.Application.DTOs.FotoItem.FotoItemUpdateDTO;

public interface IFotoItemService {
    FotoItemListDTO getAllFotosItem();
    FotoItemListDTO getActiveFotosItem();
}
