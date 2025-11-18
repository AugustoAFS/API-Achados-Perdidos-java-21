package com.AchadosPerdidos.API.Application.Services;

import com.AchadosPerdidos.API.Application.DTOs.ItemDevolvido.ItemDevolvidoCreateDTO;
import com.AchadosPerdidos.API.Application.DTOs.ItemDevolvido.ItemDevolvidoDTO;
import com.AchadosPerdidos.API.Application.DTOs.ItemDevolvido.ItemDevolvidoListDTO;
import com.AchadosPerdidos.API.Application.DTOs.ItemDevolvido.ItemDevolvidoUpdateDTO;
import com.AchadosPerdidos.API.Application.Mapper.ItemDevolvidoModelMapper;
import com.AchadosPerdidos.API.Application.Services.Interfaces.IItemDevolvidoService;
import com.AchadosPerdidos.API.Domain.Entity.ItemDevolvido;
import com.AchadosPerdidos.API.Domain.Repository.ItemDevolvidoRepository;
import com.AchadosPerdidos.API.Domain.Repository.ItensRepository;
import com.AchadosPerdidos.API.Domain.Repository.UsuariosRepository;
import com.AchadosPerdidos.API.Exeptions.BusinessException;
import com.AchadosPerdidos.API.Exeptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ItemDevolvidoService implements IItemDevolvidoService {

    @Autowired
    private ItemDevolvidoRepository itemDevolvidoRepository;

    @Autowired
    private ItemDevolvidoModelMapper itemDevolvidoModelMapper;

    @Autowired
    private ItensRepository itensRepository;

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Override
    @Cacheable(value = "itensDevolvidos", key = "'all'")
    public ItemDevolvidoListDTO getAllItensDevolvidos() {
        return itemDevolvidoModelMapper.toListDTO(itemDevolvidoRepository.findAll());
    }

    @Override
    @Cacheable(value = "itensDevolvidos", key = "#id")
    public ItemDevolvidoDTO getItemDevolvidoById(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID do item devolvido deve ser válido");
        }

        ItemDevolvido itemDevolvido = itemDevolvidoRepository.findById(id);
        if (itemDevolvido == null) {
            throw new ResourceNotFoundException("Item devolvido não encontrado com ID: " + id);
        }
        return itemDevolvidoModelMapper.toDTO(itemDevolvido);
    }

    @Override
    @CacheEvict(value = "itensDevolvidos", allEntries = true)
    public ItemDevolvidoDTO createItemDevolvido(ItemDevolvidoCreateDTO createDTO) {
        if (createDTO == null) {
            throw new IllegalArgumentException("Dados do item devolvido não podem ser nulos");
        }

        if (createDTO.getItemId() == null || createDTO.getItemId() <= 0) {
            throw new BusinessException("ItemDevolvido", "criar", "ID do item é obrigatório e deve ser válido");
        }

        if (createDTO.getUsuarioDevolvedorId() == null || createDTO.getUsuarioDevolvedorId() <= 0) {
            throw new BusinessException("ItemDevolvido", "criar", "ID do usuário devolvedor é obrigatório e deve ser válido");
        }

        if (createDTO.getDetalhesDevolucao() == null || createDTO.getDetalhesDevolucao().trim().isEmpty()) {
            throw new BusinessException("ItemDevolvido", "criar", "Detalhes da devolução são obrigatórios");
        }

        // Verificar se o item existe
        if (itensRepository.findById(createDTO.getItemId()) == null) {
            throw new ResourceNotFoundException("Item", "ID", createDTO.getItemId());
        }

        // Verificar se o usuário devolvedor existe
        if (usuariosRepository.findById(createDTO.getUsuarioDevolvedorId()) == null) {
            throw new ResourceNotFoundException("Usuário Devolvedor", "ID", createDTO.getUsuarioDevolvedorId());
        }

        // Verificar se o usuário que achou existe (se fornecido)
        if (createDTO.getUsuarioAchouId() != null && createDTO.getUsuarioAchouId() > 0) {
            if (usuariosRepository.findById(createDTO.getUsuarioAchouId()) == null) {
                throw new ResourceNotFoundException("Usuário que Achou", "ID", createDTO.getUsuarioAchouId());
            }
        }

        ItemDevolvido itemDevolvido = itemDevolvidoModelMapper.fromCreateDTO(createDTO);
        itemDevolvido.setDtaCriacao(LocalDateTime.now());
        itemDevolvido.setFlgInativo(false);

        ItemDevolvido savedItemDevolvido = itemDevolvidoRepository.save(itemDevolvido);
        return itemDevolvidoModelMapper.toDTO(savedItemDevolvido);
    }

    @Override
    @CacheEvict(value = "itensDevolvidos", allEntries = true)
    public ItemDevolvidoDTO updateItemDevolvido(Integer id, ItemDevolvidoUpdateDTO updateDTO) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID do item devolvido deve ser válido");
        }
        if (updateDTO == null) {
            throw new IllegalArgumentException("Dados de atualização não podem ser nulos");
        }

        ItemDevolvido existingItemDevolvido = itemDevolvidoRepository.findById(id);
        if (existingItemDevolvido == null) {
            throw new ResourceNotFoundException("Item devolvido não encontrado com ID: " + id);
        }

        if (existingItemDevolvido.getFlgInativo() != null && existingItemDevolvido.getFlgInativo()) {
            throw new BusinessException("ItemDevolvido", "atualizar", "Não é possível atualizar um item devolvido inativo");
        }

        itemDevolvidoModelMapper.updateFromDTO(existingItemDevolvido, updateDTO);
        existingItemDevolvido.setDtaRemocao(updateDTO.getFlgInativo() != null && updateDTO.getFlgInativo() ? LocalDateTime.now() : null);

        ItemDevolvido updatedItemDevolvido = itemDevolvidoRepository.save(existingItemDevolvido);
        return itemDevolvidoModelMapper.toDTO(updatedItemDevolvido);
    }

    @Override
    @CacheEvict(value = "itensDevolvidos", allEntries = true)
    public boolean deleteItemDevolvido(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID do item devolvido deve ser válido");
        }

        ItemDevolvido itemDevolvido = itemDevolvidoRepository.findById(id);
        if (itemDevolvido == null) {
            throw new ResourceNotFoundException("Item devolvido não encontrado com ID: " + id);
        }

        if (itemDevolvido.getFlgInativo() != null && itemDevolvido.getFlgInativo()) {
            throw new BusinessException("ItemDevolvido", "deletar", "Item devolvido já está inativo");
        }

        itemDevolvido.setFlgInativo(true);
        itemDevolvido.setDtaRemocao(LocalDateTime.now());
        itemDevolvidoRepository.save(itemDevolvido);
        return true;
    }

    @Override
    @Cacheable(value = "itensDevolvidos", key = "'active'")
    public ItemDevolvidoListDTO getActiveItensDevolvidos() {
        return itemDevolvidoModelMapper.toListDTO(itemDevolvidoRepository.findActive());
    }

    @Override
    @Cacheable(value = "itensDevolvidos", key = "'item_' + #itemId")
    public ItemDevolvidoListDTO findByItemId(Integer itemId) {
        if (itemId == null || itemId <= 0) {
            throw new IllegalArgumentException("ID do item deve ser válido");
        }
        return itemDevolvidoModelMapper.toListDTO(itemDevolvidoRepository.findByItemId(itemId));
    }

    @Override
    @Cacheable(value = "itensDevolvidos", key = "'usuario_' + #usuarioId")
    public ItemDevolvidoListDTO findByUsuarioDevolvedorId(Integer usuarioId) {
        if (usuarioId == null || usuarioId <= 0) {
            throw new IllegalArgumentException("ID do usuário deve ser válido");
        }
        return itemDevolvidoModelMapper.toListDTO(itemDevolvidoRepository.findByUsuarioDevolvedorId(usuarioId));
    }
}

