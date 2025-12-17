package com.AchadosPerdidos.API.Application.Services;

import com.AchadosPerdidos.API.Application.DTOs.FotoItem.FotoItemListDTO;
import com.AchadosPerdidos.API.Application.Mapper.FotoItemMapper;
import com.AchadosPerdidos.API.Application.Services.Interfaces.IFotoItemService;
import com.AchadosPerdidos.API.Domain.Entity.FotoItem;
import com.AchadosPerdidos.API.Domain.Repository.FotoItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class FotoItemService implements IFotoItemService {

    @Autowired
    private FotoItemRepository fotoItemRepository;

    @Autowired
    private FotoItemMapper fotoItemMapper;

    @Override
    @Cacheable(value = "fotosItem", key = "'all'")
    public FotoItemListDTO getAllFotosItem() {
        return fotoItemMapper.toListDTO(fotoItemRepository.findAll());
    }

    @Override
    @Cacheable(value = "fotosItem", key = "'active'")
    public FotoItemListDTO getActiveFotosItem() {
        return fotoItemMapper.toListDTO(fotoItemRepository.findActive());
    }
}

