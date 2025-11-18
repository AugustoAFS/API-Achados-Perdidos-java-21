package com.AchadosPerdidos.API.Application.Services;

import com.AchadosPerdidos.API.Application.DTOs.UsuarioRole.UsuarioRoleCreateDTO;
import com.AchadosPerdidos.API.Application.DTOs.UsuarioRole.UsuarioRoleDTO;
import com.AchadosPerdidos.API.Application.DTOs.UsuarioRole.UsuarioRoleListDTO;
import com.AchadosPerdidos.API.Application.DTOs.UsuarioRole.UsuarioRoleUpdateDTO;
import com.AchadosPerdidos.API.Application.Mapper.UsuarioRoleModelMapper;
import com.AchadosPerdidos.API.Application.Services.Interfaces.IUsuarioRoleService;
import com.AchadosPerdidos.API.Domain.Entity.UsuarioRole;
import com.AchadosPerdidos.API.Domain.Repository.UsuarioRoleRepository;
import com.AchadosPerdidos.API.Domain.Repository.UsuariosRepository;
import com.AchadosPerdidos.API.Domain.Repository.RoleRepository;
import com.AchadosPerdidos.API.Exeptions.BusinessException;
import com.AchadosPerdidos.API.Exeptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UsuarioRoleService implements IUsuarioRoleService {

    @Autowired
    private UsuarioRoleRepository usuarioRoleRepository;

    @Autowired
    private UsuarioRoleModelMapper usuarioRoleModelMapper;

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    @Cacheable(value = "usuarioRoles", key = "'all'")
    public UsuarioRoleListDTO getAllUsuarioRoles() {
        return usuarioRoleModelMapper.toListDTO(usuarioRoleRepository.findAll());
    }

    @Override
    @Cacheable(value = "usuarioRoles", key = "'usuario_' + #usuarioId + '_role_' + #roleId")
    public UsuarioRoleDTO getUsuarioRoleByUsuarioIdAndRoleId(Integer usuarioId, Integer roleId) {
        if (usuarioId == null || usuarioId <= 0) {
            throw new IllegalArgumentException("ID do usuário deve ser válido");
        }
        if (roleId == null || roleId <= 0) {
            throw new IllegalArgumentException("ID da role deve ser válido");
        }

        UsuarioRole usuarioRole = usuarioRoleRepository.findByUsuarioIdAndRoleId(usuarioId, roleId);
        if (usuarioRole == null) {
            throw new ResourceNotFoundException("Role de usuário não encontrada para usuário ID: " + usuarioId + " e role ID: " + roleId);
        }
        return usuarioRoleModelMapper.toDTO(usuarioRole);
    }

    @Override
    @CacheEvict(value = "usuarioRoles", allEntries = true)
    public UsuarioRoleDTO createUsuarioRole(UsuarioRoleCreateDTO createDTO) {
        if (createDTO == null) {
            throw new IllegalArgumentException("Dados da role de usuário não podem ser nulos");
        }

        if (createDTO.getUsuarioId() == null || createDTO.getUsuarioId() <= 0) {
            throw new BusinessException("UsuarioRole", "criar", "ID do usuário é obrigatório e deve ser válido");
        }

        if (createDTO.getRoleId() == null || createDTO.getRoleId() <= 0) {
            throw new BusinessException("UsuarioRole", "criar", "ID da role é obrigatório e deve ser válido");
        }

        // Verificar se o usuário existe
        if (usuariosRepository.findById(createDTO.getUsuarioId()) == null) {
            throw new ResourceNotFoundException("Usuário", "ID", createDTO.getUsuarioId());
        }

        // Verificar se a role existe
        if (roleRepository.findById(createDTO.getRoleId()) == null) {
            throw new ResourceNotFoundException("Role", "ID", createDTO.getRoleId());
        }

        // Verificar se já existe a associação
        UsuarioRole existing = usuarioRoleRepository.findByUsuarioIdAndRoleId(createDTO.getUsuarioId(), createDTO.getRoleId());
        if (existing != null && (existing.getFlg_Inativo() == null || !existing.getFlg_Inativo())) {
            throw new BusinessException("UsuarioRole", "criar", "Já existe uma associação ativa entre este usuário e esta role");
        }

        UsuarioRole usuarioRole = usuarioRoleModelMapper.fromCreateDTO(createDTO);
        usuarioRole.setDta_Criacao(LocalDateTime.now());
        usuarioRole.setFlg_Inativo(false);

        UsuarioRole savedUsuarioRole = usuarioRoleRepository.save(usuarioRole);
        return usuarioRoleModelMapper.toDTO(savedUsuarioRole);
    }

    @Override
    @CacheEvict(value = "usuarioRoles", allEntries = true)
    public UsuarioRoleDTO updateUsuarioRole(Integer usuarioId, Integer roleId, UsuarioRoleUpdateDTO updateDTO) {
        if (usuarioId == null || usuarioId <= 0) {
            throw new IllegalArgumentException("ID do usuário deve ser válido");
        }
        if (roleId == null || roleId <= 0) {
            throw new IllegalArgumentException("ID da role deve ser válido");
        }
        if (updateDTO == null) {
            throw new IllegalArgumentException("Dados de atualização não podem ser nulos");
        }

        UsuarioRole existingUsuarioRole = usuarioRoleRepository.findByUsuarioIdAndRoleId(usuarioId, roleId);
        if (existingUsuarioRole == null) {
            throw new ResourceNotFoundException("Role de usuário não encontrada para usuário ID: " + usuarioId + " e role ID: " + roleId);
        }

        usuarioRoleModelMapper.updateFromDTO(existingUsuarioRole, updateDTO);
        existingUsuarioRole.setDta_Remocao(updateDTO.getFlgInativo() != null && updateDTO.getFlgInativo() ? LocalDateTime.now() : null);

        UsuarioRole updatedUsuarioRole = usuarioRoleRepository.save(existingUsuarioRole);
        return usuarioRoleModelMapper.toDTO(updatedUsuarioRole);
    }

    @Override
    @CacheEvict(value = "usuarioRoles", allEntries = true)
    public boolean deleteUsuarioRole(Integer usuarioId, Integer roleId) {
        if (usuarioId == null || usuarioId <= 0) {
            throw new IllegalArgumentException("ID do usuário deve ser válido");
        }
        if (roleId == null || roleId <= 0) {
            throw new IllegalArgumentException("ID da role deve ser válido");
        }

        UsuarioRole usuarioRole = usuarioRoleRepository.findByUsuarioIdAndRoleId(usuarioId, roleId);
        if (usuarioRole == null) {
            throw new ResourceNotFoundException("Role de usuário não encontrada para usuário ID: " + usuarioId + " e role ID: " + roleId);
        }

        return usuarioRoleRepository.deleteByUsuarioIdAndRoleId(usuarioId, roleId);
    }

    @Override
    @Cacheable(value = "usuarioRoles", key = "'active'")
    public UsuarioRoleListDTO getActiveUsuarioRoles() {
        return usuarioRoleModelMapper.toListDTO(usuarioRoleRepository.findActive());
    }

    @Override
    @Cacheable(value = "usuarioRoles", key = "'usuario_' + #usuarioId")
    public UsuarioRoleListDTO findByUsuarioId(Integer usuarioId) {
        if (usuarioId == null || usuarioId <= 0) {
            throw new IllegalArgumentException("ID do usuário deve ser válido");
        }
        return usuarioRoleModelMapper.toListDTO(usuarioRoleRepository.findByUsuarioId(usuarioId));
    }

    @Override
    @Cacheable(value = "usuarioRoles", key = "'role_' + #roleId")
    public UsuarioRoleListDTO findByRoleId(Integer roleId) {
        if (roleId == null || roleId <= 0) {
            throw new IllegalArgumentException("ID da role deve ser válido");
        }
        return usuarioRoleModelMapper.toListDTO(usuarioRoleRepository.findByRoleId(roleId));
    }
}

