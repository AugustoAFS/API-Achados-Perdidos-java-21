package com.AchadosPerdidos.API.Application.Services;

import com.AchadosPerdidos.API.Application.DTOs.DeviceToken.DeviceTokenCreateDTO;
import com.AchadosPerdidos.API.Application.DTOs.DeviceToken.DeviceTokenDTO;
import com.AchadosPerdidos.API.Application.DTOs.DeviceToken.DeviceTokenListDTO;
import com.AchadosPerdidos.API.Application.DTOs.DeviceToken.DeviceTokenUpdateDTO;
import com.AchadosPerdidos.API.Application.Mapper.DeviceTokenMapper;
import com.AchadosPerdidos.API.Application.Services.Interfaces.IDeviceTokenService;
import com.AchadosPerdidos.API.Domain.Entity.DeviceToken;
import com.AchadosPerdidos.API.Domain.Repository.DeviceTokenRepository;
import com.AchadosPerdidos.API.Domain.Repository.UsuariosRepository;
import com.AchadosPerdidos.API.Exeptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DeviceTokenService implements IDeviceTokenService {

    @Autowired
    private DeviceTokenRepository deviceTokenRepository;

    @Autowired
    private DeviceTokenMapper deviceTokenMapper;

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Override
    @Cacheable(value = "deviceTokens", key = "'all'")
    public DeviceTokenListDTO getAllDeviceTokens() {
        return deviceTokenMapper.toListDTO(deviceTokenRepository.findAll());
    }

    @Override
    @Cacheable(value = "deviceTokens", key = "'id_' + #id")
    public DeviceTokenDTO getDeviceTokenById(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID do token deve ser válido");
        }

        DeviceToken deviceToken = getDeviceTokenOrThrow(id);
        return deviceTokenMapper.toDTO(deviceToken);
    }

    @Override
    @Cacheable(value = "deviceTokens", key = "'usuario_' + #usuarioId")
    public DeviceTokenListDTO getDeviceTokensByUsuarioId(Integer usuarioId) {
        if (usuarioId == null || usuarioId <= 0) {
            throw new IllegalArgumentException("ID do usuário deve ser válido");
        }

        if (usuariosRepository.findById(usuarioId) == null) {
            throw new ResourceNotFoundException("Usuário não encontrado com ID: " + usuarioId);
        }

        return deviceTokenMapper.toListDTO(deviceTokenRepository.findByUsuarioId(usuarioId));
    }

    @Override
    @Cacheable(value = "deviceTokens", key = "'active_usuario_' + #usuarioId")
    public DeviceTokenListDTO getActiveDeviceTokensByUsuarioId(Integer usuarioId) {
        if (usuarioId == null || usuarioId <= 0) {
            throw new IllegalArgumentException("ID do usuário deve ser válido");
        }

        if (usuariosRepository.findById(usuarioId) == null) {
            throw new ResourceNotFoundException("Usuário não encontrado com ID: " + usuarioId);
        }

        return deviceTokenMapper.toListDTO(deviceTokenRepository.findActiveTokensByUsuarioId(usuarioId));
    }

    @Override
    @CacheEvict(value = "deviceTokens", allEntries = true)
    public DeviceTokenDTO createDeviceToken(DeviceTokenCreateDTO createDTO) {
        if (createDTO == null) {
            throw new IllegalArgumentException("DTO de criação não pode ser nulo");
        }

        if (createDTO.getUsuarioId() == null || createDTO.getUsuarioId() <= 0) {
            throw new IllegalArgumentException("ID do usuário deve ser válido");
        }

        if (createDTO.getToken() == null || createDTO.getToken().trim().isEmpty()) {
            throw new IllegalArgumentException("Token do dispositivo não pode ser vazio");
        }

        if (createDTO.getPlataforma() == null || createDTO.getPlataforma().trim().isEmpty()) {
            throw new IllegalArgumentException("Plataforma do dispositivo deve ser informada");
        }

        String plataforma = createDTO.getPlataforma().toUpperCase();
        if (!plataforma.equals("ANDROID") && !plataforma.equals("IOS")) {
            throw new IllegalArgumentException("Plataforma deve ser ANDROID ou IOS");
        }

        if (usuariosRepository.findById(createDTO.getUsuarioId()) == null) {
            throw new ResourceNotFoundException("Usuário não encontrado com ID: " + createDTO.getUsuarioId());
        }

        // Verifica se já existe um token igual para o mesmo usuário
        DeviceToken existing = deviceTokenRepository.findByUsuarioIdAndToken(createDTO.getUsuarioId(), createDTO.getToken());
        if (existing != null) {
            // Se existe e está inativo, reativa
            if (existing.getFlg_Inativo()) {
                existing.setFlg_Inativo(false);
                existing.setDta_Remocao(null);
                existing.setDta_Atualizacao(LocalDateTime.now());
                return deviceTokenMapper.toDTO(deviceTokenRepository.save(existing));
            }
            // Se já existe e está ativo, retorna o existente
            return deviceTokenMapper.toDTO(existing);
        }

        DeviceToken deviceToken = deviceTokenMapper.toEntity(createDTO);
        DeviceToken saved = deviceTokenRepository.save(deviceToken);
        return deviceTokenMapper.toDTO(saved);
    }

    @Override
    @CacheEvict(value = "deviceTokens", allEntries = true)
    public DeviceTokenDTO updateDeviceToken(Integer id, DeviceTokenUpdateDTO updateDTO) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID do token deve ser válido");
        }

        if (updateDTO == null) {
            throw new IllegalArgumentException("DTO de atualização não pode ser nulo");
        }

        DeviceToken existing = getDeviceTokenOrThrow(id);

        if (updateDTO.getPlataforma() != null) {
            String plataforma = updateDTO.getPlataforma().toUpperCase();
            if (!plataforma.equals("ANDROID") && !plataforma.equals("IOS")) {
                throw new IllegalArgumentException("Plataforma deve ser ANDROID ou IOS");
            }
        }

        DeviceToken updated = deviceTokenMapper.toEntity(updateDTO, existing);
        DeviceToken saved = deviceTokenRepository.save(updated);
        return deviceTokenMapper.toDTO(saved);
    }

    @Override
    @CacheEvict(value = "deviceTokens", allEntries = true)
    public DeviceTokenDTO registerOrUpdateDeviceToken(Integer usuarioId, String token, String plataforma) {
        if (usuarioId == null || usuarioId <= 0) {
            throw new IllegalArgumentException("ID do usuário deve ser válido");
        }

        if (token == null || token.trim().isEmpty()) {
            throw new IllegalArgumentException("Token do dispositivo não pode ser vazio");
        }

        if (plataforma == null || plataforma.trim().isEmpty()) {
            throw new IllegalArgumentException("Plataforma do dispositivo deve ser informada");
        }

        String plataformaUpper = plataforma.toUpperCase();
        if (!plataformaUpper.equals("ANDROID") && !plataformaUpper.equals("IOS")) {
            throw new IllegalArgumentException("Plataforma deve ser ANDROID ou IOS");
        }

        if (usuariosRepository.findById(usuarioId) == null) {
            throw new ResourceNotFoundException("Usuário não encontrado com ID: " + usuarioId);
        }

        // Busca token existente
        DeviceToken existing = deviceTokenRepository.findByUsuarioIdAndToken(usuarioId, token);
        
        if (existing != null) {
            // Se existe, atualiza (reativa se estiver inativo)
            existing.setFlg_Inativo(false);
            existing.setDta_Remocao(null);
            existing.setDta_Atualizacao(LocalDateTime.now());
            existing.setPlataforma(plataformaUpper);
            return deviceTokenMapper.toDTO(deviceTokenRepository.save(existing));
        } else {
            // Se não existe, cria novo
            DeviceTokenCreateDTO createDTO = new DeviceTokenCreateDTO(usuarioId, token, plataformaUpper);
            DeviceToken deviceToken = deviceTokenMapper.toEntity(createDTO);
            DeviceToken saved = deviceTokenRepository.save(deviceToken);
            return deviceTokenMapper.toDTO(saved);
        }
    }

    @Override
    @CacheEvict(value = "deviceTokens", allEntries = true)
    public boolean deleteDeviceToken(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID do token deve ser válido");
        }

        DeviceToken existing = getDeviceTokenOrThrow(id);
        deviceTokenRepository.deleteById(existing.getId());
        return true;
    }

    @Override
    @Cacheable(value = "deviceTokens", key = "'active'")
    public DeviceTokenListDTO getActiveDeviceTokens() {
        return deviceTokenMapper.toListDTO(deviceTokenRepository.findActive());
    }
    
    private DeviceToken getDeviceTokenOrThrow(Integer id) {
        return deviceTokenRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Token de dispositivo não encontrado com ID: " + id));
    }
}

