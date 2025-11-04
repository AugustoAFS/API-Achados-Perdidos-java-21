package com.AchadosPerdidos.API.Application.Services;

import com.AchadosPerdidos.API.Application.DTOs.StatusItem.StatusItemDTO;
import com.AchadosPerdidos.API.Application.DTOs.StatusItem.StatusItemCreateDTO;
import com.AchadosPerdidos.API.Application.DTOs.StatusItem.StatusItemUpdateDTO;
import com.AchadosPerdidos.API.Application.Mapper.StatusItemModelMapper;
import com.AchadosPerdidos.API.Application.Services.Interfaces.IStatusItemService;
import com.AchadosPerdidos.API.Domain.Entity.StatusItem;
import com.AchadosPerdidos.API.Domain.Repository.StatusItemRepository;
import com.AchadosPerdidos.API.Exeptions.BusinessException;
import com.AchadosPerdidos.API.Exeptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StatusItemService implements IStatusItemService {

    @Autowired
    private StatusItemRepository statusItemRepository;

    @Autowired
    private StatusItemModelMapper statusItemModelMapper;

    @Override
    @Cacheable(value = "statusItems", key = "'all'")
    public List<StatusItemDTO> getAllStatus() {
        List<StatusItem> statusItems = statusItemRepository.findAll();
        return statusItems.stream()
                .map(statusItemModelMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "statusItems", key = "#id")
    public StatusItemDTO getStatusById(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID do status deve ser válido");
        }
        
        StatusItem statusItem = statusItemRepository.findById(id);
        if (statusItem == null) {
            throw new ResourceNotFoundException("Status Item", "ID", id);
        }
        return statusItemModelMapper.toDTO(statusItem);
    }

    @Override
    @Cacheable(value = "statusItems", key = "'nome_' + #nome")
    public StatusItemDTO getStatusByNome(String nome) {
        if (!StringUtils.hasText(nome)) {
            throw new IllegalArgumentException("Nome do status não pode ser vazio");
        }
        
        StatusItem statusItem = statusItemRepository.findByNome(nome);
        if (statusItem == null) {
            throw new ResourceNotFoundException("Status Item", "nome", nome);
        }
        return statusItemModelMapper.toDTO(statusItem);
    }

    @Override
    @CacheEvict(value = "statusItems", allEntries = true)
    public StatusItemDTO createStatus(StatusItemCreateDTO createDTO) {
        // Validações de entrada
        if (createDTO == null) {
            throw new IllegalArgumentException("Dados do status não podem ser nulos");
        }
        
        if (!StringUtils.hasText(createDTO.getNome())) {
            throw new BusinessException("Status Item", "criar", "Nome é obrigatório");
        }
        
        // Regra de negócio: Verificar se já existe um status com o mesmo nome
        StatusItem existingStatus = statusItemRepository.findByNome(createDTO.getNome());
        if (existingStatus != null) {
            throw new BusinessException("Status Item", "criar", "Já existe um status com o nome: " + createDTO.getNome());
        }
        
        StatusItem statusItem = new StatusItem();
        statusItem.setNome(createDTO.getNome());
        statusItem.setDescricao(createDTO.getDescricao());
        statusItem.setStatusItem(createDTO.getStatusItem());
        statusItem.setDtaCriacao(new Date());
        statusItem.setFlgInativo(false);
        
        StatusItem savedStatusItem = statusItemRepository.save(statusItem);
        return statusItemModelMapper.toDTO(savedStatusItem);
    }

    @Override
    @CacheEvict(value = "statusItems", allEntries = true)
    public StatusItemDTO updateStatus(Integer id, StatusItemUpdateDTO updateDTO) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID do status deve ser válido");
        }
        
        if (updateDTO == null) {
            throw new IllegalArgumentException("Dados de atualização não podem ser nulos");
        }
        
        StatusItem existingStatusItem = statusItemRepository.findById(id);
        if (existingStatusItem == null) {
            throw new ResourceNotFoundException("Status Item", "ID", id);
        }
        
        // Regra de negócio: Se estiver alterando o nome, verificar se não existe outro com o mesmo nome
        if (updateDTO.getNome() != null && !updateDTO.getNome().equals(existingStatusItem.getNome())) {
            StatusItem statusWithSameName = statusItemRepository.findByNome(updateDTO.getNome());
            if (statusWithSameName != null && !statusWithSameName.getId().equals(id)) {
                throw new BusinessException("Status Item", "atualizar", "Já existe outro status com o nome: " + updateDTO.getNome());
            }
            existingStatusItem.setNome(updateDTO.getNome());
        }
        
        if (updateDTO.getDescricao() != null) {
            existingStatusItem.setDescricao(updateDTO.getDescricao());
        }
        if (updateDTO.getStatusItem() != null) {
            existingStatusItem.setStatusItem(updateDTO.getStatusItem());
        }
        if (updateDTO.getFlgInativo() != null) {
            existingStatusItem.setFlgInativo(updateDTO.getFlgInativo());
        }
        
        StatusItem updatedStatusItem = statusItemRepository.save(existingStatusItem);
        return statusItemModelMapper.toDTO(updatedStatusItem);
    }

    @Override
    @CacheEvict(value = "statusItems", allEntries = true)
    public StatusItemDTO deleteStatus(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID do status deve ser válido");
        }
        
        StatusItem statusItem = statusItemRepository.findById(id);
        if (statusItem == null) {
            throw new ResourceNotFoundException("Status Item", "ID", id);
        }
        
        // Soft delete: Marcar como inativo ao invés de deletar fisicamente
        if (Boolean.TRUE.equals(statusItem.getFlgInativo())) {
            throw new BusinessException("Status Item", "deletar", "O status já está inativo");
        }
        
        statusItem.setFlgInativo(true);
        statusItem.setDtaRemocao(new Date());
        
        StatusItem updatedStatusItem = statusItemRepository.save(statusItem);
        return statusItemModelMapper.toDTO(updatedStatusItem);
    }

    @Override
    @Cacheable(value = "statusItems", key = "'active'")
    public List<StatusItemDTO> getActiveStatus() {
        List<StatusItem> activeStatusItems = statusItemRepository.findActive();
        return activeStatusItems.stream()
                .map(statusItemModelMapper::toDTO)
                .collect(Collectors.toList());
    }
}
