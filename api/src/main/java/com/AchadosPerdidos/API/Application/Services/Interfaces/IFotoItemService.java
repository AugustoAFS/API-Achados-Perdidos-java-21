package com.AchadosPerdidos.API.Application.Services.Interfaces;

import com.AchadosPerdidos.API.Application.DTOs.FotoItem.FotoItemListDTO;

public interface IFotoItemService {
    FotoItemListDTO getAllFotosItem();
    FotoItemListDTO getActiveFotosItem();
}
