package com.AchadosPerdidos.API.Application.Services;

import com.AchadosPerdidos.API.Application.DTOs.Campus.CampusDTO;
import com.AchadosPerdidos.API.Application.DTOs.Campus.CampusListDTO;
import com.AchadosPerdidos.API.Application.DTOs.Campus.CampusCreateDTO;
import com.AchadosPerdidos.API.Application.DTOs.Campus.CampusUpdateDTO;
import com.AchadosPerdidos.API.Application.Mapper.CampusModelMapper;
import com.AchadosPerdidos.API.Application.Services.Interfaces.ICampusService;
import com.AchadosPerdidos.API.Domain.Entity.Campus;
import com.AchadosPerdidos.API.Domain.Repository.CampusRepository;
import com.AchadosPerdidos.API.Domain.Repository.InstituicaoRepository;
import com.AchadosPerdidos.API.Domain.Repository.EnderecoRepository;
import com.AchadosPerdidos.API.Exeptions.BusinessException;
import com.AchadosPerdidos.API.Exeptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

@Service
public class CampusService implements ICampusService {

    @Autowired
    private CampusRepository campusRepository;

    @Autowired
    private CampusModelMapper campusModelMapper;
    
    @Autowired
    private InstituicaoRepository instituicaoRepository;
    
    @Autowired
    private EnderecoRepository enderecoRepository;

    @Override
    @Cacheable(value = "campus", key = "'all'")
    public CampusListDTO getAllCampus() {
        List<Campus> campus = campusRepository.findAll();
        return campusModelMapper.toListDTO(campus);
    }

    @Override
    @Cacheable(value = "campus", key = "#id")
    public CampusDTO getCampusById(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID do campus deve ser válido");
        }
        
