package com.AchadosPerdidos.API.Application.Services;

import com.AchadosPerdidos.API.Application.DTOs.FotoItem.FotoItemCreateDTO;
import com.AchadosPerdidos.API.Application.DTOs.FotoItem.FotoItemDTO;
import com.AchadosPerdidos.API.Application.DTOs.FotoItem.FotoItemListDTO;
import com.AchadosPerdidos.API.Application.DTOs.FotoItem.FotoItemUpdateDTO;
import com.AchadosPerdidos.API.Application.Mapper.FotoItemModelMapper;
import com.AchadosPerdidos.API.Application.Services.Interfaces.IFotoItemService;
import com.AchadosPerdidos.API.Domain.Entity.FotoItem;
import com.AchadosPerdidos.API.Domain.Repository.FotoItemRepository;
import com.AchadosPerdidos.API.Domain.Repository.FotosRepository;
import com.AchadosPerdidos.API.Domain.Repository.ItensRepository;
import com.AchadosPerdidos.API.Exeptions.BusinessException;
import com.AchadosPerdidos.API.Exeptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class FotoItemService implements IFotoItemService {

    @Autowired
    private FotoItemRepository fotoItemRepository;

    @Autowired
    private FotoItemModelMapper fotoItemModelMapper;

    @Autowired
    private FotosRepository fotosRepository;

    @Autowired
    private ItensRepository itensRepository;

    @Override
    @Cacheable(value = "fotosItem", key = "'all'")
    public FotoItemListDTO getAllFotosItem() {
        return fotoItemModelMapper.toListDTO(fotoItemRepository.findAll());
    }

    @Override
    @Cacheable(value = "fotosItem", key = "'item_' + #itemId + '_foto_' + #fotoId")
    public FotoItemDTO getFotoItemByItemIdAndFotoId(Integer itemId, Integer fotoId) {
        if (itemId == null || itemId <= 0) {
            throw new IllegalArgumentException("ID do item deve ser válido");
        }
        if (fotoId == null || fotoId <= 0) {
            throw new IllegalArgumentException("ID da foto deve ser válido");
        }

        FotoItem fotoItem = fotoItemRepository.findByItemIdAndFotoId(itemId, fotoId);
        if (fotoItem == null) {
            throw new ResourceNotFoundException("Foto de item não encontrada para item ID: " + itemId + " e foto ID: " + fotoId);
        }
        return fotoItemModelMapper.toDTO(fotoItem);
    }

    @Override
    @CacheEvict(value = "fotosItem", allEntries = true)
    public FotoItemDTO createFotoItem(FotoItemCreateDTO createDTO) {
        if (createDTO == null) {
            throw new IllegalArgumentException("Dados da foto de item não podem ser nulos");
        }

        if (createDTO.getItemId() == null || createDTO.getItemId() <= 0) {
            throw new BusinessException("FotoItem", "criar", "ID do item é obrigatório e deve ser válido");
        }

        if (createDTO.getFotoId() == null || createDTO.getFotoId() <= 0) {
            throw new BusinessException("FotoItem", "criar", "ID da foto é obrigatório e deve ser válido");
        }

        // Verificar se o item existe
        if (itensRepository.findById(createDTO.getItemId()) == null) {
            throw new ResourceNotFoundException("Item", "ID", createDTO.getItemId());
        }

        // Verificar se a foto existe
        if (fotosRepository.findById(createDTO.getFotoId()) == null) {
            throw new ResourceNotFoundException("Foto", "ID", createDTO.getFotoId());
        }

        // Verificar se já existe a associação
        FotoItem existing = fotoItemRepository.findByItemIdAndFotoId(createDTO.getItemId(), createDTO.getFotoId());
        if (existing != null && (existing.getFlgInativo() == null || !existing.getFlgInativo())) {
            throw new BusinessException("FotoItem", "criar", "Já existe uma associação ativa entre este item e esta foto");
        }

        FotoItem fotoItem = fotoItemModelMapper.fromCreateDTO(createDTO);
        fotoItem.setDtaCriacao(LocalDateTime.now());
        fotoItem.setFlgInativo(false);

        FotoItem savedFotoItem = fotoItemRepository.save(fotoItem);
        return fotoItemModelMapper.toDTO(savedFotoItem);
    }

    @Override
    @CacheEvict(value = "fotosItem", allEntries = true)
    public FotoItemDTO updateFotoItem(Integer itemId, Integer fotoId, FotoItemUpdateDTO updateDTO) {
        if (itemId == null || itemId <= 0) {
            throw new IllegalArgumentException("ID do item deve ser válido");
        }
        if (fotoId == null || fotoId <= 0) {
            throw new IllegalArgumentException("ID da foto deve ser válido");
        }
        if (updateDTO == null) {
            throw new IllegalArgumentException("Dados de atualização não podem ser nulos");
        }

        FotoItem existingFotoItem = fotoItemRepository.findByItemIdAndFotoId(itemId, fotoId);
        if (existingFotoItem == null) {
            throw new ResourceNotFoundException("Foto de item não encontrada para item ID: " + itemId + " e foto ID: " + fotoId);
        }

        fotoItemModelMapper.updateFromDTO(existingFotoItem, updateDTO);

        FotoItem updatedFotoItem = fotoItemRepository.save(existingFotoItem);
        return fotoItemModelMapper.toDTO(updatedFotoItem);
    }

    @Override
    @CacheEvict(value = "fotosItem", allEntries = true)
    public boolean deleteFotoItem(Integer itemId, Integer fotoId) {
        if (itemId == null || itemId <= 0) {
            throw new IllegalArgumentException("ID do item deve ser válido");
        }
        if (fotoId == null || fotoId <= 0) {
            throw new IllegalArgumentException("ID da foto deve ser válido");
        }

        FotoItem fotoItem = fotoItemRepository.findByItemIdAndFotoId(itemId, fotoId);
        if (fotoItem == null) {
            throw new ResourceNotFoundException("Foto de item não encontrada para item ID: " + itemId + " e foto ID: " + fotoId);
        }

        return fotoItemRepository.deleteByItemIdAndFotoId(itemId, fotoId);
    }

    @Override
    @Cacheable(value = "fotosItem", key = "'active'")
    public FotoItemListDTO getActiveFotosItem() {
        return fotoItemModelMapper.toListDTO(fotoItemRepository.findActive());
    }

    @Override
    @Cacheable(value = "fotosItem", key = "'item_' + #itemId")
    public FotoItemListDTO findByItemId(Integer itemId) {
        if (itemId == null || itemId <= 0) {
            throw new IllegalArgumentException("ID do item deve ser válido");
        }
        return fotoItemModelMapper.toListDTO(fotoItemRepository.findByItemId(itemId));
    }

    @Override
    @Cacheable(value = "fotosItem", key = "'foto_' + #fotoId")
    public FotoItemListDTO findByFotoId(Integer fotoId) {
        if (fotoId == null || fotoId <= 0) {
            throw new IllegalArgumentException("ID da foto deve ser válido");
        }
        return fotoItemModelMapper.toListDTO(fotoItemRepository.findByFotoId(fotoId));
    }
}

