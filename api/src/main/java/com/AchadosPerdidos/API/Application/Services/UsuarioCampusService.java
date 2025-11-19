package com.AchadosPerdidos.API.Application.Services;

import com.AchadosPerdidos.API.Application.DTOs.UsuarioCampus.UsuarioCampusCreateDTO;
import com.AchadosPerdidos.API.Application.DTOs.UsuarioCampus.UsuarioCampusDTO;
import com.AchadosPerdidos.API.Application.DTOs.UsuarioCampus.UsuarioCampusListDTO;
import com.AchadosPerdidos.API.Application.DTOs.UsuarioCampus.UsuarioCampusUpdateDTO;
import com.AchadosPerdidos.API.Application.Mapper.UsuarioCampusMapper;
import com.AchadosPerdidos.API.Application.Services.Interfaces.IUsuarioCampusService;
import com.AchadosPerdidos.API.Domain.Entity.UsuarioCampus;
import com.AchadosPerdidos.API.Domain.Repository.UsuarioCampusRepository;
import com.AchadosPerdidos.API.Domain.Repository.UsuariosRepository;
import com.AchadosPerdidos.API.Domain.Repository.CampusRepository;
import com.AchadosPerdidos.API.Exeptions.BusinessException;
import com.AchadosPerdidos.API.Exeptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UsuarioCampusService implements IUsuarioCampusService {

    @Autowired
    private UsuarioCampusRepository usuarioCampusRepository;

    @Autowired
    private UsuarioCampusMapper usuarioCampusMapper;

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Autowired
    private CampusRepository campusRepository;

    @Override
    @Cacheable(value = "usuarioCampus", key = "'all'")
    public UsuarioCampusListDTO getAllUsuarioCampus() {
        return usuarioCampusMapper.toListDTO(usuarioCampusRepository.findAll());
    }

    @Override
    @Cacheable(value = "usuarioCampus", key = "'usuario_' + #usuarioId + '_campus_' + #campusId")
    public UsuarioCampusDTO getUsuarioCampusByUsuarioIdAndCampusId(Integer usuarioId, Integer campusId) {
        if (usuarioId == null || usuarioId <= 0) {
            throw new IllegalArgumentException("ID do usuário deve ser válido");
        }
        if (campusId == null || campusId <= 0) {
            throw new IllegalArgumentException("ID do campus deve ser válido");
        }

        UsuarioCampus usuarioCampus = usuarioCampusRepository.findByUsuarioIdAndCampusId(usuarioId, campusId);
        if (usuarioCampus == null) {
            throw new ResourceNotFoundException("Campus de usuário não encontrado para usuário ID: " + usuarioId + " e campus ID: " + campusId);
        }
        return usuarioCampusMapper.toDTO(usuarioCampus);
    }

    @Override
    @CacheEvict(value = "usuarioCampus", allEntries = true)
    public UsuarioCampusDTO createUsuarioCampus(UsuarioCampusCreateDTO createDTO) {
        if (createDTO == null) {
            throw new IllegalArgumentException("Dados do campus de usuário não podem ser nulos");
        }

        if (createDTO.getUsuarioId() == null || createDTO.getUsuarioId() <= 0) {
            throw new BusinessException("UsuarioCampus", "criar", "ID do usuário é obrigatório e deve ser válido");
        }

        if (createDTO.getCampusId() == null || createDTO.getCampusId() <= 0) {
            throw new BusinessException("UsuarioCampus", "criar", "ID do campus é obrigatório e deve ser válido");
        }

        // Verificar se o usuário existe
        if (usuariosRepository.findById(createDTO.getUsuarioId()) == null) {
            throw new ResourceNotFoundException("Usuário", "ID", createDTO.getUsuarioId());
        }

        // Verificar se o campus existe
        campusRepository.findById(createDTO.getCampusId())
                .orElseThrow(() -> new ResourceNotFoundException("Campus", "ID", createDTO.getCampusId()));

        // Verificar se já existe a associação
        UsuarioCampus existing = usuarioCampusRepository.findByUsuarioIdAndCampusId(createDTO.getUsuarioId(), createDTO.getCampusId());
        if (existing != null && (existing.getFlg_Inativo() == null || !existing.getFlg_Inativo())) {
            throw new BusinessException("UsuarioCampus", "criar", "Já existe uma associação ativa entre este usuário e este campus");
        }

        UsuarioCampus usuarioCampus = usuarioCampusMapper.fromCreateDTO(createDTO);
        usuarioCampus.setDta_Criacao(LocalDateTime.now());
        usuarioCampus.setFlg_Inativo(false);

        UsuarioCampus savedUsuarioCampus = usuarioCampusRepository.save(usuarioCampus);
        return usuarioCampusMapper.toDTO(savedUsuarioCampus);
    }

    @Override
    @CacheEvict(value = "usuarioCampus", allEntries = true)
    public UsuarioCampusDTO updateUsuarioCampus(Integer usuarioId, Integer campusId, UsuarioCampusUpdateDTO updateDTO) {
        if (usuarioId == null || usuarioId <= 0) {
            throw new IllegalArgumentException("ID do usuário deve ser válido");
        }
        if (campusId == null || campusId <= 0) {
            throw new IllegalArgumentException("ID do campus deve ser válido");
        }
        if (updateDTO == null) {
            throw new IllegalArgumentException("Dados de atualização não podem ser nulos");
        }

        UsuarioCampus existingUsuarioCampus = usuarioCampusRepository.findByUsuarioIdAndCampusId(usuarioId, campusId);
        if (existingUsuarioCampus == null) {
            throw new ResourceNotFoundException("Campus de usuário não encontrado para usuário ID: " + usuarioId + " e campus ID: " + campusId);
        }

        usuarioCampusMapper.updateFromDTO(existingUsuarioCampus, updateDTO);
        existingUsuarioCampus.setDtaRemocao(updateDTO.getFlgInativo() != null && updateDTO.getFlgInativo() ? LocalDateTime.now() : null);

        UsuarioCampus updatedUsuarioCampus = usuarioCampusRepository.save(existingUsuarioCampus);
        return usuarioCampusMapper.toDTO(updatedUsuarioCampus);
    }

    @Override
    @CacheEvict(value = "usuarioCampus", allEntries = true)
    public boolean deleteUsuarioCampus(Integer usuarioId, Integer campusId) {
        if (usuarioId == null || usuarioId <= 0) {
            throw new IllegalArgumentException("ID do usuário deve ser válido");
        }
        if (campusId == null || campusId <= 0) {
            throw new IllegalArgumentException("ID do campus deve ser válido");
        }

        UsuarioCampus usuarioCampus = usuarioCampusRepository.findByUsuarioIdAndCampusId(usuarioId, campusId);
        if (usuarioCampus == null) {
            throw new ResourceNotFoundException("Campus de usuário não encontrado para usuário ID: " + usuarioId + " e campus ID: " + campusId);
        }

        return usuarioCampusRepository.deleteByUsuarioIdAndCampusId(usuarioId, campusId);
    }

    @Override
    @Cacheable(value = "usuarioCampus", key = "'active'")
    public UsuarioCampusListDTO getActiveUsuarioCampus() {
        return usuarioCampusMapper.toListDTO(usuarioCampusRepository.findActive());
    }

    @Override
    @Cacheable(value = "usuarioCampus", key = "'usuario_' + #usuarioId")
    public UsuarioCampusListDTO findByUsuarioId(Integer usuarioId) {
        if (usuarioId == null || usuarioId <= 0) {
            throw new IllegalArgumentException("ID do usuário deve ser válido");
        }
        return usuarioCampusMapper.toListDTO(usuarioCampusRepository.findByUsuarioId(usuarioId));
    }

    @Override
    @Cacheable(value = "usuarioCampus", key = "'campus_' + #campusId")
    public UsuarioCampusListDTO findByCampusId(Integer campusId) {
        if (campusId == null || campusId <= 0) {
            throw new IllegalArgumentException("ID do campus deve ser válido");
        }
        return usuarioCampusMapper.toListDTO(usuarioCampusRepository.findByCampusId(campusId));
    }
}

