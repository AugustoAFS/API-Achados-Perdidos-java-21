package com.AchadosPerdidos.API.Application.Services;

import com.AchadosPerdidos.API.Application.DTOs.Local.LocalDTO;
import com.AchadosPerdidos.API.Application.DTOs.Local.LocalCreateDTO;
import com.AchadosPerdidos.API.Application.DTOs.Local.LocalUpdateDTO;
import com.AchadosPerdidos.API.Application.Mapper.LocalModelMapper;
import com.AchadosPerdidos.API.Application.Services.Interfaces.ILocalService;
import com.AchadosPerdidos.API.Domain.Entity.Local;
import com.AchadosPerdidos.API.Domain.Repository.LocalRepository;
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
public class LocalService implements ILocalService {

    @Autowired
    private LocalRepository localRepository;

    @Autowired
    private LocalModelMapper localModelMapper;

    @Override
    @Cacheable(value = "localItems", key = "'all'")
    public List<LocalDTO> getAllLocais() {
        List<Local> locais = localRepository.findAll();
        return locais.stream()
                .map(localModelMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "localItems", key = "#id")
    public LocalDTO getLocalById(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID do local deve ser válido");
        }
        
        Local local = localRepository.findById(id);
        if (local == null) {
            throw new ResourceNotFoundException("Local", "ID", id);
        }
        return localModelMapper.toDTO(local);
    }

    @Override
    @CacheEvict(value = "localItems", allEntries = true)
    public LocalDTO createLocal(LocalCreateDTO createDTO) {
        if (createDTO == null) {
            throw new IllegalArgumentException("Dados do local não podem ser nulos");
        }
        
        if (!StringUtils.hasText(createDTO.getNome())) {
            throw new BusinessException("Local", "criar", "Nome é obrigatório");
        }
        
        if (createDTO.getCampusId() == null || createDTO.getCampusId() <= 0) {
            throw new BusinessException("Local", "criar", "ID do campus é obrigatório e deve ser válido");
        }
        
        Local local = new Local();
        local.setNome(createDTO.getNome());
        local.setDescricao(createDTO.getDescricao());
        local.setCampusId(createDTO.getCampusId());
        local.setDtaCriacao(new Date());
        local.setFlgInativo(false);
        
        Local savedLocal = localRepository.save(local);
        return localModelMapper.toDTO(savedLocal);
    }

    @Override
    @CacheEvict(value = "localItems", allEntries = true)
    public LocalDTO updateLocal(Integer id, LocalUpdateDTO updateDTO) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID do local deve ser válido");
        }
        
        if (updateDTO == null) {
            throw new IllegalArgumentException("Dados de atualização não podem ser nulos");
        }
        
        Local existingLocal = localRepository.findById(id);
        if (existingLocal == null) {
            throw new ResourceNotFoundException("Local", "ID", id);
        }
        
        if (updateDTO.getNome() != null) {
            if (!StringUtils.hasText(updateDTO.getNome())) {
                throw new BusinessException("Local", "atualizar", "Nome não pode ser vazio");
            }
            existingLocal.setNome(updateDTO.getNome());
        }
        if (updateDTO.getDescricao() != null) {
            existingLocal.setDescricao(updateDTO.getDescricao());
        }
        if (updateDTO.getCampusId() != null) {
            if (updateDTO.getCampusId() <= 0) {
                throw new BusinessException("Local", "atualizar", "ID do campus deve ser válido");
            }
            existingLocal.setCampusId(updateDTO.getCampusId());
        }
        if (updateDTO.getFlgInativo() != null) {
            existingLocal.setFlgInativo(updateDTO.getFlgInativo());
        }
        
        Local updatedLocal = localRepository.save(existingLocal);
        return localModelMapper.toDTO(updatedLocal);
    }

    @Override
    @CacheEvict(value = "localItems", allEntries = true)
    public LocalDTO deleteLocal(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID do local deve ser válido");
        }
        
        Local local = localRepository.findById(id);
        if (local == null) {
            throw new ResourceNotFoundException("Local", "ID", id);
        }
        
        // Soft delete: Marcar como inativo ao invés de deletar fisicamente
        if (Boolean.TRUE.equals(local.getFlgInativo())) {
            throw new BusinessException("Local", "deletar", "O local já está inativo");
        }
        
        local.setFlgInativo(true);
        local.setDtaRemocao(new Date());
        
        Local updatedLocal = localRepository.save(local);
        return localModelMapper.toDTO(updatedLocal);
    }

    @Override
    @Cacheable(value = "localItems", key = "'campus_' + #campusId")
    public List<LocalDTO> getLocaisByCampus(Integer campusId) {
        if (campusId == null || campusId <= 0) {
            throw new IllegalArgumentException("ID do campus deve ser válido");
        }
        
        List<Local> locais = localRepository.findByCampus(campusId);
        return locais.stream()
                .map(localModelMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "localItems", key = "'active'")
    public List<LocalDTO> getActiveLocais() {
        List<Local> activeLocais = localRepository.findActive();
        return activeLocais.stream()
                .map(localModelMapper::toDTO)
                .collect(Collectors.toList());
    }
}

