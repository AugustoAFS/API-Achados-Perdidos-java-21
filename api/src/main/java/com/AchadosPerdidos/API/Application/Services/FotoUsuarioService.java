package com.AchadosPerdidos.API.Application.Services;

import com.AchadosPerdidos.API.Application.DTOs.FotoUsuario.FotoUsuarioCreateDTO;
import com.AchadosPerdidos.API.Application.DTOs.FotoUsuario.FotoUsuarioDTO;
import com.AchadosPerdidos.API.Application.DTOs.FotoUsuario.FotoUsuarioListDTO;
import com.AchadosPerdidos.API.Application.DTOs.FotoUsuario.FotoUsuarioUpdateDTO;
import com.AchadosPerdidos.API.Application.Mapper.FotoUsuarioMapper;
import com.AchadosPerdidos.API.Application.Services.Interfaces.IFotoUsuarioService;
import com.AchadosPerdidos.API.Domain.Entity.FotoUsuario;
import com.AchadosPerdidos.API.Domain.Repository.FotoUsuarioRepository;
import com.AchadosPerdidos.API.Domain.Repository.FotosRepository;
import com.AchadosPerdidos.API.Domain.Repository.UsuariosRepository;
import com.AchadosPerdidos.API.Exeptions.BusinessException;
import com.AchadosPerdidos.API.Exeptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class FotoUsuarioService implements IFotoUsuarioService {

    @Autowired
    private FotoUsuarioRepository fotoUsuarioRepository;

    @Autowired
    private FotoUsuarioMapper fotoUsuarioMapper;

    @Autowired
    private FotosRepository fotosRepository;

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Override
    @Cacheable(value = "fotosUsuario", key = "'all'")
    public FotoUsuarioListDTO getAllFotosUsuario() {
        return fotoUsuarioMapper.toListDTO(fotoUsuarioRepository.findAll());
    }

    @Override
    @Cacheable(value = "fotosUsuario", key = "'usuario_' + #usuarioId + '_foto_' + #fotoId")
    public FotoUsuarioDTO getFotoUsuarioByUsuarioIdAndFotoId(Integer usuarioId, Integer fotoId) {
        if (usuarioId == null || usuarioId <= 0) {
            throw new IllegalArgumentException("ID do usuário deve ser válido");
        }
        if (fotoId == null || fotoId <= 0) {
            throw new IllegalArgumentException("ID da foto deve ser válido");
        }

        FotoUsuario fotoUsuario = fotoUsuarioRepository.findByUsuarioIdAndFotoId(usuarioId, fotoId);
        if (fotoUsuario == null) {
            throw new ResourceNotFoundException("Foto de usuário não encontrada para usuário ID: " + usuarioId + " e foto ID: " + fotoId);
        }
        return fotoUsuarioMapper.toDTO(fotoUsuario);
    }

    @Override
    @CacheEvict(value = "fotosUsuario", allEntries = true)
    public FotoUsuarioDTO createFotoUsuario(FotoUsuarioCreateDTO createDTO) {
        if (createDTO == null) {
            throw new IllegalArgumentException("Dados da foto de usuário não podem ser nulos");
        }

        if (createDTO.getUsuarioId() == null || createDTO.getUsuarioId() <= 0) {
            throw new BusinessException("FotoUsuario", "criar", "ID do usuário é obrigatório e deve ser válido");
        }

        if (createDTO.getFotoId() == null || createDTO.getFotoId() <= 0) {
            throw new BusinessException("FotoUsuario", "criar", "ID da foto é obrigatório e deve ser válido");
        }

        // Verificar se o usuário existe
        if (usuariosRepository.findById(createDTO.getUsuarioId()) == null) {
            throw new ResourceNotFoundException("Usuário", "ID", createDTO.getUsuarioId());
        }

        // Verificar se a foto existe
        if (fotosRepository.findById(createDTO.getFotoId()) == null) {
            throw new ResourceNotFoundException("Foto", "ID", createDTO.getFotoId());
        }

        // Verificar se já existe a associação
        FotoUsuario existing = fotoUsuarioRepository.findByUsuarioIdAndFotoId(createDTO.getUsuarioId(), createDTO.getFotoId());
        if (existing != null && (existing.getFlgInativo() == null || !existing.getFlgInativo())) {
            throw new BusinessException("FotoUsuario", "criar", "Já existe uma associação ativa entre este usuário e esta foto");
        }

        FotoUsuario fotoUsuario = fotoUsuarioMapper.fromCreateDTO(createDTO);
        fotoUsuario.setDtaCriacao(LocalDateTime.now());
        fotoUsuario.setFlgInativo(false);

        FotoUsuario savedFotoUsuario = fotoUsuarioRepository.save(fotoUsuario);
        return fotoUsuarioMapper.toDTO(savedFotoUsuario);
    }

    @Override
    @CacheEvict(value = "fotosUsuario", allEntries = true)
    public FotoUsuarioDTO updateFotoUsuario(Integer usuarioId, Integer fotoId, FotoUsuarioUpdateDTO updateDTO) {
        if (usuarioId == null || usuarioId <= 0) {
            throw new IllegalArgumentException("ID do usuário deve ser válido");
        }
        if (fotoId == null || fotoId <= 0) {
            throw new IllegalArgumentException("ID da foto deve ser válido");
        }
        if (updateDTO == null) {
            throw new IllegalArgumentException("Dados de atualização não podem ser nulos");
        }

        FotoUsuario existingFotoUsuario = fotoUsuarioRepository.findByUsuarioIdAndFotoId(usuarioId, fotoId);
        if (existingFotoUsuario == null) {
            throw new ResourceNotFoundException("Foto de usuário não encontrada para usuário ID: " + usuarioId + " e foto ID: " + fotoId);
        }

        fotoUsuarioMapper.updateFromDTO(existingFotoUsuario, updateDTO);
        existingFotoUsuario.setDtaRemocao(updateDTO.getFlgInativo() != null && updateDTO.getFlgInativo() ? LocalDateTime.now() : null);

        FotoUsuario updatedFotoUsuario = fotoUsuarioRepository.save(existingFotoUsuario);
        return fotoUsuarioMapper.toDTO(updatedFotoUsuario);
    }

    @Override
    @CacheEvict(value = "fotosUsuario", allEntries = true)
    public boolean deleteFotoUsuario(Integer usuarioId, Integer fotoId) {
        if (usuarioId == null || usuarioId <= 0) {
            throw new IllegalArgumentException("ID do usuário deve ser válido");
        }
        if (fotoId == null || fotoId <= 0) {
            throw new IllegalArgumentException("ID da foto deve ser válido");
        }

        FotoUsuario fotoUsuario = fotoUsuarioRepository.findByUsuarioIdAndFotoId(usuarioId, fotoId);
        if (fotoUsuario == null) {
            throw new ResourceNotFoundException("Foto de usuário não encontrada para usuário ID: " + usuarioId + " e foto ID: " + fotoId);
        }

        return fotoUsuarioRepository.deleteByUsuarioIdAndFotoId(usuarioId, fotoId);
    }

    @Override
    @Cacheable(value = "fotosUsuario", key = "'active'")
    public FotoUsuarioListDTO getActiveFotosUsuario() {
        return fotoUsuarioMapper.toListDTO(fotoUsuarioRepository.findActive());
    }

    @Override
    @Cacheable(value = "fotosUsuario", key = "'usuario_' + #usuarioId")
    public FotoUsuarioListDTO findByUsuarioId(Integer usuarioId) {
        if (usuarioId == null || usuarioId <= 0) {
            throw new IllegalArgumentException("ID do usuário deve ser válido");
        }
        return fotoUsuarioMapper.toListDTO(fotoUsuarioRepository.findByUsuarioId(usuarioId));
    }

    @Override
    @Cacheable(value = "fotosUsuario", key = "'foto_' + #fotoId")
    public FotoUsuarioListDTO findByFotoId(Integer fotoId) {
        if (fotoId == null || fotoId <= 0) {
            throw new IllegalArgumentException("ID da foto deve ser válido");
        }
        return fotoUsuarioMapper.toListDTO(fotoUsuarioRepository.findByFotoId(fotoId));
    }
}

