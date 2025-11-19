package com.AchadosPerdidos.API.Application.Services;

import com.AchadosPerdidos.API.Application.DTOs.Local.LocalDTO;
import com.AchadosPerdidos.API.Application.DTOs.Local.LocalCreateDTO;
import com.AchadosPerdidos.API.Application.DTOs.Local.LocalUpdateDTO;
import com.AchadosPerdidos.API.Application.Mapper.LocalMapper;
import com.AchadosPerdidos.API.Application.Services.Interfaces.ILocalService;
import com.AchadosPerdidos.API.Domain.Entity.Campus;
import com.AchadosPerdidos.API.Domain.Entity.Local;
import com.AchadosPerdidos.API.Domain.Repository.LocalRepository;
import com.AchadosPerdidos.API.Exeptions.BusinessException;
import com.AchadosPerdidos.API.Exeptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocalService implements ILocalService {

    @Autowired
    private LocalRepository localRepository;

    @Autowired
    private LocalMapper localMapper;

    @Override
    @Cacheable(value = "localItems", key = "'all'")
    public List<LocalDTO> getAllLocais() {
        List<Local> locais = localRepository.findAll();
        return locais.stream()
                .map(localMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "localItems", key = "#id")
    public LocalDTO getLocalById(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID do local deve ser válido");
        }
        
        Local local = getLocalOrThrow(id);
        return localMapper.toDTO(local);
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
        local.setCampus_id(buildCampusReference(createDTO.getCampusId()));
        local.setDtaCriacao(LocalDateTime.now());
        local.setFlgInativo(false);
        
        Local savedLocal = localRepository.save(local);
        return localMapper.toDTO(savedLocal);
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
        
        Local existingLocal = getLocalOrThrow(id);
        
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
            existingLocal.setCampus_id(buildCampusReference(updateDTO.getCampusId()));
        }
        if (updateDTO.getFlgInativo() != null) {
            existingLocal.setFlgInativo(updateDTO.getFlgInativo());
        }
        
        Local updatedLocal = localRepository.save(existingLocal);
        return localMapper.toDTO(updatedLocal);
    }

    @Override
    @CacheEvict(value = "localItems", allEntries = true)
    public LocalDTO deleteLocal(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID do local deve ser válido");
        }
        
        Local local = getLocalOrThrow(id);
        
        // Soft delete: Marcar como inativo ao invés de deletar fisicamente
        if (Boolean.TRUE.equals(local.getFlgInativo())) {
            throw new BusinessException("Local", "deletar", "O local já está inativo");
        }
        
        local.setFlgInativo(true);
        local.setDtaRemocao(LocalDateTime.now());
        
        Local updatedLocal = localRepository.save(local);
        return localMapper.toDTO(updatedLocal);
    }

    @Override
    @Cacheable(value = "localItems", key = "'campus_' + #campusId")
    public List<LocalDTO> getLocaisByCampus(Integer campusId) {
        if (campusId == null || campusId <= 0) {
            throw new IllegalArgumentException("ID do campus deve ser válido");
        }
        
        List<Local> locais = localRepository.findByCampus(campusId);
        return locais.stream()
                .map(localMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "localItems", key = "'active'")
    public List<LocalDTO> getActiveLocais() {
        List<Local> activeLocais = localRepository.findActive();
        return activeLocais.stream()
                .map(localMapper::toDTO)
                .collect(Collectors.toList());
    }

    private Local getLocalOrThrow(Integer id) {
        return localRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Local", "ID", id));
    }

    private Campus buildCampusReference(Integer campusId) {
        if (campusId == null) {
            return null;
        }
        Campus campus = new Campus();
        campus.setId(campusId);
        return campus;
    }
}

