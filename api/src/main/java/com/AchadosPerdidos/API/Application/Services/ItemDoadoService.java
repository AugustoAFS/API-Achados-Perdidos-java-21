package com.AchadosPerdidos.API.Application.Services;

import com.AchadosPerdidos.API.Application.DTOs.ItemDoado.ItemDoadoDTO;
import com.AchadosPerdidos.API.Application.DTOs.ItemDoado.ItemDoadoListDTO;
import com.AchadosPerdidos.API.Application.DTOs.ItemDoado.ItemDoadoCreateDTO;
import com.AchadosPerdidos.API.Application.DTOs.ItemDoado.ItemDoadoUpdateDTO;
import com.AchadosPerdidos.API.Application.Mapper.ItemDoadoModelMapper;
import com.AchadosPerdidos.API.Application.Services.Interfaces.IItemDoadoService;
import com.AchadosPerdidos.API.Domain.Entity.ItemDoado;
import com.AchadosPerdidos.API.Domain.Repository.ItemDoadoRepository;
import com.AchadosPerdidos.API.Domain.Repository.ItensRepository;
import com.AchadosPerdidos.API.Exeptions.BusinessException;
import com.AchadosPerdidos.API.Exeptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ItemDoadoService implements IItemDoadoService {

    @Autowired
    private ItemDoadoRepository itemDoadoRepository;

    @Autowired
    private ItemDoadoModelMapper itemDoadoModelMapper;
    
    @Autowired
    private ItensRepository itensRepository;

    @Override
    @Cacheable(value = "itensDoados", key = "'all'")
    public ItemDoadoListDTO getAllItensDoados() {
        return itemDoadoModelMapper.toListDTO(itemDoadoRepository.findAll());
    }

    @Override
    @Cacheable(value = "itensDoados", key = "#id")
    public ItemDoadoDTO getItemDoadoById(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID do item doado deve ser válido");
        }
        
        ItemDoado itemDoado = itemDoadoRepository.findById(id);
        if (itemDoado == null) {
            throw new ResourceNotFoundException("Item doado não encontrado com ID: " + id);
        }
        return itemDoadoModelMapper.toDTO(itemDoado);
    }

    @Override
    @CacheEvict(value = "itensDoados", allEntries = true)
    public ItemDoadoDTO createItemDoado(ItemDoadoCreateDTO createDTO) {
        if (createDTO == null) {
            throw new IllegalArgumentException("Dados do item doado não podem ser nulos");
        }
        
        if (createDTO.getItemId() == null || createDTO.getItemId() <= 0) {
            throw new BusinessException("ItemDoado", "criar", "ID do item é obrigatório e deve ser válido");
        }
        
        // Verificar se o item existe
        if (itensRepository.findById(createDTO.getItemId()) == null) {
            throw new ResourceNotFoundException("Item", "ID", createDTO.getItemId());
        }
        
        // Verificar se já existe um ItemDoado para este item
        ItemDoado existing = itemDoadoRepository.findByItemId(createDTO.getItemId());
        if (existing != null) {
            throw new BusinessException("ItemDoado", "criar", "Já existe um item doado para este item");
        }
        
        ItemDoado itemDoado = itemDoadoModelMapper.fromCreateDTO(createDTO);
        itemDoado.setId(createDTO.getItemId()); // O ID é o mesmo do item
        if (itemDoado.getDoado_em() == null) {
            itemDoado.setDoado_em(LocalDateTime.now());
        }
        itemDoado.setDta_Criacao(LocalDateTime.now());
        itemDoado.setFlg_Inativo(false);
        
        ItemDoado savedItemDoado = itemDoadoRepository.save(itemDoado);
        return itemDoadoModelMapper.toDTO(savedItemDoado);
    }

    @Override
    @CacheEvict(value = "itensDoados", allEntries = true)
    public ItemDoadoDTO updateItemDoado(Integer id, ItemDoadoUpdateDTO updateDTO) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID do item doado deve ser válido");
        }
        
        if (updateDTO == null) {
            throw new IllegalArgumentException("Dados de atualização não podem ser nulos");
        }
        
        ItemDoado existingItemDoado = itemDoadoRepository.findById(id);
        if (existingItemDoado == null) {
            throw new ResourceNotFoundException("Item doado não encontrado com ID: " + id);
        }
        
        if (existingItemDoado.getDta_Remocao() != null) {
            throw new BusinessException("ItemDoado", "atualizar", "Não é possível atualizar um item doado que já foi removido");
        }
        
        itemDoadoModelMapper.updateFromDTO(existingItemDoado, updateDTO);
        
        ItemDoado updatedItemDoado = itemDoadoRepository.save(existingItemDoado);
        return itemDoadoModelMapper.toDTO(updatedItemDoado);
    }

    @Override
    @CacheEvict(value = "itensDoados", allEntries = true)
    public boolean deleteItemDoado(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID do item doado deve ser válido");
        }
        
        ItemDoado itemDoado = itemDoadoRepository.findById(id);
        if (itemDoado == null) {
            throw new ResourceNotFoundException("Item doado não encontrado com ID: " + id);
        }
        
        if (Boolean.TRUE.equals(itemDoado.getFlg_Inativo())) {
            throw new BusinessException("ItemDoado", "deletar", "O item doado já está inativo");
        }
        
        itemDoado.setFlg_Inativo(true);
        itemDoado.setDta_Remocao(LocalDateTime.now());
        itemDoadoRepository.save(itemDoado);
        
        return true;
    }

    @Override
    @Cacheable(value = "itensDoados", key = "'active'")
    public ItemDoadoListDTO getActiveItensDoados() {
        return itemDoadoModelMapper.toListDTO(itemDoadoRepository.findActive());
    }

    @Override
    @Cacheable(value = "itensDoados", key = "'item_' + #itemId")
    public ItemDoadoDTO findByItemId(Integer itemId) {
        if (itemId == null || itemId <= 0) {
            throw new IllegalArgumentException("ID do item deve ser válido");
        }
        
        ItemDoado itemDoado = itemDoadoRepository.findByItemId(itemId);
        if (itemDoado == null) {
            throw new ResourceNotFoundException("Item doado não encontrado para o item com ID: " + itemId);
        }
        return itemDoadoModelMapper.toDTO(itemDoado);
    }
}