        Campus campus = campusRepository.findById(id);
        if (campus == null) {
            throw new ResourceNotFoundException("Campus não encontrado com ID: " + id);
        }
        return campusModelMapper.toDTO(campus);
    }

    @Override
    @CacheEvict(value = "campus", allEntries = true)
    public CampusDTO createCampus(CampusDTO campusDTO) {
        if (campusDTO == null) {
            throw new IllegalArgumentException("Dados do campus não podem ser nulos");
        }
        
        validarCampusParaCriacao(campusDTO.getNome(), campusDTO.getInstituicaoId(), campusDTO.getEnderecoId());
        
        Campus campus = campusModelMapper.toEntity(campusDTO);
        campus.setDtaCriacao(new Date());
        campus.setFlgInativo(false);
        
        Campus savedCampus = campusRepository.save(campus);
        return campusModelMapper.toDTO(savedCampus);
    }

    @Override
    @CacheEvict(value = "campus", allEntries = true)
    public CampusDTO createCampusFromDTO(CampusCreateDTO createDTO) {
        if (createDTO == null) {
            throw new IllegalArgumentException("Dados do campus não podem ser nulos");
        }
        
        validarCampusParaCriacao(createDTO.getNome(), createDTO.getInstituicaoId(), createDTO.getEnderecoId());
        
        Campus campus = new Campus();
        campus.setNome(createDTO.getNome());
        campus.setInstituicaoId(createDTO.getInstituicaoId());
        campus.setEnderecoId(createDTO.getEnderecoId());
        campus.setDtaCriacao(new Date());
        campus.setFlgInativo(false);
        
        Campus savedCampus = campusRepository.save(campus);
        return campusModelMapper.toDTO(savedCampus);
    }

    @Override
    @CacheEvict(value = "campus", allEntries = true)
    public CampusDTO updateCampus(int id, CampusDTO campusDTO) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID do campus deve ser válido");
        }
        
        if (campusDTO == null) {
            throw new IllegalArgumentException("Dados de atualização não podem ser nulos");
        }
        
        Campus existingCampus = campusRepository.findById(id);
        if (existingCampus == null) {
            throw new ResourceNotFoundException("Campus não encontrado com ID: " + id);
        }
        
        if (existingCampus.getDtaRemocao() != null) {
            throw new BusinessException("Não é possível atualizar um campus que já foi removido");
        }
        
        validarCampusParaCriacao(campusDTO.getNome(), campusDTO.getInstituicaoId(), campusDTO.getEnderecoId());
        
        existingCampus.setNome(campusDTO.getNome());
        existingCampus.setInstituicaoId(campusDTO.getInstituicaoId());
        existingCampus.setEnderecoId(campusDTO.getEnderecoId());
        existingCampus.setFlgInativo(campusDTO.getFlgInativo());
        existingCampus.setDtaRemocao(campusDTO.getDtaRemocao());
        
        Campus updatedCampus = campusRepository.save(existingCampus);
        return campusModelMapper.toDTO(updatedCampus);
    }

    @Override
    @CacheEvict(value = "campus", allEntries = true)
    public CampusDTO updateCampusFromDTO(int id, CampusUpdateDTO updateDTO) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID do campus deve ser válido");
        }
        
        if (updateDTO == null) {
            throw new IllegalArgumentException("Dados de atualização não podem ser nulos");
        }
        
        Campus existingCampus = campusRepository.findById(id);
        if (existingCampus == null) {
            throw new ResourceNotFoundException("Campus não encontrado com ID: " + id);
        }
        
        if (existingCampus.getDtaRemocao() != null) {
            throw new BusinessException("Não é possível atualizar um campus que já foi removido");
        }
        
        if (updateDTO.getNome() != null) {
            if (!StringUtils.hasText(updateDTO.getNome())) {
                throw new BusinessException("Campus", "atualizar", "Nome não pode ser vazio");
            }
            if (updateDTO.getNome().length() < 3) {
                throw new BusinessException("Campus", "atualizar", "Nome deve ter no mínimo 3 caracteres");
            }
            existingCampus.setNome(updateDTO.getNome());
        }
        
        if (updateDTO.getInstituicaoId() != null) {
            validarInstituicaoExiste(updateDTO.getInstituicaoId());
            existingCampus.setInstituicaoId(updateDTO.getInstituicaoId());
        }
        
        if (updateDTO.getEnderecoId() != null) {
            validarEnderecoExiste(updateDTO.getEnderecoId());
            existingCampus.setEnderecoId(updateDTO.getEnderecoId());
        }
        
        if (updateDTO.getFlgInativo() != null) {
            existingCampus.setFlgInativo(updateDTO.getFlgInativo());
        }
        
        Campus updatedCampus = campusRepository.save(existingCampus);
        return campusModelMapper.toDTO(updatedCampus);
    }

    @Override
    @CacheEvict(value = "campus", allEntries = true)
    public boolean deleteCampus(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID do campus deve ser válido");
        }
        
        Campus campus = campusRepository.findById(id);
        if (campus == null) {
            throw new ResourceNotFoundException("Campus não encontrado com ID: " + id);
        }
        
        if (Boolean.TRUE.equals(campus.getFlgInativo())) {
            throw new BusinessException("O campus já está inativo");
        }
        
        campus.setFlgInativo(true);
        campus.setDtaRemocao(new Date());
        campusRepository.save(campus);
        
        return true;
    }

    @Override
    @Cacheable(value = "campus", key = "'active'")
    public CampusListDTO getActiveCampus() {
        List<Campus> activeCampus = campusRepository.findActive();
        return campusModelMapper.toListDTO(activeCampus);
    }

    @Override
    @Cacheable(value = "campus", key = "'institution_' + #institutionId")
    public CampusListDTO getCampusByInstitution(int institutionId) {
        if (institutionId <= 0) {
            throw new IllegalArgumentException("ID da instituição deve ser válido");
        }
        
        List<Campus> campus = campusRepository.findByInstitution(institutionId);
        return campusModelMapper.toListDTO(campus);
    }
    
    private void validarCampusParaCriacao(String nome, Integer instituicaoId, Integer enderecoId) {
        if (!StringUtils.hasText(nome)) {
            throw new BusinessException("Campus", "criar", "Nome é obrigatório");
        }
        
        if (nome.length() < 3) {
            throw new BusinessException("Campus", "criar", "Nome deve ter no mínimo 3 caracteres");
        }
        
        if (nome.length() > 150) {
            throw new BusinessException("Campus", "criar", "Nome deve ter no máximo 150 caracteres");
        }
        
        if (instituicaoId == null || instituicaoId <= 0) {
            throw new BusinessException("Campus", "criar", "ID da instituição é obrigatório e deve ser válido");
        }
        validarInstituicaoExiste(instituicaoId);
        
        if (enderecoId == null || enderecoId <= 0) {
            throw new BusinessException("Campus", "criar", "ID do endereço é obrigatório e deve ser válido");
        }
        validarEnderecoExiste(enderecoId);
    }
    
    private void validarInstituicaoExiste(Integer instituicaoId) {
        if (instituicaoRepository.findById(instituicaoId) == null) {
            throw new ResourceNotFoundException("Instituição", "ID", instituicaoId);
        }
    }
    
    private void validarEnderecoExiste(Integer enderecoId) {
        if (enderecoRepository.findById(enderecoId) == null) {
            throw new ResourceNotFoundException("Endereço", "ID", enderecoId);
        }
    }
}
