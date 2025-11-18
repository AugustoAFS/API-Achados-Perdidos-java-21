package com.AchadosPerdidos.API.Application.Services;

import com.AchadosPerdidos.API.Application.DTOs.Cidade.CidadeDTO;
import com.AchadosPerdidos.API.Application.DTOs.Cidade.CidadeCreateDTO;
import com.AchadosPerdidos.API.Application.DTOs.Cidade.CidadeUpdateDTO;
import com.AchadosPerdidos.API.Application.Mapper.CidadeModelMapper;
import com.AchadosPerdidos.API.Application.Services.Interfaces.ICidadeService;
import com.AchadosPerdidos.API.Domain.Entity.Cidade;
import com.AchadosPerdidos.API.Domain.Repository.CidadeRepository;
import com.AchadosPerdidos.API.Domain.Repository.EstadoRepository;
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
public class CidadeService implements ICidadeService {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private CidadeModelMapper cidadeModelMapper;

    @Override
    @Cacheable(value = "cidades", key = "'all'")
    public List<CidadeDTO> getAllCidades() {
        List<Cidade> cidades = cidadeRepository.findAll();
        return cidades.stream()
                .map(cidadeModelMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "cidades", key = "#id")
    public CidadeDTO getCidadeById(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID da cidade deve ser válido");
        }
        
        Cidade cidade = cidadeRepository.findById(id);
        if (cidade == null) {
            throw new ResourceNotFoundException("Cidade", "ID", id);
        }
        return cidadeModelMapper.toDTO(cidade);
    }

    @Override
    @CacheEvict(value = "cidades", allEntries = true)
    public CidadeDTO createCidade(CidadeCreateDTO createDTO) {
        if (createDTO == null) {
            throw new IllegalArgumentException("Dados da cidade não podem ser nulos");
        }
        
        if (!StringUtils.hasText(createDTO.getNome())) {
            throw new BusinessException("Cidade", "criar", "Nome é obrigatório");
        }
        
        if (createDTO.getEstadoId() == null || createDTO.getEstadoId() <= 0) {
            throw new BusinessException("Cidade", "criar", "ID do estado é obrigatório e deve ser válido");
        }
        
        // Regra de negócio: Verificar se o estado existe
        com.AchadosPerdidos.API.Domain.Entity.Estado estado = estadoRepository.findById(createDTO.getEstadoId());
        if (estado == null) {
            throw new ResourceNotFoundException("Estado", "ID", createDTO.getEstadoId());
        }
        
        Cidade cidade = new Cidade();
        cidade.setNome(createDTO.getNome());
        cidade.setEstadoId(createDTO.getEstadoId());
        cidade.setDtaCriacao(LocalDateTime.now());
        cidade.setFlgInativo(false);
        
        Cidade savedCidade = cidadeRepository.save(cidade);
        return cidadeModelMapper.toDTO(savedCidade);
    }

    @Override
    @CacheEvict(value = "cidades", allEntries = true)
    public CidadeDTO updateCidade(Integer id, CidadeUpdateDTO updateDTO) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID da cidade deve ser válido");
        }
        
        if (updateDTO == null) {
            throw new IllegalArgumentException("Dados de atualização não podem ser nulos");
        }
        
        Cidade existingCidade = cidadeRepository.findById(id);
        if (existingCidade == null) {
            throw new ResourceNotFoundException("Cidade", "ID", id);
        }
        
        if (updateDTO.getNome() != null) {
            if (!StringUtils.hasText(updateDTO.getNome())) {
                throw new BusinessException("Cidade", "atualizar", "Nome não pode ser vazio");
            }
            existingCidade.setNome(updateDTO.getNome());
        }
        if (updateDTO.getEstadoId() != null) {
            if (updateDTO.getEstadoId() <= 0) {
                throw new BusinessException("Cidade", "atualizar", "ID do estado deve ser válido");
            }
            // Verificar se o novo estado existe
            com.AchadosPerdidos.API.Domain.Entity.Estado estado = estadoRepository.findById(updateDTO.getEstadoId());
            if (estado == null) {
                throw new ResourceNotFoundException("Estado", "ID", updateDTO.getEstadoId());
            }
            existingCidade.setEstadoId(updateDTO.getEstadoId());
        }
        if (updateDTO.getFlgInativo() != null) {
            existingCidade.setFlgInativo(updateDTO.getFlgInativo());
        }
        
        Cidade updatedCidade = cidadeRepository.save(existingCidade);
        return cidadeModelMapper.toDTO(updatedCidade);
    }

    @Override
    @CacheEvict(value = "cidades", allEntries = true)
    public CidadeDTO deleteCidade(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID da cidade deve ser válido");
        }
        
        Cidade cidade = cidadeRepository.findById(id);
        if (cidade == null) {
            throw new ResourceNotFoundException("Cidade", "ID", id);
        }
        
        // Soft delete: Marcar como inativo ao invés de deletar fisicamente
        if (Boolean.TRUE.equals(cidade.getFlgInativo())) {
            throw new BusinessException("Cidade", "deletar", "A cidade já está inativa");
        }
        
        cidade.setFlgInativo(true);
        cidade.setDtaRemocao(LocalDateTime.now());
        
        Cidade updatedCidade = cidadeRepository.save(cidade);
        return cidadeModelMapper.toDTO(updatedCidade);
    }

    @Override
    @Cacheable(value = "cidades", key = "'estado_' + #estadoId")
    public List<CidadeDTO> getCidadesByEstado(Integer estadoId) {
        if (estadoId == null || estadoId <= 0) {
            throw new IllegalArgumentException("ID do estado deve ser válido");
        }
        
        List<Cidade> cidades = cidadeRepository.findByEstado(estadoId);
        return cidades.stream()
                .map(cidadeModelMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "cidades", key = "'active'")
    public List<CidadeDTO> getActiveCidades() {
        List<Cidade> activeCidades = cidadeRepository.findActive();
        return activeCidades.stream()
                .map(cidadeModelMapper::toDTO)
                .collect(Collectors.toList());
    }
}

